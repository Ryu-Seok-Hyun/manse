package com.keduit.shop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.keduit.shop.constant.ItemSellStatus;
import com.keduit.shop.dto.ItemDTO;
import com.keduit.shop.entity.Item;
import com.keduit.shop.entity.ItemImg;
import com.keduit.shop.repository.ItemImgRepository;
import com.keduit.shop.repository.ItemRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

// 2024년 09월 23일 16:46
@SpringBootTest
@Transactional
public class ItemServiceTests {

  @Autowired
  private ItemService itemService;

  @Autowired
  private ItemRepository itemRepository;

  @Autowired
  private ItemImgRepository itemImgRepository;

  List<MultipartFile> createMultipartFiles() throws Exception {

    List<MultipartFile> multipartFileList = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      String path = "/Users/seokhyun/Desktop/hi";
      String imageName = "image" + i + ".jpg";
      MockMultipartFile multipartFile = new MockMultipartFile(path, imageName, "image/jpg",
          new byte[]{1, 2, 3, 4});
      multipartFileList.add(multipartFile);
    }
    return multipartFileList;
  }

  @Test
  @DisplayName("상품 등록 테스트")
  @WithMockUser(username = "admin", roles = "ADMIN")
  void saveItem() throws Exception {
    ItemDTO itemDTO = new ItemDTO();
    itemDTO.setItemNm("테스트 상품");
    itemDTO.setItemDetail("내용");
    itemDTO.setPrice(12000);
    itemDTO.setItemSellStatus(ItemSellStatus.SELL);
    itemDTO.setStockNumber(100);

    List<MultipartFile> multipartFileList = createMultipartFiles();

    Long itemId = itemService.saveItem(itemDTO, multipartFileList);

    List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
    Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);

    assertEquals(itemDTO.getItemNm(), item.getItemNm());
    assertEquals(itemDTO.getItemSellStatus(), item.getItemSellStatus());
    assertEquals(itemDTO.getItemDetail(), item.getItemDetail());
    assertEquals(itemDTO.getPrice(), item.getPrice());
    assertEquals(itemDTO.getStockNumber(), item.getStockNumber());
    assertEquals(multipartFileList.get(0).getOriginalFilename(),
        itemImgList.get(0).getOriImgName());
  }

}
