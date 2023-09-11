package com.khteam2.connectgym.dietlist.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khteam2.connectgym.common.Pagination;
import com.khteam2.connectgym.dietlist.FoodTime;
import com.khteam2.connectgym.dietlist.model.*;
import com.khteam2.connectgym.dietlist.repository.FoodRepository;
import com.khteam2.connectgym.dietlist.repository.MemberFoodRepository;
import com.khteam2.connectgym.member.Member;
import com.khteam2.connectgym.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;
    private final MemberFoodRepository memberFoodRepository;
    private final MemberRepository memberRepository;

    @Value("${dietApi.key}")
    private String opendataEncodedApiKey;

    /* food api로 get */
    public FoodNutrientDto getFoods(int pageNo, int limit) {
        FoodNutrientDto dto = null;

        try {
            URL url = new URL(
                new StringBuilder("http://apis.data.go.kr/1471000/FoodNtrIrdntInfoService1/getFoodNtrItdntList1")
                    .append("?serviceKey=")
                    .append(this.opendataEncodedApiKey)
                    .append("&type=json")
                    .append("&numOfRows=")
                    .append(limit)
                    .append("&pageNo=")
                    .append(pageNo)
                    .toString()
            );

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Accept", "*/*;q=0.9");

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8));

            String line = null;
            StringBuilder body = new StringBuilder();
            while ((line = br.readLine()) != null) {
                body.append(line);
            }

            ObjectMapper mapper = new ObjectMapper();
            dto = mapper.readValue(body.toString(), FoodNutrientDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return dto;
    }

    /* db 저장 */
    @Transactional
    public int moveDataToDatabase() {
        int page = 1;
        int pageSize = 100;
        int totalCount = 0;
        int totalPage = 1;

        while (totalPage >= page) {
            FoodNutrientDto dto = this.getFoods(page, pageSize);
            List<Food> newFoods = dto.getBody().getItems().stream()
                .map(FoodNutrientFoodDto::ofOpenDataFoodNutrientItemDto)
                .map(FoodNutrientFoodDto::toEntity)
                .collect(Collectors.toList());

            this.foodRepository.saveAll(newFoods);

            totalCount = dto.getBody().getTotalCount();
            totalPage = totalCount / pageSize + (totalCount % pageSize > 0 ? 1 : 0);
            page++;
        }

        return 1;
    }

    @Transactional
    @Override
    public Food saveFood(Food food) {
        return foodRepository.save(food);
    }

    /* 유효성 체크해서 에러 메세지 */
    @Override
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            String errorMessage = error.getDefaultMessage();
            validatorResult.put(validKeyName, errorMessage);
        }

        return validatorResult;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Food> searchFood(String key) {
        List<Food> foodinfo = new ArrayList<>();

        List<Food> foods = foodRepository.findByFoodNmContains(key);
        for (Food food : foods) {
            foodinfo.add(food);
        }

        return foodinfo;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Food> searchDiet(String key) {
        List<Food> dietList = new ArrayList<>();

        List<Food> foods = foodRepository.findByFoodNmContains(key);
        for (Food food : foods) {
            dietList.add(food);
        }

        return dietList;
    }

    @Transactional
    @Override
    public Food selectFood(Long selectedKey) {
        Food selfood = foodRepository.findByFoodCd(selectedKey);
        return selfood;
    }

    /**
     * 다이어트 리스트를 보여줄 때 사용되는 메소드
     *
     * @param loginMemberNo 로그인된 회원의 번호
     * @param date          조회할 날짜
     * @return 식단 등의 정보를 담은 DTO
     */
    @Transactional(readOnly = true)
    @Override
    public DietListResponseDto dietList(Long loginMemberNo, LocalDate date) {
        DietListResponseDto responseDto = DietListResponseDto.builder()
            .success(false)
            .build();

        // 로그인 되어있지 않으면 오류 메시지를 넣고 객체를 반환한다.
        if (loginMemberNo == null) {
            responseDto.setMessage("로그인되어 있지 않습니다.");
            return responseDto;
        }

        // 만약 날짜가 없을 경우를 대비해 예외를 처리한다.
        if (date == null) {
            responseDto.setMessage("잘못된 요청입니다.");
            return responseDto;
        }

        // 회원 번호를 이용해 회원 정보를 가져온다.
        Member member = this.memberRepository.findById(loginMemberNo).orElse(null);

        // 혹시 조회했는데 없는 회원일 수도 있으니 그럴 때를 대비한 예외를 처리한다.
        if (member == null) {
            responseDto.setMessage("사용자 정보가 없습니다.");
            return responseDto;
        }

        // 회원이 등록한 음식 리스트를 가져온다.
        List<MemberFood> memberFoodList = this.memberFoodRepository.findByMemberAndRegDate(member, date);

        // 아침, 점심, 저녁, 간식 목록을 나눠주기 위해 각각의 List를 생성한다.
        List<DietListFoodDto> breakfastList = new ArrayList<>();
        List<DietListFoodDto> lunchList = new ArrayList<>();
        List<DietListFoodDto> dinnerList = new ArrayList<>();
        List<DietListFoodDto> snackList = new ArrayList<>();

        // 가져온 음식 리스트에서 음식을 순환한다.
        for (MemberFood memberFood : memberFoodList) {
            // 회원이 등록한 음식의 시간을 가져온다.
            FoodTime foodTime = memberFood.getFoodTime();
            // 회원이 등록한 음식을 가져온다.
            Food food = memberFood.getFood();
            // DTO로 변환한다.
            FoodDto foodDto = FoodDto.of(food);
            // 정보를 담은 DTO를 생성해 준다.
            DietListFoodDto dietListFoodDto = DietListFoodDto.builder()
                .memberFoodNo(memberFood.getNo())
                .food(foodDto)
                .build();

            // 등록한 각 음식 시간을 분류해 해당 리스트에 추가한다.
            switch (foodTime) {
                case BREAKFAST:
                    breakfastList.add(dietListFoodDto);
                    break;
                case LUNCH:
                    lunchList.add(dietListFoodDto);
                    break;
                case DINNER:
                    dinnerList.add(dietListFoodDto);
                    break;
                case SNACK:
                    snackList.add(dietListFoodDto);
                    break;
                default:
                    // 혹시 모를 예외를 방지하기 위해 추가한다.
                    responseDto.setMessage("알 수 없는 시간대입니다.");
                    return responseDto;
            }
        }

        // 정상적으로 처리되었다면 DTO에 값을 넣고 객체를 반환한다.
        responseDto.setSuccess(true);
        responseDto.setBreakfastList(breakfastList);
        responseDto.setLunchList(lunchList);
        responseDto.setDinnerList(dinnerList);
        responseDto.setSnackList(snackList);

        return responseDto;
    }

    /**
     * 음식을 검색하는 메소드
     *
     * @param requestDto 검색어나 페이징 정보를 담는 DTO
     * @return 검색한 음식에 대한 정보를 담아두는 DTO 반환
     */
    @Transactional(readOnly = true)
    @Override
    public FoodFindResponseDto findFood(FoodFindRequestDto requestDto) {
        FoodFindResponseDto responseDto = FoodFindResponseDto.builder()
            .success(false)
            .build();

        // DTO에서 현재 페이지 및 검색어 등을 가져온다.
        Integer page = requestDto.getPage();
        int recordSize = 20;
        int pageSize = 10;
        String search = requestDto.getSearch();

        // 입력받은 현재 페이지가 없거나 1미만이면 1로 설정한다.
        if (page == null || page < 1) {
            page = 1;
        }

        // 페이징하기 위해서 DB에 넘겨줄 객체를 생성한다.
        Pageable pageable = PageRequest.of(page - 1, recordSize);
        Page<Food> foodPageList = null;

        if (search != null && !search.isBlank()) {
            // 검색어가 있으면 실행되는 조건문.
            // 해당 음식 이름이 포함되어 있는 음식만 조회한다.
            foodPageList = this.foodRepository.findByFoodNmContains(search, pageable);
        } else {
            // 검색어가 없으면 실행되는 조건문.
            // 모든 음식을 조회한다.
            foodPageList = this.foodRepository.findAll(pageable);
        }

        // 엔티티를 DTO로 바꿔준다.
        List<FoodDto> foodDtoList = foodPageList.stream()
            .map(FoodDto::of)
            .collect(Collectors.toList());

        // 페이징 정보를 DTO에 넘겨준다.
        Pagination pagination = new Pagination(foodPageList, pageSize);

        responseDto.setSuccess(true);
        responseDto.setFoods(foodDtoList);
        responseDto.setPagination(pagination);

        return responseDto;
    }

    /**
     * 다이어트 리스트에 음식을 넣는 메소드
     *
     * @param requestDto    다이어트 리스트에 넣을 음식의 정보를 담은 DTO
     * @param loginMemberNo 로그인된 회원의 번호
     * @return 성공/실패 여부를 담은 객체 반환
     */
    @Transactional
    @Override
    public FoodInsertResponseDto insertDietListFood(FoodInsertRequestDto requestDto, Long loginMemberNo) {
        FoodInsertResponseDto responseDto = FoodInsertResponseDto.builder()
            .success(false)
            .build();

        // DTO에서 정보들을 꺼내 변수에 넣어준다.
        Long foodNo = requestDto.getSelectedKey();
        FoodTime foodTime = requestDto.getFoodTime();
        LocalDate date = requestDto.getDate();

        if (loginMemberNo == null) {
            responseDto.setMessage("로그인되어 있지 않습니다.");
            return responseDto;
        }

        // 만약 파라미터에 값이 하나도 없다면 오류 메시지를 설정한 후 객체를 반환한다.
        if (foodNo == null || foodTime == null || date == null) {
            responseDto.setMessage("잘못된 요청입니다.");
            return responseDto;
        }

        // 회원 번호를 이용해 회원 정보를 가져온다.
        Member member = this.memberRepository.findById(loginMemberNo).orElse(null);

        // 회원 정보가 없을 때를 대비한 예외를 처리한다.
        if (member == null) {
            responseDto.setMessage("사용자 정보가 없습니다.");
            return responseDto;
        }

        // 요청한 음식 번호를 이용해 음식 정보를 불러온다.
        Food food = this.foodRepository.findById(foodNo).orElse(null);

        // 해당 음식이 존재하지 않으면 객체를 반환한다.
        if (food == null) {
            responseDto.setMessage("해당 음식이 없습니다.");
            return responseDto;
        }

        // DB에 넣어주기 위해서 새로운 엔티티를 생성한다.
        MemberFood memberFood = MemberFood.builder()
            .food(food)
            .member(member)
            .foodTime(foodTime)
            .regDate(date)
            .build();

        // 회원이 요청한 음식을 DB에 넣는다.
        this.memberFoodRepository.save(memberFood);

        // 성공적으로 끝났으면 true로 설정해 주고 객체를 반환한다.
        responseDto.setSuccess(true);

        return responseDto;
    }

    /**
     * 다이어트 리스트에 등록된 음식을 삭제할 때 사용되는 메소드
     *
     * @param loginMemberNo 로그인된 회원의 번호
     * @param memberFoodNo  삭제하려는 회원의 음식 번호
     * @return 삭제 성공 여부를 담는 DTO 객체 반환
     */
    @Transactional
    @Override
    public FoodDeleteResponseDto deleteDietListFood(Long loginMemberNo, Long memberFoodNo) {
        FoodDeleteResponseDto responseDto = FoodDeleteResponseDto.builder()
            .success(false)
            .build();

        // 로그인되어 있지 않거나 필수 파라미터가 존재하지 않으면 객체를 반환한다.
        if (loginMemberNo == null || memberFoodNo == null) {
            responseDto.setMessage("잘못된 요청입니다.");
            return responseDto;
        }

        // 로그인된 회원의 번호를 이용해 회원을 찾는다.
        Member member = this.memberRepository.findById(loginMemberNo).orElse(null);

        // 회원이 존재하지 않을 때를 대비해 예외를 설정해 준다.
        if (member == null) {
            responseDto.setMessage("사용자 정보가 없습니다.");
            return responseDto;
        }

        // 삭제하려는 회원의 음식을 조회한다.
        MemberFood memberFood = this.memberFoodRepository.findByMemberAndNo(member, memberFoodNo);

        // NullPointerException을 방지하기 위해 null이 아닌 경우에만 삭제한다.
        if (memberFood != null) {
            this.memberFoodRepository.delete(memberFood);
        }

        // 성공했다면 성공 여부에 true로 설정하고 객체를 반환한다.
        responseDto.setSuccess(true);

        return responseDto;
    }
}
