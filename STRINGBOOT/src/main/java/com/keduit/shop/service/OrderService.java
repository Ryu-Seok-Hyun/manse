package com.keduit.shop.service;


import com.keduit.shop.dto.OrderDTO;
import com.keduit.shop.dto.OrderHistDTO;
import com.keduit.shop.dto.OrderItemDTO;
import com.keduit.shop.entity.Item;
import com.keduit.shop.entity.ItemImg;
import com.keduit.shop.entity.Member;
import com.keduit.shop.entity.Order;
import com.keduit.shop.entity.OrderItem;
import com.keduit.shop.repository.ItemImgRepository;
import com.keduit.shop.repository.ItemRepository;
import com.keduit.shop.repository.MemberRepository;
import com.keduit.shop.repository.OrderRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

@Service
@Transactional
@RequiredArgsConstructor

public class OrderService {

  private final ItemRepository itemRepository;
  private final MemberRepository memberRepository;
  private final OrderRepository orderRepository;
  private final ItemImgRepository itemImgRepository;

  public Long order(OrderDTO orderDTO, String email) {

    System.out.println("order =>>>>" + orderDTO + ", " + email);
    Item item = itemRepository.findById(orderDTO.getItemId())
        .orElseThrow(EntityNotFoundException::new);
    Member member = memberRepository.findByEmail(email);

    List<OrderItem> orderItemList = new ArrayList<>();
    OrderItem orderItem = OrderItem.createOrderItem(item, orderDTO.getCount());
    orderItemList.add(orderItem);

    Order order = Order.createOrder(member, orderItemList);
    orderRepository.save(order);

    return order.getId();
  }

  public Page<OrderHistDTO> getOrderList(String email, Pageable pageable) {
    List<Order> orders = orderRepository.findOrders(email, pageable);
    Long totalCount = orderRepository.countOrder(email);

    List<OrderHistDTO> orderHistDTOS = new ArrayList<>();
    for (Order order : orders) {
      OrderHistDTO orderHistDTO = new OrderHistDTO(order);
      List<OrderItem> orderItems = order.getOrderItems();

      for (OrderItem orderItem : orderItems) {
        ItemImg itemImg = itemImgRepository.findByItemIdAndRegImgYn(orderItem.getItem().getId(),
            "Y");
        OrderItemDTO orderItemDTO = new OrderItemDTO(orderItem, itemImg.getImgUrl());
        orderHistDTO.addOrderItemDTO(orderItemDTO);
      }
      orderHistDTOS.add(orderHistDTO);
    }
    return new PageImpl<OrderHistDTO>(orderHistDTOS, pageable, totalCount);
  }

  @Transactional(readOnly = true)
  public boolean validateOrder(Long orderId, String email) {
    Member curMember = memberRepository.findByEmail(email);
    Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
    Member savedMember = order.getMember();

    if (!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())) {
      return false;
    }
    return true;
  }

  public void cancelOrder(Long orderId) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(EntityNotFoundException::new);
    order.cancelOrder();
  }

  public long orders(List<OrderDTO> orderDtoList, String email) {
    Member member = memberRepository.findByEmail(email);
    List<OrderItem> orderItems = new ArrayList<>();

    for (OrderDTO orderDTO : orderDtoList) {
      Item item = itemRepository.findById(orderDTO.getItemId())
          .orElseThrow(EntityNotFoundException::new);

      OrderItem orderItem = OrderItem.createOrderItem(item, orderDTO.getCount());
      orderItems.add(orderItem);
    }
    Order order = Order.createOrder(member, orderItems);
    orderRepository.save(order);

    return order.getId();
  }


}
