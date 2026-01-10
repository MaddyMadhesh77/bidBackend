package com.project.bidBackend.Repo;

import com.project.bidBackend.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product,Long> {

}
