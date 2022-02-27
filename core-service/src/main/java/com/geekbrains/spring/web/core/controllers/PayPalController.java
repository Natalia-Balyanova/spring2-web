package com.geekbrains.spring.web.core.controllers;

import com.geekbrains.spring.web.core.entities.OrderStatus;
import com.geekbrains.spring.web.core.services.OrderService;
import com.geekbrains.spring.web.core.services.PayPalService;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCaptureRequest;
import com.paypal.orders.OrdersCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/paypal")
@RequiredArgsConstructor
public class PayPalController {
    private final PayPalHttpClient payPalClient;
    private final OrderService orderService;
    private final PayPalService payPalService;

    @PostMapping("/create/{orderId}")
    @Operation(
            summary = "Запрос на создание заказа по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = List.class))
                    )
            }
    )
    public ResponseEntity<?> createOrder(@PathVariable Long orderId) throws IOException {
        Optional<com.geekbrains.spring.web.core.entities.Order> orderOptional = orderService.findById(orderId);
        if(orderOptional.isPresent() && orderOptional.get().getOrderStatus().equals(OrderStatus.CREATED)) {
            OrdersCreateRequest request = new OrdersCreateRequest();
            request.prefer("return=representation");
            request.requestBody(payPalService.createOrderRequest(orderId));
            HttpResponse<Order> response = payPalClient.execute(request);
            return new ResponseEntity<>(response.result().id(), HttpStatus.valueOf(response.statusCode()));
        }
        return new ResponseEntity<>("Order is invalid!", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/capture/{payPalId}")
    @Operation(
            summary = "Запрос на получение ответа по заказу",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ResponseEntity.class))
                    )
            }
    )
    public ResponseEntity<?> captureOrder(@PathVariable String payPalId) throws IOException {
        OrdersCaptureRequest request = new OrdersCaptureRequest(payPalId);
        request.requestBody(new OrderRequest());

        HttpResponse<Order> response = payPalClient.execute(request);
        Order payPalOrder = response.result();
        if ("COMPLETED".equals(payPalOrder.status())) {
            long orderId = Long.parseLong(payPalOrder.purchaseUnits().get(0).referenceId());
            orderService.changeStatus(OrderStatus.PAYED, orderId);
            // Optional<com.geekbrains.spring.web.core.entities.Order> orderOptional = orderService.findById(orderId);
            return new ResponseEntity<>("Order completed!", HttpStatus.valueOf(response.statusCode()));
        }
        return new ResponseEntity<>(payPalOrder, HttpStatus.valueOf(response.statusCode()));
    }
}