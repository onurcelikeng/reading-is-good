package com.onurcelik.readingisgood.statistics.service;

import com.onurcelik.readingisgood.order.entity.Order;
import com.onurcelik.readingisgood.order.entity.OrderItem;
import com.onurcelik.readingisgood.order.repository.OrderItemRepository;
import com.onurcelik.readingisgood.order.repository.OrderRepository;
import com.onurcelik.readingisgood.statistics.dto.StatisticsOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public List<StatisticsOutput> getStatistics() {
        List<Order> orders = orderRepository.findAll();

        Map<StatisticsOutput, List<Order>> collect = orders.stream().collect(Collectors.groupingBy(d -> d.getCreateDate().getMonth()))
                .entrySet().stream().collect(Collectors.toMap(x -> {
                    int totalOrderCount = x.getValue().size();
                    int totalBookCount = 0;
                    double totalPurchasedAmount = 0;

                    List<Order> orderList = x.getValue();
                    for(Order order : orderList) {
                        List<OrderItem> orderItemList = orderItemRepository.findAllOrderItemByOrderId(order.getId());
                        for(OrderItem orderItem : orderItemList) {
                            totalBookCount += orderItem.getQuantity();
                            totalPurchasedAmount += orderItem.getQuantity() * orderItem.getUnitPrice();
                        }
                    }
                    return new StatisticsOutput(x.getKey().name(), totalOrderCount, totalBookCount, totalPurchasedAmount);
                }, Map.Entry::getValue));
        return new ArrayList<>(collect.keySet());
    }
}
