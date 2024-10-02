package com.keduit.shop.controller;

import com.keduit.shop.dto.ItemDTO;
import com.keduit.shop.dto.ItemSearchDTO;
import com.keduit.shop.entity.Item;
import com.keduit.shop.service.ItemService;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

//2024년 9월 23일 15:23
@Controller
@RequiredArgsConstructor
public class ItemController {

  private final ItemService itemService;

  // 새 아이템을 추가하기 위한 폼을 보여주는 메서드
  @GetMapping("/admin/item/new")
  public String itemForm(Model model) {
    // "item/itemForm" 뷰 이름을 반환하여 아이템 추가 폼을 렌더링
    model.addAttribute("itemDTO", new ItemDTO()); // 새 ItemDTO 객체를 모델에 추가
    return "item/itemForm";
  }

  //2024/09/24 10:13
  @PostMapping("/admin/item/new")
  public String itemnew(@Valid ItemDTO itemDTO, BindingResult bindingResult, Model model,
      @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {
    if (bindingResult.hasErrors()) {
      return "item/itemForm";
    }

    if (itemImgFileList.get(0).isEmpty() && itemDTO.getId() == null) {
      model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 입니다.");
      return "item/itemForm";
    }

    try {
      itemService.saveItem(itemDTO, itemImgFileList);
    } catch (Exception e) {
      model.addAttribute("errorMessage", "상품 등록 중 에러가 발생 하였습니다.");
      return "item/itemForm";
    }
    return "redirect:/";
  }

  @GetMapping("/admin/item/{itemId}")
  public String itemDetail(@PathVariable("itemId") Long itemId, Model model) {
    try {
      ItemDTO itemDTO = itemService.getItemDtl(itemId);
      model.addAttribute("itemDTO", itemDTO);
    } catch (EntityNotFoundException e) {
      model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
      model.addAttribute("itemDTO", new ItemDTO());

    }
    return "item/itemForm";
  }

  @PostMapping("/admin/item/{itemId}")
  public String itemUpdate(@Valid ItemDTO itemDTO, BindingResult bindingResult,
      @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList, Model model) {
    if (bindingResult.hasErrors()) {
      return "item/itemForm";
    }

    if (itemImgFileList.get(0).isEmpty() && itemDTO.getId() == null) {
      model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 입니다.");
      return "item/itemForm";

    }
    try {
      itemService.updateItem(itemDTO, itemImgFileList);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
      return "item/itemForm";
    }
    return "redirect:/"; // 메인으로 이동.
  }

  @GetMapping({"/admin/items", "admin/items/{page}"})
  public String itemManage(ItemSearchDTO itemSearchDTO,
      @PathVariable("page") Optional<Integer> page, Model model) {
    Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
    Page<Item> items = itemService.getAdminPage(itemSearchDTO, pageable);
    model.addAttribute("items", items);
    model.addAttribute("itemSearchDTO", itemSearchDTO);
    model.addAttribute("maxPage", 5);
    return "item/itemMng";
  }

  @GetMapping("/item/{itemId}")
  public String itemDtl(Model model, @PathVariable("itemId") Long itemId) {
    ItemDTO itemDTO = itemService.getItemDtl(itemId);
    model.addAttribute("item", itemDTO);
    return "item/itemDtl";
  }

}

