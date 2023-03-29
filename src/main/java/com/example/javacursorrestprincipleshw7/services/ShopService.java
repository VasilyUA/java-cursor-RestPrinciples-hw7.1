package com.example.javacursorrestprincipleshw7.services;


import com.example.javacursorrestprincipleshw7.entitys.Shop;

import java.util.List;
import java.util.Optional;

public interface ShopService {
    Shop createShop(Shop shop);
    void deleteShop(Long id);
    List<Shop> getAllShops();
    Optional<Shop> getShopById(Long id);
    Optional<Shop> updateShop(Long id, Shop shopUpdate);
}

