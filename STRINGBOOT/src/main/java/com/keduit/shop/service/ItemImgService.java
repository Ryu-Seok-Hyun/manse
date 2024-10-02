package com.keduit.shop.service;


import com.keduit.shop.entity.ItemImg;
import com.keduit.shop.repository.ItemImgRepository;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

//2024년 9월 23일 14:32
@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {

  private final ItemImgRepository itemImgRepository;

  private final FileService fileService;

  @Value("${itemImgLocation}")
  private String itemImgLocation;

  public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception {

    String originalFileName = itemImgFile.getOriginalFilename();
    String imgName = "";
    String imgUrl = "";

    // 파일 업로드
    if (!StringUtils.isEmpty(originalFileName)) {
      imgName = fileService.uploadFile(itemImgLocation, originalFileName, itemImgFile.getBytes());
      imgUrl = "/images/item/" + imgName;
    }

    // 상품이미지 정보 저장
    itemImg.updateItemImg(originalFileName, imgName, imgUrl);
    itemImgRepository.save(itemImg);

  }

  public void updateItemImg() {
  }

  // 2024/09/24 10:38
  public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception {
    if (!itemImgFile.isEmpty()) {
      ItemImg savedItemImg = itemImgRepository.findById(itemImgId)
          .orElseThrow(EntityNotFoundException::new);

      //기존 이미지 삭제
      if (!StringUtils.isEmpty(savedItemImg.getImgName())) {
        fileService.deleteFile(itemImgLocation + "/" + savedItemImg.getImgName());
      }

      String oriImgName = itemImgFile.getOriginalFilename();
      String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
      String imgUrl = "/images/item/" + imgName;
      savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);
    }
  }
}
