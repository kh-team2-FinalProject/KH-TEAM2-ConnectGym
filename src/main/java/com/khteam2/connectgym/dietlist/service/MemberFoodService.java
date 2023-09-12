package com.khteam2.connectgym.dietlist.service;

import com.khteam2.connectgym.dietlist.model.MemberFood;
import com.khteam2.connectgym.dietlist.model.MemberFoodResponseDto;
import com.khteam2.connectgym.dietlist.repository.MemberFoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MemberFoodService {
    private final MemberFoodRepository memberFoodRepository;

    public List<MemberFoodResponseDto> getMemberFoodListByDateRange(LocalDate startDate, LocalDate endDate) {
        List<MemberFood> memberFoodList = memberFoodRepository.findByRegDateBetween(startDate, endDate);
        List<MemberFoodResponseDto> memberFoodResponseDtoList = new ArrayList<>();

        for (MemberFood m : memberFoodList) {
            memberFoodResponseDtoList.add(new MemberFoodResponseDto().formEntity(m));
        }

        return memberFoodResponseDtoList;
    }

    public List<MemberFoodResponseDto> getMemberFoodListByMemberNoAndDateRange(Long memberNo, LocalDate startDate, LocalDate endDate) {
        List<MemberFood> memberFoodList = memberFoodRepository.findByMemberNoAndRegDateBetween(memberNo, startDate, endDate);
        Collections.sort(memberFoodList, Comparator.comparingInt(MemberFood::getFoodTimeNo));

        List<MemberFoodResponseDto> memberFoodResponseDtoList = new ArrayList<>();
        for (MemberFood m : memberFoodList) {
            memberFoodResponseDtoList.add(new MemberFoodResponseDto().formEntity(m));
        }

        return memberFoodResponseDtoList;
    }

    //중복제거
    public List<MemberFoodResponseDto> getUniqueDayAndFoodTime(Long memberNo, LocalDate startDate, LocalDate endDate) {
        List<MemberFoodResponseDto> memberFoodListByDateRange = getMemberFoodListByMemberNoAndDateRange(memberNo, startDate, endDate);

        List<MemberFoodResponseDto> uniqueDayAndFoodTime = new ArrayList<>();
        Set<String> checkDayAndFooTime = new HashSet<>();

        for (MemberFoodResponseDto m : memberFoodListByDateRange) {
            String check = m.getDay() + "-" + m.getFoodTime();
            if (!checkDayAndFooTime.contains(check)) {
                checkDayAndFooTime.add(check);
                uniqueDayAndFoodTime.add(m);
            }
        }
        return uniqueDayAndFoodTime;
    }
}
