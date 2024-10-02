package com.keduit.shop.repository;

import com.keduit.shop.entity.ItemImg;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

//2024년 9월 23일 14:34
public interface ItemImgRepository extends
    JpaRepository<ItemImg, Long> {  // JpaRepository : Spring Data JPA의 Repository 인터페이스

  List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);

  ItemImg findByItemIdAndRegImgYn(Long itemId, String regImgYn);
}
