package com.keduit.shop.controller;


import com.keduit.shop.dto.CartDetailDTO;
import com.keduit.shop.dto.CartItemDTO;
import com.keduit.shop.dto.CartOrderDTO;
import com.keduit.shop.service.CartService;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class CartController {

  private final CartService cartService;

  @PostMapping("/cart")
  public @ResponseBody ResponseEntity cart(@RequestBody @Valid CartItemDTO cartItemDTO,
      BindingResult bindingResult, Principal principal) {
    if (bindingResult.hasErrors()) {
      StringBuilder sb = new StringBuilder();
      List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
      for (FieldError fieldError : fieldErrorList) {
        sb.append(fieldError.getDefaultMessage());
      }
      return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
    }

    String email = principal.getName();
    Long cartItemId;

    try {
      cartItemId = cartService.addCart(cartItemDTO, email);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(cartItemId, HttpStatus.OK);
  }

  @GetMapping("/cart")
  public String cartList(Principal principal, Model model) {
    List<CartDetailDTO> cartDetailDTOList = cartService.getCartDetail(principal.getName());
    model.addAttribute("cartItems", cartDetailDTOList);
    return "cart/cartList";
  }

  @PatchMapping("/cartItem/{cartItemId}")
  public @ResponseBody ResponseEntity updateCartItem(@PathVariable("cartItemId") Long cartItemId,
      int count, Principal principal) {
    if (count <= 0) {
      return new ResponseEntity<>("최소 1개 이상 담아주세요.", HttpStatus.BAD_REQUEST);
    } else if (!cartService.validationCartItem(cartItemId, principal.getName())) {
      return new ResponseEntity<>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
    }
    cartService.updateCartItemCount(cartItemId, count);
    return new ResponseEntity(cartItemId, HttpStatus.OK);
  }

  @DeleteMapping("/cartItem/{cartItemId}")
  public @ResponseBody ResponseEntity deleteCartItem(@PathVariable("cartItemId") Long cartItemId,
      Principal principal) {
    if (!cartService.validationCartItem(cartItemId, principal.getName())) {
      return new ResponseEntity("삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
    }
    cartService.deleteCartItem(cartItemId);
    return new ResponseEntity(cartItemId, HttpStatus.OK);
  }

  @PostMapping("/cart/orders")
  public @ResponseBody ResponseEntity orderCartItem(
      @RequestBody CartOrderDTO cartOrderDTO, Principal principal) {
    List<CartOrderDTO> cartOrderDTOList = cartOrderDTO.getCartOrderDTOList();

    if (cartOrderDTOList == null || cartOrderDTOList.size() == 0) {
      return new ResponseEntity<String>("주문할 상품을 선택해 주세요.", HttpStatus.BAD_REQUEST);

    }

    for (CartOrderDTO cartOrderDTO1 : cartOrderDTOList) {
      if (!cartService.validationCartItem(cartOrderDTO1.getCartItemId(), principal.getName())) {
        return new ResponseEntity<>("주문 권한이 없습니다.", HttpStatus.FORBIDDEN);
      }
    }

    Long orderId = cartService.orderCartItem(cartOrderDTOList, principal.getName());
    return new ResponseEntity(orderId, HttpStatus.OK);
  }


}