/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shopap.order;

/**
 *
 * @author Mateusz
 */
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mycompany.shopap.customer.Customer;
import com.mycompany.shopap.customer.CustomerService;
import com.mycompany.shopap.customer.CustomerResponse;
import com.mycompany.shopap.product.Product;
import com.mycompany.shopap.product.ProductService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository,
                        CustomerService customerService,
                        ProductService productService) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.productService = productService;
    }

    @Transactional
    public OrderDetailsResponse createOrder(OrderCreateRequest request) {
        Customer customer = customerService.getByIdOrThrow(request.getCustomerId());

        Order order = new Order();
        order.setCustomer(customer);
        order.setCreatedAt(LocalDateTime.now());

        BigDecimal totalNet = BigDecimal.ZERO;
        BigDecimal totalGross = BigDecimal.ZERO;

        for (OrderItemRequest itemReq : request.getItems()) {
            Product product = productService.getByIdOrThrow(itemReq.getProductId());

            BigDecimal quantity = BigDecimal.valueOf(itemReq.getQuantity());
            BigDecimal netAmount = product.getNetPrice().multiply(quantity);
            BigDecimal grossAmount = netAmount.multiply(
                    BigDecimal.ONE.add(product.getVatRate())
            );

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemReq.getQuantity());
            item.setNetAmount(netAmount);
            item.setGrossAmount(grossAmount);

            order.addItem(item);

            totalNet = totalNet.add(netAmount);
            totalGross = totalGross.add(grossAmount);
        }

        order.setTotalNet(totalNet);
        order.setTotalGross(totalGross);

        Order saved = orderRepository.save(order);

        return toDetailsResponse(saved);
    }

    @Transactional(readOnly = true)
    public OrderDetailsResponse getOrderDetails(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + orderId));
        return toDetailsResponse(order);
    }

    private OrderDetailsResponse toDetailsResponse(Order order) {
        OrderDetailsResponse resp = new OrderDetailsResponse();
        resp.setOrderId(order.getId());
        resp.setCreatedAt(order.getCreatedAt());
        resp.setTotalNet(order.getTotalNet());
        resp.setTotalGross(order.getTotalGross());

        CustomerResponse cr = new CustomerResponse();
        cr.setId(order.getCustomer().getId());
        cr.setFirstName(order.getCustomer().getFirstName());
        cr.setLastName(order.getCustomer().getLastName());
        cr.setEmail(order.getCustomer().getEmail());
        cr.setPhone(order.getCustomer().getPhone());
        cr.setStreet(order.getCustomer().getStreet());
        cr.setCity(order.getCustomer().getCity());
        cr.setPostalCode(order.getCustomer().getPostalCode());
        cr.setCountry(order.getCustomer().getCountry());
        resp.setCustomer(cr);

        List<OrderItemDetails> items = order.getItems().stream()
                .map(item -> {
                    OrderItemDetails d = new OrderItemDetails();
                    d.setProductId(item.getProduct().getId());
                    d.setProductName(item.getProduct().getName());
                    d.setNetPrice(item.getProduct().getNetPrice());
                    d.setVatRate(item.getProduct().getVatRate());
                    d.setQuantity(item.getQuantity());
                    d.setNetAmount(item.getNetAmount());
                    d.setGrossAmount(item.getGrossAmount());
                    return d;
                })
                .toList();

        resp.setItems(items);
        return resp;
    }

}

