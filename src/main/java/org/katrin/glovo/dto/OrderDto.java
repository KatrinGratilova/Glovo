package org.katrin.glovo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class OrderDto {
    private int id;

    private String customerName;

    @JsonProperty("status")
    private OrderStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkoutDate;

    private List<Integer> items;
}
