package com.project.bidBackend.dto;

import lombok.Data;

@Data
public class BidUpdateMessage {

    private Long auctionId;
    private Double currentHighestBid;
    private Long bidderId;
}
