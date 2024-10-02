package com.keduit.shop.repository;

import com.keduit.shop.constant.ItemSellStatus;
import com.keduit.shop.entity.Item;
import com.keduit.shop.entity.QItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest // 스프링 부트 통합 테스트 설정
public class ItemRepositoryTests {

  @Autowired
  ItemRepository itemRepository; // 아이템 관련 DB 작업을 위한 레포지토리 주입

  @PersistenceContext
  EntityManager em; // 데이터베이스와의 상호작용을 위한 엔티티 매니저 주입

  // 테스트용 아이템 리스트를 생성하는 메서드
  public void createItemList() {
    for (int i = 1; i <= 10; i++) {
      Item item = new Item(); // 새 아이템 객체 생성
      item.setItemNm("테스트 상품" + i); // 아이템 이름 설정
      item.setItemDetail("테스트 상품 상세 설명" + i); // 상세 설명 설정
      item.setPrice(15000 + 1000 * i); // 가격 설정 (15000부터 시작)
      item.setItemSellStatus(ItemSellStatus.SELL); // 판매 상태를 '판매중'으로 설정
      item.setStockNumber(i * 500); // 재고 수량 설정
      item.setUpdateTime(LocalDateTime.now()); // 현재 시간으로 수정 시간 설정
      itemRepository.save(item); // 아이템을 DB에 저장
    }
  }

  // 단일 상품 저장 테스트
  @Test
  @DisplayName("상품 저장 테스트") // 테스트 이름 설정
  public void createItemTest() {
    Item item = new Item(); // 새 아이템 객체 생성
    item.setItemNm("테스트 상품"); // 아이템 이름 설정
    item.setItemDetail("테스트 상품 상세 설명"); // 상세 설명 설정
    item.setPrice(15000); // 가격 설정
    item.setItemSellStatus(ItemSellStatus.SELL); // 판매 상태 설정
    item.setStockNumber(500); // 재고 수량 설정
    item.setUpdateTime(LocalDateTime.now()); // 현재 시간으로 수정 시간 설정
    Item saveItem = itemRepository.save(item); // 아이템 저장
    System.out.println(saveItem); // 저장된 아이템 출력
  }

  // 상품명을 기준으로 아이템 조회 테스트
  @Test
  @DisplayName("상품명 조회 테스트") // 테스트 이름 설정
  public void findByItemNmTest() {
    this.createItemList(); // 테스트용 아이템 리스트 생성
    List<Item> itemList = itemRepository.findByItemNm("테스트 상품"); // 아이템 조회
    for (Item item : itemList) {
      System.out.println(item); // 조회된 아이템 출력
    }
  }

  // 상품명 또는 상품 상세 설명으로 아이템 조회 테스트
  @Test
  @DisplayName("상품명, 상품상세설명으로 조회하기") // 테스트 이름 설정
  public void findByItemNmOrItemDetailTest() {
    List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품5", "테스트 상품 상세 설명2"); // 조회
    for (Item item : itemList) {
      System.out.println(item); // 조회된 아이템 출력
    }
  }

  // 가격이 특정 값보다 낮은 아이템 조회 테스트
  @Test
  @DisplayName("가격 LessThen 테스트") // 테스트 이름 설정
  public void findByPriceLessThanTest() {
    List<Item> itemList = itemRepository.findByPriceLessThan(19000); // 가격이 19000 이하인 아이템 조회
    for (Item item : itemList) {
      System.out.println(item); // 조회된 아이템 출력
    }
  }

  // 가격이 특정 값보다 낮고 내림차순으로 정렬하여 아이템 조회 테스트
  @Test
  @DisplayName("가격 내림차순 조회 테스트") // 테스트 이름 설정
  public void findByPriceLessThanOrderByPriceDescTest() {
    this.createItemList(); // 테스트용 아이템 리스트 생성
    List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(
        20000); // 가격이 20000 이하인 아이템 조회
    for (Item item : itemList) {
      System.out.println(item); // 조회된 아이템 출력
    }
  }

