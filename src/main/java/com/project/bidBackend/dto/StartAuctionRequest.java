package com.project.bidBackend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StartAuctionRequest {

    private Long productId;
    private Double startingBid;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
