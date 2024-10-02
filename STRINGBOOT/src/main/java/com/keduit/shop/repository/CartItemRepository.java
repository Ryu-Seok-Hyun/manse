package com.keduit.shop.repository;

import com.keduit.shop.dto.CartDetailDTO;
import com.keduit.shop.entity.CartItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends
    // CartItem 엔티티에 대한 CRUD (Create, Read, Update, Delete) 작업을 지원하는 리포지토리 인터페이스
    JpaRepository<CartItem, Long> {


  CartItem findByCartIdAndItemId(Long cartId, Long itemId);

  @Query(
      "select new com.keduit.shop.dto.CartDetailDTO (ci.id, i.itemNm, i.price, ci.count, im.imgUrl) "
          +
          "from CartItem ci, ItemImg im " + "join ci.item i " + "where ci.cart.id = :cartId " +
          "and im.item.id = ci.item.id " + "and im.regImgYn = 'Y' " + "order by ci.regTime desc")
  List<CartDetailDTO> findCartDetailDTOList(Long cartId);


}
