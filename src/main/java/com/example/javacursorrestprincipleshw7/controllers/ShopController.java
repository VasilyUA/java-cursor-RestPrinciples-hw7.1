package com.example.javacursorrestprincipleshw7.controllers;

import com.example.javacursorrestprincipleshw7.entitys.Shop;
import com.example.javacursorrestprincipleshw7.exeptions.ShopNotFound;
import com.example.javacursorrestprincipleshw7.services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/shops")
public class ShopController {

    @Autowired
    private ShopService shopService;

    // 1) Створити новий магазин
    @PostMapping
    public ResponseEntity<Shop> createShop(@RequestBody Shop shop) {
        try {
            Shop createdShop = shopService.createShop(shop);
            return new ResponseEntity<>(createdShop, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 2) Видалити магазин (по айдішці)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShop(@PathVariable Long id) {
        try {
            shopService.deleteShop(id);
            return ResponseEntity.noContent().build();
        } catch (ShopNotFound e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 3) Отримати список всіх магазинів
    @GetMapping
    public ResponseEntity<List<Shop>> getAllShops() {
        try {
            List<Shop> shops = shopService.getAllShops();
            return ResponseEntity.ok(shops);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    // 4) Отримати магазин по айдішці
    @GetMapping("/{id}")
    public ResponseEntity<Shop> getShopById(@PathVariable Long id) {
        try {
            Optional<Shop> shopOptional = shopService.getShopById(id);
            return shopOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 5) Змінити поля магазину (по айдішці)
    @PutMapping("/{id}")
    public ResponseEntity<Shop> updateShop(@PathVariable Long id, @RequestBody Shop shopUpdate) {
        try {
            Optional<Shop> updatedShopOptional = shopService.updateShop(id, shopUpdate);
            return updatedShopOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
