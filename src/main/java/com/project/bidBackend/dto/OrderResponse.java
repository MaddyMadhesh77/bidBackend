package com.project.bidBackend.dto;

import lombok.Data;

@Data
public class OrderResponse {

    private Long orderId;
    private Long auctionId;
    private Long winnerId;
    private Double finalPrice;
    private String message;
}
