package com.project.bidBackend.Repo;

import com.project.bidBackend.Model.Auction;
import com.project.bidBackend.Model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BidRepo extends JpaRepository<Bid,Long> {

    Optional<Bid> findTopByAuctionOrderByBidAmountDesc(Auction auction);
}
