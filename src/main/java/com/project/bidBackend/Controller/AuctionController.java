package com.project.bidBackend.Controller;

import com.project.bidBackend.Model.Auction;
import com.project.bidBackend.Service.AuctionService;
import com.project.bidBackend.dto.AuctionResponse;
import com.project.bidBackend.dto.StartAuctionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auctions")
public class AuctionController {

    private final AuctionService auctionService;

    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @PostMapping("/start")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<AuctionResponse> startAuction(@RequestBody StartAuctionRequest request) {
        return ResponseEntity.ok(auctionService.startAuction(request));
    }
}