  // @Query를 사용하여 상품 상세 조회 테스트
  @Test
  @DisplayName("@Query를 이용한 상품 상세 조회 테스트") // 테스트 이름 설정
  public void findByItemDetailTest() {
    this.createItemList(); // 테스트용 아이템 리스트 생성
    List<Item> itemList = itemRepository.findByItemDetail("상세 설명3"); // 상세 설명으로 아이템 조회
    for (Item item : itemList) {
      System.out.println(item); // 조회된 아이템 출력
    }
  }

  // Native Query를 이용한 상품 조회 테스트
  @Test
  @DisplayName("nativeQuery 속성을 이용한 상품조회 테스트") // 테스트 이름 설정
  public void findByItemDetailByNativeTest() {
    this.createItemList(); // 테스트용 아이템 리스트 생성
    List<Item> itemList = itemRepository.findByItemDetailByNative("상세 설명5"); // 상세 설명으로 아이템 조회
    for (Item item : itemList) {
      System.out.println(item); // 조회된 아이템 출력
    }
  }

  // Querydsl을 사용한 아이템 조회 테스트
  @Test
  @DisplayName("Querydsl 조회 테스트") // 테스트 이름 설정
  public void querydslTest() {
    this.createItemList(); // 테스트용 아이템 리스트 생성
    JPAQueryFactory queryFactory = new JPAQueryFactory(em); // QueryFactory 생성
    QItem qitem = QItem.item; // QItem 객체 생성
    JPAQuery<Item> query = queryFactory.selectFrom(qitem) // 아이템 선택
        .where(qitem.itemSellStatus.eq(ItemSellStatus.SELL)) // 판매 상태가 '판매중'인 아이템
        .where(qitem.itemDetail.like("%" + "테스트 상품 상세 설명" + "%")) // 상세 설명에 특정 문자열이 포함된 아이템
        .orderBy(qitem.price.desc()); // 가격 기준 내림차순 정렬

    List<Item> itemList = query.fetch(); // 쿼리 실행

    for (Item item : itemList) {
      System.out.println(item); // 조회된 아이템 출력
    }
  }

  // QuerydslPredicateExecutor를 이용한 아이템 조회 테스트
  @Test
  @DisplayName("상품 Querydsl조회 테스트-QuerydslPredicateExecutor") // 테스트 이름 설정
  public void queryDslTest2() {
    this.createItemList(); // 테스트용 아이템 리스트 생성
    QItem item = QItem.item; // QItem 객체 생성
    BooleanBuilder booleanBuilder = new BooleanBuilder(); // 쿼리 조건을 동적으로 추가할 수 있는 빌더

    String itemDetail = "테스트 상품 상세 설명"; // 상세 설명 문자열
    int price = 20000; // 가격 조건
    String itemSellState = "SELL"; // 판매 상태

    booleanBuilder.and(item.itemDetail.like("%" + "테스트" + "%")); // 상세 설명에 '테스트' 포함
    booleanBuilder.and(item.price.gt(price)); // 가격이 특정 값보다 큰 조건 추가

    // 판매 상태가 '판매중'인 경우 조건 추가
    if (StringUtils.equals(itemSellState, ItemSellStatus.SELL)) {
      booleanBuilder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));
    }

    Pageable pageable = PageRequest.of(0, 5); // 페이지 설정 (첫 페이지, 5개 아이템)
    Page<Item> itemPageResult = itemRepository.findAll(booleanBuilder, pageable); // 조건에 맞는 아이템 조회
    System.out.println("total element : " + itemPageResult.getTotalElements()); // 총 아이템 수 출력
    List<Item> resultItemList = itemPageResult.getContent(); // 조회된 아이템 리스트
    for (Item resultItem : resultItemList) {
      System.out.println(resultItem); // 조회된 아이템 출력
    }
  }
}
