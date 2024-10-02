package com.keduit.shop.dto;

import com.keduit.shop.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

//2024년 09월 23일 09:40
@Getter
@Setter
@ToString

public class ItemImgDTO {

  private Long id; // 이미지 ID

  private String imgName; // 이미지 파일 이름

  private String oriImgName; // 원본 이미지 이름

  private String imgUrl; // 저장된 이미지 URL

  private String regImgYn; // 대표 이미지 여부

  private static ModelMapper modelMapper = new ModelMapper();

  public static ItemImgDTO of(ItemImg itemImg) {
    return modelMapper.map(itemImg, ItemImgDTO.class);

  }


}
