package com.keduit.shop.controller;

import com.keduit.shop.dto.ItemDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/thymeleaf") // "/thymeleaf" 경로에 대한 요청을 처리하는 컨트롤러야
public class ThymeleafExController {

  // 타임리프 예제를 보여주는 메서드
  @GetMapping("/ex01")
  public String thymeleafEx01(Model model) {
    model.addAttribute("data", "타임리프 예제"); // 뷰에 전달할 데이터를 모델에 추가
    return "thymeleafEx/thymeleafEx01"; // thymeleafEx01 뷰를 반환
  }

  // 상품 정보를 보여주는 타임리프 예제 메서드
  @GetMapping("/ex02")
  public String thymeleafEx02(Model model) {
    ItemDTO dto = new ItemDTO(); // 새로운 ItemDTO 객체 생성
    dto.setItemNm("타임리프 상품1"); // 상품 이름 설정
    dto.setItemDetail("타임리프 상품1 상세"); // 상품 상세 설명 설정
    dto.setPrice(20000); // 가격 설정
    //  dto.setRegTime(LocalDateTime.now()); // 현재 시간 설정
    model.addAttribute("itemDTO", dto); // 모델에 아이템 정보를 추가
    return "thymeleafEx/thymeleafEx02"; // thymeleafEx02 뷰를 반환
  }

  // 여러 상품 정보를 보여주는 타임리프 예제 메서드
  @GetMapping("/ex03")
  public String thymeleafEx03(Model model) {
    List<ItemDTO> list = new ArrayList<>(); // 아이템 DTO 리스트 생성

    // 1부터 10까지 테스트 상품 생성
    for (int i = 1; i <= 10; i++) {
      ItemDTO dto = new ItemDTO(); // 새로운 ItemDTO 객체 생성
      dto.setItemNm("테스트 상품" + i); // 상품 이름 설정
      dto.setItemDetail("테스트 상품 상세설명" + i); // 상품 상세 설명 설정
      dto.setPrice(25000); // 가격 설정
      // dto.setRegTime(LocalDateTime.now()); // 현재 시간 설정

      list.add(dto); // 리스트에 아이템 추가
    }
    model.addAttribute("list", list); // 모델에 상품 리스트 추가
    return "thymeleafEx/thymeleafEx03"; // thymeleafEx03 뷰를 반환
  }

  // 타임리프 예제 4를 보여주는 메서드
  @GetMapping("/ex04")
  public String thymeleafEx04() {
    return "thymeleafEx/thymeleafEx04"; // thymeleafEx04 뷰를 반환
  }

  // 타임리프 예제 5를 보여주는 메서드
  @GetMapping("/ex05")
  public String thymeleafEx05() {
    return "thymeleafEx/thymeleafEx05"; // thymeleafEx05 뷰를 반환
  }

}
