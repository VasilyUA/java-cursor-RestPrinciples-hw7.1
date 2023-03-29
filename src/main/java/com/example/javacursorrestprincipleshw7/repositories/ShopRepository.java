package com.example.javacursorrestprincipleshw7.repositories;


import com.example.javacursorrestprincipleshw7.entitys.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
}
