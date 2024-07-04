package org.katrin.glovo.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.katrin.glovo.dto.OrderDto;
import org.katrin.glovo.dto.OrderItemDto;
import org.katrin.glovo.dto.OrderStatus;
import org.katrin.glovo.repository.OrderItemRepository;
import org.katrin.glovo.repository.OrderRepository;

import java.time.LocalDate;
import java.util.List;

public class OrderServiceTest {
    private OrderService orderService;

    @Before
    public void setUp() {
        OrderRepository orderRepository = new OrderRepository(new OrderItemRepository());
        orderService = new OrderService(orderRepository);
    }

    @Test
    public void getById_ok() {
        int id = 1;
        OrderDto expected = OrderDto.builder()
                .id(id)
                .customerName("Kate")
                .status(OrderStatus.SENT)
                .checkoutDate(LocalDate.of(2024, 12, 12))
                .build();
        orderService.save(expected);
        OrderDto actual = orderService.getById(id);

        Assert.assertTrue(orderService.getAll().contains(expected));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAll_ok() {
        OrderDto orderDto = OrderDto.builder()
                .id(1)
                .customerName("Kate")
                .status(OrderStatus.SENT)
                .checkoutDate(LocalDate.of(2024, 12, 12))
                .build();
        orderService.save(orderDto);
        List<OrderDto> expected = List.of(orderDto);
        List<OrderDto> actual = orderService.getAll().stream().toList();

        Assert.assertEquals(actual, expected);
        Assert.assertEquals(actual.size(), expected.size());
    }

    @Test
    public void save_ok() {
        int id = 1;
        OrderDto expected = OrderDto.builder()
                .id(id)
                .customerName("Kate")
                .status(OrderStatus.SENT)
                .checkoutDate(LocalDate.of(2024, 12, 12))
                .build();
        orderService.save(expected);
        OrderDto actual = orderService.getById(id);

        Assert.assertTrue(orderService.getAll().contains(expected));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void update_ok() {
        OrderDto orderDto = OrderDto.builder()
                .id(1)
                .customerName("Kate")
                .status(OrderStatus.SENT)
                .checkoutDate(LocalDate.of(2024, 12, 12))
                .build();
        orderService.save(orderDto);

        OrderDto expected = OrderDto.builder()
                .id(1)
                .customerName("Kate")
                .status(OrderStatus.SENT)
                .checkoutDate(LocalDate.of(2024, 12, 12))
                .build();

        OrderDto actual = orderService.update(expected);

        Assert.assertTrue(orderService.getAll().contains(expected));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getItems_ok() {
        int id = 1;
        OrderDto orderDto = OrderDto.builder()
                .id(id)
                .customerName("Kate")
                .status(OrderStatus.SENT)
                .checkoutDate(LocalDate.of(2024, 12, 12))
                .build();
        orderService.save(orderDto);
        orderService.addItem(id, OrderItemDto.builder()
                .id(id)
                .price(89.2)
                .productId(1)
                .quantity(23)
                .build());

        List<Integer> expected = orderDto.getItems();

        List<Integer> actual = orderService.getItems(id);

        Assert.assertEquals(expected.size(), actual.size());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addItem_ok() {
        int orderId = 1;
        int itemId = 1;
        OrderDto orderDto = OrderDto.builder()
                .id(orderId)
                .customerName("Kate")
                .status(OrderStatus.SENT)
                .checkoutDate(LocalDate.of(2024, 12, 12))
                .build();
        orderService.save(orderDto);
        orderService.addItem(orderId, OrderItemDto.builder()
                .id(itemId)
                .price(89.2)
                .productId(1)
                .quantity(23)
                .build());

        List<Integer> expected = orderDto.getItems();

        List<Integer> actual = orderService.getItems(orderId);

        Assert.assertEquals(expected.size(), actual.size());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void removeItem_ok() {
        int orderId = 1;
        Integer itemId = 1;
        OrderDto orderDto = OrderDto.builder()
                .id(orderId)
                .customerName("Kate")
                .status(OrderStatus.SENT)
                .checkoutDate(LocalDate.of(2024, 12, 12))
                .build();
        orderService.save(orderDto);
        orderService.addItem(orderId, OrderItemDto.builder()
                .id(itemId)
                .price(89.2)
                .productId(1)
                .quantity(23)
                .build());

        orderDto.getItems().remove(itemId);

        List<Integer> expected = orderDto.getItems();

        List<Integer> actual = orderService.removeItem(orderId, itemId).getItems();

        Assert.assertEquals(expected.size(), actual.size());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void delete_ok() {
        int id = 1;
        OrderDto orderDto = OrderDto.builder()
                .id(id)
                .customerName("Kate")
                .status(OrderStatus.SENT)
                .checkoutDate(LocalDate.of(2024, 12, 12))
                .build();
        orderService.save(orderDto);
        orderService.delete(id);

        Assert.assertFalse(orderService.getAll().contains(orderDto));
    }
}
