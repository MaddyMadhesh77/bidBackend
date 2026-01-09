package com.project.bidBackend.Service;

import com.project.bidBackend.Repo.AuctionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BidService{

    @Autowired
    AuctionRepo auctionRepo;

    public void placeBid(int userId,int auctionId,double bidAmount){
        auctionRepo.findById(auctionId);
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
