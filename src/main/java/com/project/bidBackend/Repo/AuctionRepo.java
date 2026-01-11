package com.project.bidBackend.Repo;

import com.project.bidBackend.Model.Auction;
import com.project.bidBackend.Model.AuctionStatus;
import com.project.bidBackend.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepo extends JpaRepository<Auction,Long> {
    boolean existsByProductIdAndStatus(Product product, AuctionStatus status);
}
