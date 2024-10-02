package com.keduit.shop.service;

import com.keduit.shop.dto.ItemDTO;
import com.keduit.shop.dto.ItemImgDTO;
import com.keduit.shop.dto.ItemSearchDTO;
import com.keduit.shop.dto.MainItemDTO;
import com.keduit.shop.entity.Item;
import com.keduit.shop.entity.ItemImg;
import com.keduit.shop.repository.ItemImgRepository;
import com.keduit.shop.repository.ItemRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

//2024년 9월 23일 15:05
@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

  private final ItemRepository itemRepository;
  private final ItemImgRepository itemImgRepository;
  private final ItemImgService itemImgService;

  public Long saveItem(ItemDTO itemDTO, List<MultipartFile> itemImgFileList) throws Exception {

    // 상품등록
    Item item = itemDTO.createItem();
    itemRepository.save(item);

    // 이미지 등록
    for (int i = 0; i < itemImgFileList.size(); i++) {
      ItemImg itemImg = new ItemImg();
      itemImg.setItem(item);
      if (i == 0) {
        itemImg.setRegImgYn("Y");
      } else {
        itemImg.setRegImgYn("N");
      }
      itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));

    }
    return item.getId();
  }

  // 2024/09/24 09:32
  //readOnly = true : 상품 데이터를 읽기만 하므로 JPA에게 더티체킹(변경감지)를 하지 않도록 설정. -> 성능상 이점이 발생.
  @Transactional(readOnly = true)
  public ItemDTO getItemDtl(Long itemId) {
    List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
    List<ItemImgDTO> itemImgDTOList = new ArrayList<>();
    for (ItemImg itemImg : itemImgList) {
      ItemImgDTO itemImgDTO = ItemImgDTO.of(itemImg);
      itemImgDTOList.add(itemImgDTO);
    }

    Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
    ItemDTO itemDTO = ItemDTO.of(item);
    itemDTO.setItemImgDTOList(itemImgDTOList);

    return itemDTO;

  }

  public Long updateItem(ItemDTO itemDTO, List<MultipartFile> itemImgFileList) throws Exception {

    //  상품수정
    Item item = itemRepository.findById(itemDTO.getId())
        .orElseThrow(EntityNotFoundException::new);
    item.updateItem(itemDTO);

    //  이미지 수정
    List<Long> itemImgIds = itemDTO.getItemImgIds();

    //이미지 등록
    for (int i = 0; i < itemImgFileList.size(); i++) {
      System.out.println(
          "****" + i + "------" + itemImgIds.get(i) + "---" + itemImgFileList.get(i));
      itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
    }
    return item.getId();
  }

  @Transactional(readOnly = true)
  public Page<Item> getAdminPage(ItemSearchDTO itemSearchDTO, Pageable pageable) {
    return itemRepository.getAdminItemPage(itemSearchDTO, pageable);
  }

  @Transactional(readOnly = true)
  public Page<MainItemDTO> getMainItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable) {
    return itemRepository.getMainItemPage(itemSearchDTO, pageable);
  }
}
