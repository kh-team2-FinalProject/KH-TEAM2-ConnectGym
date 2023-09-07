package com.khteam2.connectgym.order;

import com.khteam2.connectgym.order.dto.OrderDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    //리뷰쓰기에 넘겨줄 정보
    @Transactional
    public OrderDetailDto findOrderDetail(Long orderDetailNo){
        OrderDetail od = orderDetailRepository.findById(orderDetailNo).orElse(null);

        OrderDetailDto odd = OrderDetailDto.builder()
            .no(od.getNo())
            .lesson(od.getLesson())
            .build();

        return odd;
    }

}
