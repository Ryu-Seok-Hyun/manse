package com.keduit.shop.dto;

import com.keduit.shop.constant.OrderStatus;
import com.keduit.shop.entity.Order;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
//주문내역의 주문아이디 주문상태 주문날짜 이런걸 담을 얘. 히스토리
public class OrderHistDTO {

  public OrderHistDTO(Order order) {
    this.orderId = order.getId();
    this.orderDate = order.getOrderDate()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    this.orderStatus = order.getOrderStatus();
  }

  private Long orderId; // 주문아이디

  private String orderDate;

  private OrderStatus orderStatus; // 주문상태

  private List<OrderItemDTO> orderItemDTOList = new ArrayList<>();

  public void addOrderItemDTO(OrderItemDTO orderItemDTO) {
    orderItemDTOList.add(orderItemDTO);
  }

}
