package com.onurcelik.readingisgood.order.controller;

import com.onurcelik.readingisgood.core.dto.ResponseDTO;
import com.onurcelik.readingisgood.core.exception.BusinessException;
import com.onurcelik.readingisgood.order.dto.order.OrderInput;
import com.onurcelik.readingisgood.order.dto.order.OrderSummaryOutput;
import com.onurcelik.readingisgood.order.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Order")
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseDTO<OrderSummaryOutput> createOrder(@RequestBody @Valid OrderInput input) throws BusinessException {
        OrderSummaryOutput orderSummaryOutput = orderService.createOrder(input);
        return new ResponseDTO<>(HttpStatus.OK, orderSummaryOutput);
    }

    @GetMapping
    public ResponseDTO<List<OrderSummaryOutput>> getOrdersBetweenStartDateAndEndDate(
            @PathParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @PathParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<OrderSummaryOutput> orderSummaryOutputs = orderService.getOrders(startDate, endDate);
        return new ResponseDTO<>(HttpStatus.OK, orderSummaryOutputs);
    }
}
