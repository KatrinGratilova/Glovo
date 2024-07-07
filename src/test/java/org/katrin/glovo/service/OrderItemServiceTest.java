package org.katrin.glovo.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.katrin.glovo.dto.OrderItemDto;
import org.katrin.glovo.repository.OrderItemRepository;

import java.util.List;

public class OrderItemServiceTest {
    private OrderItemRepository orderItemRepository;
    private OrderItemService orderItemService;

    @Before
    public void setUp() {
        orderItemRepository = new OrderItemRepository();
        orderItemService = new OrderItemService(orderItemRepository);
    }

    @Test
    public void getById_ok() {
        int id = 1;
        OrderItemDto expected = OrderItemDto.builder()
                .id(id)
                .price(89.2)
                .productId(1)
                .quantity(23)
                .build();
        orderItemRepository.save(expected);
        OrderItemDto actual = orderItemService.getById(id);

        Assert.assertTrue(orderItemService.getAll().contains(expected));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAll_ok() {
        OrderItemDto orderItemDto = OrderItemDto.builder()
                .id(1)
                .price(89.2)
                .productId(1)
                .quantity(23)
                .build();
        orderItemRepository.save(orderItemDto);
        List<OrderItemDto> expected = List.of(orderItemDto);
        List<OrderItemDto> actual = orderItemService.getAll().stream().toList();

        Assert.assertEquals(actual, expected);
        Assert.assertEquals(actual.size(), expected.size());
    }

    @Test
    public void update_ok() {
        int id = 1;
        OrderItemDto orderItemDto = OrderItemDto.builder()
                .id(id)
                .price(89.2)
                .productId(1)
                .quantity(23)
                .build();
        orderItemRepository.save(orderItemDto);

        OrderItemDto expected = OrderItemDto.builder()
                .id(id)
                .price(45.2)
                .productId(1)
                .quantity(67)
                .build();

        OrderItemDto actual = orderItemService.update(expected);

        Assert.assertTrue(orderItemService.getAll().contains(expected));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void delete_ok() {
        int id = 1;
        OrderItemDto orderItemDto = OrderItemDto.builder()
                .id(id)
                .price(89.2)
                .productId(1)
                .quantity(23)
                .build();
        orderItemRepository.save(orderItemDto);
        orderItemService.delete(id);

        Assert.assertFalse(orderItemService.getAll().contains(orderItemDto));
    }
}
