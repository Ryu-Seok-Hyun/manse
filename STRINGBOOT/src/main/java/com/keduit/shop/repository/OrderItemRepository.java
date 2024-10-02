package com.keduit.shop.repository;

import com.keduit.shop.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
  // OrderItem 엔티티에 대한 CRUD 작업을 지원하는 리포지토리 인터페이스
}
