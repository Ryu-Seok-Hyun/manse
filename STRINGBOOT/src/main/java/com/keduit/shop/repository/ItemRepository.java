package com.keduit.shop.repository;
//3번 >> 다음 테스트 하러가기. 하나씩하면서 테스트 하기.

import com.keduit.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Long>,
    QuerydslPredicateExecutor<Item>, ItemRepositoryCustom { // Item 엔티티에 대한 CRUD 및 Querydsl 지원

  // 아이템 이름으로 아이템을 찾는 메서드
  List<Item> findByItemNm(String itemNm);

  // 아이템 이름 또는 아이템 상세 설명으로 아이템을 찾는 메서드
  List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

  // 주어진 가격보다 낮은 가격의 아이템 리스트를 찾는 메서드
  List<Item> findByPriceLessThan(Integer price);

  // 주어진 가격보다 낮은 가격의 아이템을 가격 내림차순으로 정렬하여 찾는 메서드
  List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

  // JPQL을 사용하여 아이템 상세 설명에 특정 문자열이 포함된 아이템 리스트를 가격 내림차순으로 찾는 메서드
  @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
  List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

  // 네이티브 쿼리를 사용하여 아이템 상세 설명에 특정 문자열이 포함된 아이템 리스트를 가격 내림차순으로 찾는 메서드
  @Query(value = "select * from item i where i.item_detail like " +
      "%:itemDetail% order by i.price desc", nativeQuery = true)
  List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);
}
