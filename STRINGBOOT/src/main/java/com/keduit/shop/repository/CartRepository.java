package com.keduit.shop.repository;
// 장바구니임

import com.keduit.shop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
  // Cart 엔티티에 대한 CRUD (Create, Read, Update, Delete) 작업을 지원하는 리포지토리 인터페이스
  // JpaRepository를 상속받아 기본적인 데이터 접근 기능을 제공

  Cart findByMemberId(Long member_Id);
}

