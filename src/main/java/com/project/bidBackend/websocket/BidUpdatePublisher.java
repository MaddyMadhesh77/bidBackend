package com.project.bidBackend.websocket;

import com.project.bidBackend.dto.BidUpdateMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class BidUpdatePublisher {

    private final SimpMessagingTemplate messagingTemplate;
    public BidUpdatePublisher(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void broadcastBidUpdate(Long auctionId, Double highestBid, Long bidderId){

        BidUpdateMessage message = new BidUpdateMessage();
        message.setAuctionId(auctionId);
        message.setCurrentHighestBid(highestBid);
        message.setBidderId(bidderId);

        messagingTemplate.convertAndSend(
                "/topic/auction/" + auctionId,
                message
        );
    }
}
