package com.ssafy.TogetherBuyMain.shop.repository.order;

import com.ssafy.TogetherBuyMain.shop.entity.order.DeliveryStatus;
import com.ssafy.TogetherBuyMain.shop.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByDeliveryStatus(DeliveryStatus deliveryStatus);
}
