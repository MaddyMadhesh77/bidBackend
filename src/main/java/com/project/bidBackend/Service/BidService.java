package com.project.bidBackend.Service;

import org.springframework.stereotype.Service;

@Service
public class BidService{

    public void placeBid(int auctionId,double bidAmount){
        //1. Fetch auction by auctionId
        //2. Check auction exists
        //3. Check auction status == ACTIVE
        //4. Get current highest bid
        //5. Check bidAmount > currentHighestBid
        //6. Save bid
        //7. Update auction highest bid
        //8. Notify WebSocket subscribers
        //9. Return success response
    }
}
