package com.khteam2.connectgym.customer_service;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Data
@Table(name = "notice")
public class Notice {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "notice_datetime")
    @CreationTimestamp
    private LocalDateTime noticeDatetime;
    @Column(name = "top_con", nullable = false)
    private Integer topContent;

//    public String setDateFormat(LocalDateTime noticeDatetime) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String stringDate = sdf.format(noticeDatetime);
//        String viewDate = stringDate.substring(0, 10);
//        return viewDate;
//    }
}
