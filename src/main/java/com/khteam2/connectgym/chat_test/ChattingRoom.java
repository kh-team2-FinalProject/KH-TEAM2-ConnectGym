<<<<<<< HEAD
//package com.khteam2.connectgym.chat_test;
//
//import lombok.Data;
//import org.springframework.data.annotation.Id;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//
//@Data
//@Entity
//public class ChattingRoom {
//    @Id
//    @GeneratedValue
//    private Long no;
//    private Long userNo;
//    private Long trainerNo;
//    private Long chatMessageNo;
//}
=======
package com.khteam2.connectgym.chat_test;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class ChattingRoom {
    @Id
    @GeneratedValue
    private Long no;
    private Long userNo;
    private Long trainerNo;
    private Long chatMessageNo;
}
>>>>>>> e8fe5ce30d5e246b63843063304d5f27e18c4f62
