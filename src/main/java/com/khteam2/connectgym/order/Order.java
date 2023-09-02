package com.khteam2.connectgym.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khteam2.connectgym.member.Member;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"member"})
public class Order {
    @Id
    private String no;
    @JoinColumn(name = "member_no", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Member member;
    @Column(nullable = false, length = 50)
    private String type;
    @Column(nullable = false)
    @Comment("주문한 날짜 및 시간")
    private Timestamp dayOfPayment;
    @Column(nullable = false)
    private Long orderPay;
}
