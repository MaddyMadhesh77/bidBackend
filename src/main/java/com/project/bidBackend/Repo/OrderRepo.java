package com.project.bidBackend.Repo;

import com.project.bidBackend.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {
}
