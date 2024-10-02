package com.keduit.shop.controller;

import com.keduit.shop.dto.OrderDTO;
import com.keduit.shop.dto.OrderHistDTO;
import com.keduit.shop.service.OrderService;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @PostMapping("/order")
  public @ResponseBody ResponseEntity order
      (@RequestBody @Valid OrderDTO orderDTO, BindingResult bindingResult, Principal principal) {
    System.out.println("orderController =======> " + orderDTO);
    if (bindingResult.hasErrors()) {
      StringBuilder sb = new StringBuilder();
      List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
      for (FieldError fieldError : fieldErrorList) {
        sb.append(fieldError.getDefaultMessage()); //에러내용을 빌더에 담기
      }
      return new ResponseEntity(sb.toString(), HttpStatus.BAD_REQUEST);
    }
    String email = principal.getName(); //로그인한 회원의 유일키 가져오기
    Long orderId;
    try {
      orderId = orderService.order(orderDTO, email); //주문내역 pk 가져오기
    } catch (Exception e) {
      return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity(orderId, HttpStatus.OK);
  }

  @GetMapping({"/orders", "/orders/{page}"})
  public String orderHist(@PathVariable("page") Optional<Integer> page, Principal principal,
      Model model) {
    Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4);
    Page<OrderHistDTO> orderHistDTOPage = orderService.getOrderList(principal.getName(), pageable);
    model.addAttribute("orders", orderHistDTOPage);
    model.addAttribute("page", pageable.getPageNumber());
    model.addAttribute("maxPage", 5); //한화면에 보여줄 페이지 수
    return "order/orderHist";
  }

  @PostMapping("/order/{orderId}/cancel")
  public @ResponseBody ResponseEntity cancelOrder(@PathVariable("orderId") Long orderId,
      Principal principal) {

    if (!orderService.validateOrder(orderId, principal.getName())) {
      return new ResponseEntity<String>("주문 취소 권한이 없습니다.", HttpStatus.FORBIDDEN);
    }
    orderService.cancelOrder(orderId);
    return new ResponseEntity(orderId, HttpStatus.OK);
  }
}
