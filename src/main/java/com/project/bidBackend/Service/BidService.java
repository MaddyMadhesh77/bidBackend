package com.project.bidBackend.Service;

import com.project.bidBackend.Model.*;
import com.project.bidBackend.Repo.AuctionRepo;
import com.project.bidBackend.Repo.BidRepo;
import com.project.bidBackend.Repo.UserRepo;
import com.project.bidBackend.dto.BidResponse;
import com.project.bidBackend.dto.PlaceBidRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BidService{

    private final BidRepo bidRepo;
    private final AuctionRepo auctionRepo;
    private final UserRepo userRepo;

    public BidService(BidRepo bidRepo, AuctionRepo auctionRepo, UserRepo userRepo) {
        this.bidRepo = bidRepo;
        this.auctionRepo = auctionRepo;
        this.userRepo = userRepo;
    }

    public BidResponse placeBid(Long userId, PlaceBidRequest request){
        //1. Fetch auction by auctionId
        //2. Check auction exists
        //3. Check auction status == ACTIVE
        //4. Get current highest bid
        //5. Check bidAmount > currentHighestBid
        //6. Save bid
        //7. Update auction highest bid
        //8. Notify WebSocket subscribers
        //9. Return success response

        Auction auction = auctionRepo.findById(request.getAuctionId())
                .orElseThrow(() -> new RuntimeException("Auction not found"));

        if (auction.getStatus() != AuctionStatus.ACTIVE) {
            throw new RuntimeException("Auction is not active");
        }

        if (LocalDateTime.now().isAfter(auction.getEndTime())) {
            throw new RuntimeException("Auction has ended");
        }

        if (request.getBidAmount() <= auction.getCurrentHighestBid()) {
            throw new RuntimeException("Bid must be higher than current bid");
        }

        User bidder = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (bidder.getRole() == Role.ADMIN) {
            throw new RuntimeException("Admin cannot place bids");
        }

        Bid bid = new Bid();
        bid.setAuction(auction);
        bid.setBidder(bidder);
        bid.setBidAmount(request.getBidAmount());
        bid.setBidTime(LocalDateTime.now());

        bidRepo.save(bid);

        auction.setCurrentHighestBid(request.getBidAmount());
        auctionRepo.save(auction);

        BidResponse response = new BidResponse();
        response.setBidId(bid.getId());
        response.setCurrentHighestBid(auction.getCurrentHighestBid());
        response.setMessage("Bid placed successfully");

        return response;
    }
}
