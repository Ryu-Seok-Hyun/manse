package com.keduit.shop.repository;

import com.keduit.shop.constant.ItemSellStatus;
import com.keduit.shop.dto.ItemSearchDTO;
import com.keduit.shop.dto.MainItemDTO;
import com.keduit.shop.dto.QMainItemDTO;
import com.keduit.shop.entity.Item;
import com.keduit.shop.entity.QItem;
import com.keduit.shop.entity.QItemImg;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

// 2024/09/24 아이템 검색창
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

  private JPAQueryFactory queryFactory;

  public ItemRepositoryCustomImpl(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(em);
  }

  private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus) {
    return searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);
  }

  // all : 전체, 1d : 최근하루, 1w : 최근1주일, 1m : 최근1개월, 3m : 최근3개월, 6m : 최근6개월
  private BooleanExpression regDtsAfter(String searchDateType) {
    LocalDateTime dateTime = LocalDateTime.now();
    if (StringUtils.equals("all", searchDateType) || searchDateType == null) {
      return null;
    } else if (StringUtils.equals("1d", searchDateType)) {
      dateTime = dateTime.minusDays(1);
    } else if (StringUtils.equals("1w", searchDateType)) {
      dateTime = dateTime.minusWeeks(1);
    } else if (StringUtils.equals("1m", searchDateType)) {
      dateTime = dateTime.minusMonths(1);
    } else if (StringUtils.equals("6m", searchDateType)) {
      dateTime = dateTime.minusMonths(1);
    }
    return QItem.item.regTime.after(dateTime);
  }

  private BooleanExpression searchByLike(String searchBy, String searchQuery) {
    if (StringUtils.equals("itemNm", searchBy)) {
      return QItem.item.itemNm.like("%" + searchQuery + "%");
    } else if (StringUtils.equals("createBy", searchBy)) {
      return QItem.item.createdBy.like("%" + searchQuery + "%");
    }
    return null;
  }

  // Main 화면에서 상품 이름으로만 검색할수 있게 해줌.
  private BooleanExpression itemNmLike(String searchQuery) {
    return StringUtils.isEmpty(searchQuery) ? null
        : QItem.item.itemNm.like("%" + searchQuery + "%");
  }


  @Override
  public Page<Item> getAdminItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable) {
    System.out.println("[getAdminItemPage]itemSearchDTO ============> " + itemSearchDTO);
    System.out.println("[ '' ] pageable ============> " + pageable);
    List<Item> result = queryFactory
        .selectFrom(QItem.item)
        .where(regDtsAfter(itemSearchDTO.getSearchDateType()),
            searchSellStatusEq(itemSearchDTO.getSearchSellStatus()),
            searchByLike(itemSearchDTO.getSearchBy(), itemSearchDTO.getSearchQuery()))
        .orderBy(QItem.item.id.desc())
        .offset(pageable.getOffset()) // 데이터를 가지고 올 시작 인덱스
        .limit(pageable.getPageSize()) // 한 번에 가지고 올 최대 갯수
        .fetch();

    Long total = queryFactory
        .select(Wildcard.count)
        .from(QItem.item)
        .where(regDtsAfter(itemSearchDTO.getSearchDateType()),
            searchSellStatusEq(itemSearchDTO.getSearchSellStatus()),
            searchByLike(itemSearchDTO.getSearchBy(), itemSearchDTO.getSearchQuery()))
        .fetchOne(); // 하나의 결과를 가져옴.

    return new PageImpl<>(result, pageable, total);

  }

  @Override
  public Page<MainItemDTO> getMainItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable) {
    QItem item = QItem.item;
    QItemImg itemImg = QItemImg.itemImg;

    List<MainItemDTO> result = queryFactory
        .select(new QMainItemDTO(item.id, item.itemNm, item.itemDetail, itemImg.imgUrl, item.price)
        ).from(itemImg).join(itemImg.item, item).where(itemImg.regImgYn.eq("Y"))
        .where(itemNmLike(itemSearchDTO.getSearchQuery())).orderBy(item.id.desc())
        .offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();

    Long total = queryFactory.select(Wildcard.count)
        .from(itemImg).join(itemImg.item, item)
        .where(itemImg.regImgYn.eq("Y"))
        .where(itemNmLike(itemSearchDTO.getSearchQuery()))
        .fetchOne();
    return new PageImpl<>(result, pageable, total);
  }
}
