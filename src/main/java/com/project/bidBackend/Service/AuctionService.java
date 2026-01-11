package com.project.bidBackend.Service;

import com.project.bidBackend.Model.*;
import com.project.bidBackend.Repo.AuctionRepo;
import com.project.bidBackend.Repo.BidRepo;
import com.project.bidBackend.Repo.OrderRepo;
import com.project.bidBackend.Repo.ProductRepo;
import com.project.bidBackend.dto.AuctionResponse;
import com.project.bidBackend.dto.OrderResponse;
import com.project.bidBackend.dto.StartAuctionRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuctionService {

    private final AuctionRepo auctionRepo;
    private final ProductRepo productRepo;
    private final BidRepo bidRepo;
    private final OrderRepo orderRepo;

    public AuctionService(AuctionRepo auctionRepo, ProductRepo productRepo,  BidRepo bidRepo, OrderRepo orderRepo) {
        this.auctionRepo = auctionRepo;
        this.productRepo = productRepo;
        this.bidRepo = bidRepo;
        this.orderRepo = orderRepo;
    }

    public AuctionResponse startAuction(StartAuctionRequest request) {

        Product product = productRepo.findById(request.getProductId()).
                orElseThrow(() -> new RuntimeException("Product not found"));

        if(auctionRepo.existsByProductIdAndStatus(product,AuctionStatus.ACTIVE)){
            throw new RuntimeException("Auction is already active");
        }

        if (request.getStartTime().isAfter(request.getEndTime())) {
            throw new RuntimeException("Invalid auction time range");
        }

        Auction auction = new Auction();
        auction.setProduct(product);
        auction.setStartingBid(request.getStartingBid());
        auction.setCurrentHighestBid(request.getStartingBid());
        auction.setStartTime(request.getStartTime());
        auction.setEndTime(request.getEndTime());
        auction.setStatus(AuctionStatus.ACTIVE);

        Auction savedAuction = auctionRepo.save(auction);

        AuctionResponse response = new AuctionResponse();
        response.setAuctionId(savedAuction.getId());
        response.setProductId(product.getId());
        response.setCurrentHighestBid(savedAuction.getCurrentHighestBid());
        response.setStatus(savedAuction.getStatus().name());
        response.setEndTime(savedAuction.getEndTime());

        return response;
    }

    public OrderResponse endAuction(long auctionId) {

        Auction auction = auctionRepo.findById(auctionId)
                .orElseThrow(() -> new RuntimeException("Auction not found"));

        if (auction.getStatus() != AuctionStatus.ACTIVE) {
            throw new RuntimeException("Auction is not active");
        }

        auction.setStatus(AuctionStatus.ENDED);
        auctionRepo.save(auction);

        Optional<Bid> highestBidOpt =
                bidRepo.findTopByAuctionOrderByBidAmountDesc(auction);

        if (highestBidOpt.isEmpty()) {
            throw new RuntimeException("No bids placed for this auction");
        }

        Bid highestBid = highestBidOpt.get();

        Order order = new Order();
        order.setAuction(auction);
        order.setWinner(highestBid.getBidder());
        order.setFinalPrice(highestBid.getBidAmount());
        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepo.save(order);

        OrderResponse response = new OrderResponse();
        response.setOrderId(savedOrder.getId());
        response.setAuctionId(auctionId);
        response.setWinnerId(highestBid.getBidder().getId());
        response.setFinalPrice(savedOrder.getFinalPrice());
        response.setMessage("Auction ended successfully");

        return response;
    }
}
