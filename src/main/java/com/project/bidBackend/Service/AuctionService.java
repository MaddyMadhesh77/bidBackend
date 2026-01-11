package com.project.bidBackend.Service;

import com.project.bidBackend.Model.Auction;
import com.project.bidBackend.Model.AuctionStatus;
import com.project.bidBackend.Model.Product;
import com.project.bidBackend.Repo.AuctionRepo;
import com.project.bidBackend.Repo.ProductRepo;
import com.project.bidBackend.dto.AuctionResponse;
import com.project.bidBackend.dto.StartAuctionRequest;
import org.springframework.stereotype.Service;

@Service
public class AuctionService {

    private final AuctionRepo auctionRepo;
    private final ProductRepo productRepo;

    public AuctionService(AuctionRepo auctionRepo, ProductRepo productRepo) {
        this.auctionRepo = auctionRepo;
        this.productRepo = productRepo;
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
}
