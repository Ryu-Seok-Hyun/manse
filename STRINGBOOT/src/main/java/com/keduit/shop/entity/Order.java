package com.keduit.shop.entity;

import com.keduit.shop.constant.OrderStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity // JPA에서 엔티티로 매핑되는 클래스
@Getter // 모든 필드에 대한 getter 메서드를 자동 생성
@Setter // 모든 필드에 대한 setter 메서드를 자동 생성
@ToString // 객체의 문자열 표현을 자동으로 생성
@Table(name = "orders") // 데이터베이스 테이블 이름을 "orders"로 설정
public class Order extends BaseEntity {

  @Id // 이 필드는 기본 키임을 나타냄
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 주문번호를 자동 생성
  @Column(name = "order_id") // 데이터베이스에서 사용할 컬럼 이름을 "order_id"로 설정
  private Long id; // 주문번호

  @ManyToOne(fetch = FetchType.LAZY) // 여러 주문이 하나의 회원에 속함 (다대일 관계)
  @JoinColumn(name = "member_id") // 외래 키 이름을 "member_id"로 설정
  private Member member; // 주문한 회원 정보

  // all: 부모 엔티티의 영속성 상태 변화가 자식 엔티티에 모두 전이됨
  // orphanRemoval = true: 자식 엔티티가 부모 엔티티와 분리될 때 자동으로 삭제됨
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,
      orphanRemoval = true, fetch = FetchType.LAZY) // 주문과 관련된 주문 아이템 리스트
  private List<OrderItem> orderItems = new ArrayList<>(); // 주문 아이템 리스트 초기화

  private LocalDateTime orderDate; // 주문일

  @Enumerated(EnumType.STRING) // 주문 상태를 문자열로 저장
  private OrderStatus orderStatus; // 주문 상태

  // private LocalDateTime regTime; // 등록 시간 (주석 처리됨)
  // private LocalDateTime updateTime; // 수정 시간 (주석 처리됨)

  // 주문상품 정보를 order, odrerItem에 setting을 해줌.
  public void addOrderItem(OrderItem orderItem) {
    orderItems.add(orderItem);
    orderItem.setOrder(this);
  }

  public static Order createOrder(Member member, List<OrderItem> orderItemList) {
    Order order = new Order();
    order.setMember(member);
    for (OrderItem orderItem : orderItemList) {
      order.addOrderItem(orderItem);
    }
    order.setOrderStatus(OrderStatus.ORDER);
    order.setOrderDate(LocalDateTime.now());
    return order;
  }

  public int getTotalPrice() {
    int totalPrice = 0;
    for (OrderItem orderItem : orderItems) {
      totalPrice += orderItem.getTotalPrice();
    }
    return totalPrice;
  }

  // 주문 취소시 주문상태를 CANCEL로 변경
  public void cancelOrder() {
    this.orderStatus = OrderStatus.CANCEL;

    for (OrderItem orderItem : orderItems) {
      orderItem.cancel(); // 주문 취소시 주문 수만큼 상품의 재고를 더해줌
    }
  }

}
