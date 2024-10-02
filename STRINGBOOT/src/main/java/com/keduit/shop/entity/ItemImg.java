package com.keduit.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//2024년 09월 23일 09:25
@Entity
@Getter
@Setter
@ToString

public class ItemImg extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "item_img_id")
  private Long id;

  private String imgName; // 이미지 파일이름

  private String oriImgName;  // 원본 이미지 이름

  private String imgUrl;  // 저장된 이미지 경로

  private String regImgYn; // 대표 이미지 여부

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id")
  private Item item;

  public void updateItemImg(String oriImgName, String imgName, String imgUrl) {
    this.oriImgName = oriImgName;
    this.imgName = imgName;
    this.imgUrl = imgUrl;
  }


}
