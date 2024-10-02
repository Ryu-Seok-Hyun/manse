package com.keduit.shop.dto;


import com.keduit.shop.constant.ItemSellStatus;
import com.keduit.shop.entity.Item;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
// 2024년 09월 23일 09:41

@Getter
@Setter
@ToString
public class ItemDTO { // /entity/item 보면서 하면 됨.

  private Long id;

  @NotBlank(message = "상품명은 필수 입력 입니다.")
  private String itemNm;

  @NotNull(message = "가격은 필수 입력 입니다.")
  private Integer price;

  @NotNull(message = "재고는 필수 입력 입니다.")
  private Integer stockNumber;

  @NotBlank(message = "이름은 필수 입력 입니다.")
  private String itemDetail;

  private ItemSellStatus itemSellStatus;

  private List<ItemImgDTO> itemImgDTOList = new ArrayList<>();

  private List<Long> itemImgIds = new ArrayList<>();

  private static ModelMapper modelMapper = new ModelMapper();

  public Item createItem() {
    return modelMapper.map(this, Item.class);
  }

  public static ItemDTO of(Item item) {
    return modelMapper.map(item, ItemDTO.class);
  }


}
