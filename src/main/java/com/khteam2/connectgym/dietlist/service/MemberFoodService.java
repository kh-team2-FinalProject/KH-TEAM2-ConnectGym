package com.khteam2.connectgym.dietlist.service;

import com.khteam2.connectgym.dietlist.model.MemberFood;
import com.khteam2.connectgym.dietlist.model.MemberFoodResponseDto;
import com.khteam2.connectgym.dietlist.repository.MemberFoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public List<MemberFoodResponseDto> getUniqueDayAndFoodTime(LocalDate startDate, LocalDate endDate) {

        List<MemberFoodResponseDto> memberFoodListByDateRange = getMemberFoodListByDateRange(startDate, endDate);

        List<MemberFoodResponseDto> uniqueDayAndFoodTime = new ArrayList<>();
        Set<String> chechDayAndFooTime = new HashSet<>();


        for (MemberFoodResponseDto m : memberFoodListByDateRange) {
            String check = m.getDay() + "-" + m.getFoodTime();
            if (!chechDayAndFooTime.contains(check)) {
                chechDayAndFooTime.add(check);
                uniqueDayAndFoodTime.add(m);
            }


        }
        return uniqueDayAndFoodTime;
    }


}
