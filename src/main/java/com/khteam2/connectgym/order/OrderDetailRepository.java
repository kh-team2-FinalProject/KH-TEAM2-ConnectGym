package com.khteam2.connectgym.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrder(Order order);

    @Query("SELECT o FROM OrderDetail o WHERE o.order = ?1")
    OrderDetail findByEnroll(Order order);

    OrderDetail findByEnrollKey(Long enrollKey);

    @Query(value = "SELECT od FROM OrderDetail od"
        + " JOIN FETCH od.lesson l"
        + " JOIN FETCH od.order o"
        + " JOIN FETCH l.trainer t"
        + " WHERE o = :order"
        + " AND ("
        + "     l.title LIKE CONCAT('%', TRIM(:search), '%')"
        + "     OR t.trainerName LIKE CONCAT('%', TRIM(:search), '%')"
        + " )")
    List<OrderDetail> findByOrderAndLessonTitleOrTrainerName(@Param("order") Order order, @Param("search") String search);

    @Query("SELECT od FROM OrderDetail od WHERE lesson.no = ?1")
    List<OrderDetail> enrollList(Long lessonNo);

    //메인 'Popular Trainer' > 누적 수강생 Top3
    @Query("SELECT od.lesson.trainer AS trainerNo, COUNT(*) AS studentCount " +
        "FROM OrderDetail od " +
        "GROUP BY od.lesson.trainer.no " +
        "ORDER BY studentCount DESC")
    List<Object[]> findCountGroupByTrainer();

    //트레이너별 누적 수강생
    @Query("SELECT COUNT(od) FROM OrderDetail od " +
        "WHERE od.lesson.trainer.no = ?1")
    Optional<Integer> findCountByTrainer(Long trainerNo);

    //레슨별 누적 수강생
    @Query("SELECT COUNT(od) from OrderDetail od WHERE od.lesson.no = ?1")
    int findTotalOrderCountByLessonNo(Long lessonNo);
}
