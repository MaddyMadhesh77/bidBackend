package com.project.bidBackend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AuctionResponse {

    private Long auctionId;
    private Long productId;
    private Double currentHighestBid;
    private String status;
    private LocalDateTime endTime;
}
