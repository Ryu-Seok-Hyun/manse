package com.keduit.shop.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.keduit.shop.constant.ItemSellStatus;
import com.keduit.shop.repository.ItemRepository;
import com.keduit.shop.repository.MemberRepository;
import com.keduit.shop.repository.OrderItemRepository;
import com.keduit.shop.repository.OrderRepository;
import java.time.LocalDateTime;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest // 스프링 부트 테스트 설정
@Transactional // 각 테스트 후 데이터 롤백
public class OrderTests {

  @Autowired
  OrderRepository orderRepository; // 주문 레포지토리 주입

  @Autowired
  ItemRepository itemRepository; // 아이템 레포지토리 주입

  @Autowired
  MemberRepository memberRepository; // 회원 레포지토리 주입

  @Autowired
  OrderItemRepository orderItemRepository; // 주문 아이템 레포지토리 주입

  @PersistenceContext
  EntityManager em; // 엔티티 매니저 주입 (데이터베이스와의 상호작용을 위해 사용)

  // 테스트용 아이템 생성 메서드
  public Item createItem() {
    Item item = new Item(); // 새로운 아이템 객체 생성
    item.setItemNm("테스트 상품"); // 아이템 이름 설정
    item.setPrice(15000); // 가격 설정
    item.setItemDetail("테스트 상품 상세 설명"); // 상세 설명 설정
    item.setItemSellStatus(ItemSellStatus.SELL); // 판매 상태 설정
    item.setStockNumber(100); // 재고 수량 설정
    item.setRegTime(LocalDateTime.now()); // 등록 시간 설정
    item.setUpdateTime(LocalDateTime.now()); // 수정 시간 설정
    return item; // 생성된 아이템 반환
  }

  // 테스트용 주문 생성 메서드
  public Order createOrder() {
    Order order = new Order(); // 새로운 주문 객체 생성

    // 3개의 주문 아이템 생성
    for (int i = 0; i < 3; i++) {
      Item item = this.createItem(); // 아이템 생성
      itemRepository.save(item); // 아이템 저장
      OrderItem orderItem = new OrderItem(); // 주문 아이템 객체 생성
      orderItem.setItem(item); // 아이템 설정
      orderItem.setCount(10); // 수량 설정
      orderItem.setOrder(order); // 주문 설정
      order.getOrderItems().add(orderItem); // 주문에 아이템 추가
    }
    Member member = new Member(); // 새로운 회원 객체 생성
    memberRepository.save(member); // 회원 저장

    order.setMember(member); // 주문에 회원 설정
    orderRepository.save(order); // 주문 저장
    return order; // 생성된 주문 반환
  }

  // 영속성 전이 테스트
  @Test
  @DisplayName("영속성 전이 테스트") // 테스트 이름 설정
  public void cascadeTest() {
    Order order = new Order(); // 새로운 주문 객체 생성
    // 3개의 주문 아이템 생성
    for (int i = 0; i < 3; i++) {
      Item item = this.createItem(); // 아이템 생성
      itemRepository.save(item); // 아이템 저장
      OrderItem orderItem = new OrderItem(); // 주문 아이템 객체 생성
      orderItem.setItem(item); // 아이템 설정
      orderItem.setCount(10); // 수량 설정
      orderItem.setOrder(order); // 주문 설정
      order.getOrderItems().add(orderItem); // 주문에 아이템 추가
    }
    orderRepository.saveAndFlush(order); // 주문 저장 및 즉시 플러시
    em.clear(); // 영속성 컨텍스트 초기화

    // 저장한 주문을 ID로 조회
    Order saveOrder = orderRepository.findById(order.getId())
        .orElseThrow(EntityNotFoundException::new); // 존재하지 않을 경우 예외 발생
    assertEquals(3, saveOrder.getOrderItems().size()); // 주문 아이템 수 검증
  }

  // 고아 객체 제거 테스트
  @Test
  @DisplayName("고아객체 제거 테스트") // 테스트 이름 설정
  public void orphanRemovalTest() {
    Order order = this.createOrder(); // 주문 생성
    order.getOrderItems().remove(0); // 첫 번째 주문 아이템 제거
    em.flush(); // 변경 내용을 즉시 반영
  }

  // 지연 로딩 테스트
  @Test
  @DisplayName("지연 로딩 테스트") // 테스트 이름 설정
  public void lazyLoadingTest() {
    Order order = this.createOrder(); // 주문 생성
    Long orderItemId = order.getOrderItems().get(0).getId(); // 첫 번째 주문 아이템 ID 저장
    em.flush(); // 변경 내용을 즉시 반영
    em.clear(); // 영속성 컨텍스트 초기화

    // 주문 아이템 조회
    OrderItem orderItem = orderItemRepository.findById(orderItemId)
        .orElseThrow(EntityNotFoundException::new); // 존재하지 않을 경우 예외 발생
    System.out.println(
        "order class : " + orderItem.getOrder().getClass().getName()); // 주문 클래스 타입 출력
    System.out.println("===================================================");
    orderItem.getOrder().getOrderDate(); // 주문 날짜 접근 (지연 로딩 발생)
    System.out.println("===================================================");
  }
}
