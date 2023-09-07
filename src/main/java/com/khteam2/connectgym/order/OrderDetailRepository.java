package com.khteam2.connectgym.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrder(Order order);

    @Query("SELECT o FROM OrderDetail o WHERE o.order = ?1")
    OrderDetail findByEnroll(Order order);

    List<OrderDetail> findByOrderOrderByNoDesc(Order order);

    OrderDetail findByEnrollKey(Long enrollKey);

    @Query(value = "SELECT od FROM OrderDetail od"
        + " JOIN FETCH od.order o"
        + " JOIN FETCH o.member m"
        + " WHERE m.no = :memberNo")
    List<OrderDetail> findByMemberNo(@Param("memberNo") Long memberNo);

    @Query(value = "SELECT od FROM OrderDetail od"
        + " JOIN FETCH od.lesson l"
        + " JOIN FETCH l.trainer t"
        + " WHERE l.title LIKE CONCAT('%', TRIM(:search), '%')"
        + " OR t.trainerName LIKE CONCAT('%', TRIM(:search), '%')")
    List<OrderDetail> findByLessonTitleOrTrainerName(@Param("search") String search);

    @Query("SELECT od FROM OrderDetail od WHERE lesson.no = ?1")
    List<OrderDetail> enrollList(Long lessonNo);


}
