package com.keduit.shop.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MainItemDTO {

  private Long id;
  private String itemNm;
  private String itemDetail;
  private String imgURL;
  private Integer price;

  @QueryProjection

  public MainItemDTO(Long id, String itemNm, String itemDetail, String imgURL, Integer price) {
    this.id = id;
    this.itemNm = itemNm;
    this.itemDetail = itemDetail;
    this.imgURL = imgURL;
    this.price = price;
  }
}
