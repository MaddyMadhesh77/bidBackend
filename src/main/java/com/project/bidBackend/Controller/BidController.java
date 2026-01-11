package com.project.bidBackend.Controller;

import com.project.bidBackend.Service.BidService;
import com.project.bidBackend.dto.BidResponse;
import com.project.bidBackend.dto.PlaceBidRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bids")
public class BidController {

    private final BidService bidService;

    public BidController(BidService bidService) {
        this.bidService = bidService;
    }

    @PostMapping
    @PreAuthorize("hasRole('BIDDER')")
    public ResponseEntity<BidResponse> placeBid(
            @RequestBody PlaceBidRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        Long userId = Long.parseLong(userDetails.getUsername()); // or custom principal

        return ResponseEntity.ok(bidService.placeBid(userId, request));
    }
}
