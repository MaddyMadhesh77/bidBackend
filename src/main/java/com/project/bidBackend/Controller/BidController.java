package com.project.bidBackend.Controller;

import com.project.bidBackend.Service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController("/bids")
public class BidController {

    @Autowired
    BidService bidService;

    public void placeBid(int auctionId, double bidAmount){
        bidService.placeBid(auctionId,bidAmount);
    }

}
