package com.keduit.shop.entity;
//2번

import com.keduit.shop.constant.ItemSellStatus;
import com.keduit.shop.dto.ItemDTO;
import com.keduit.shop.exception.OutOfStockException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity // JPA에서 엔티티로 매핑되는 클래스
@Setter // 모든 필드에 대한 setter 메서드를 자동 생성
@ToString // 객체의 문자열 표현을 자동으로 생성
@Getter // 모든 필드에 대한 getter 메서드를 자동 생성
public class Item extends BaseEntity {

  @Id // 이 필드는 기본 키임을 나타냄
  @Column(name = "item_id") // 데이터베이스에서 사용할 컬럼 이름을 "item_id"로 설정
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 상품코드를 자동 생성
  private Long id; // 상품코드

  @Column(nullable = false, length = 50) // null이 허용되지 않으며 최대 길이는 50
  private String itemNm; // 아이템 이름 (상품명)

  @Column(nullable = false) // null이 허용되지 않음
  private int price; // 상품 가격

  @Column(nullable = false) // null이 허용되지 않음
  private int stockNumber; // 재고 수량

  @Lob // 대량의 문자열을 저장할 때 사용
  @Column(nullable = false) // null이 허용되지 않음
  private String itemDetail; // 상품 상세 설명

  @Enumerated(EnumType.STRING) // enum 값을 문자열로 저장
  private ItemSellStatus itemSellStatus; // 상품 판매 상태

  public void updateItem(ItemDTO itemDTO) {
    this.itemNm = itemDTO.getItemNm();
    this.price = itemDTO.getPrice();
    this.stockNumber = itemDTO.getStockNumber();
    this.itemDetail = itemDTO.getItemDetail();
    this.itemSellStatus = itemDTO.getItemSellStatus();
    // this.updateTime = LocalDateTime.now(); // 수정 시간 ��신
  }

  // 1. 변경감지 : update가 일어남
  // 2. 주문수량이 재고수량을 넘지 않도록.
  public void removeStock(int stockNumber) {
    int restStock = this.stockNumber - stockNumber;
    if (restStock < 0) {
      throw new OutOfStockException("상품의 재고가 부족합니다.(현재 재고 수량 : " + this.stockNumber + ")");
    }
    this.stockNumber = restStock;
  }

  public void addStock(int stockNumber) {
    this.stockNumber += stockNumber;
  }

  // @CreationTimestamp // 등록 시간을 자동으로 기록
  // private LocalDateTime regTime; // 등록 시간

  // private LocalDateTime updateTime; // 수정 시간
}
