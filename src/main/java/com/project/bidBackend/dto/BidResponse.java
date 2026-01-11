package com.project.bidBackend.dto;

import lombok.Data;

@Data
public class BidResponse {

    private Long bidId;
    private Double currentHighestBid;
    private String message;
}
