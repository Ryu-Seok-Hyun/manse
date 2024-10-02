package com.keduit.shop.controller;

import com.keduit.shop.dto.ItemSearchDTO;
import com.keduit.shop.dto.MainItemDTO;
import com.keduit.shop.service.ItemService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

  private final ItemService itemService;


  // 메인 페이지를 보여주는 메서드야!
  @GetMapping("/")
  public String main(ItemSearchDTO itemSearchDTO, Optional<Integer> page, Model model) {

    Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
    Page<MainItemDTO> items = itemService.getMainItemPage(itemSearchDTO, pageable);
    model.addAttribute("items", items);
    model.addAttribute("itemSearchDTO", itemSearchDTO);
    model.addAttribute("maxPage", 5);
    // "main" 뷰를 반환해서 메인 페이지를 띄워줄 거야.
    return "main";
  }

}
