package com.mutbook.week4_mission.app.domain.product.repository;

import com.mutbook.week4_mission.app.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByOrderByIdDesc();
}
