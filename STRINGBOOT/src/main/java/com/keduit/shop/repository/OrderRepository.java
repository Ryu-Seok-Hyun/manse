package com.keduit.shop.repository;


import com.keduit.shop.entity.Order;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

  // Order 엔티티에 대한 CRUD 작업을 지원하는 리포지토리 인터페이스
  @Query("select o from Order o " +
      "where o.member.email = :email " + "order by o.orderDate desc")
  List<Order> findOrders(@Param("email") String email, Pageable pageable);

  @Query("select count(o) from Order o " +
      "where o.member.email = :email")
  Long countOrder(@Param("email") String email);


}
