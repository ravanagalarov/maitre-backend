package com.maitre.backend.config;

import com.maitre.backend.entity.Restaurant;
import com.maitre.backend.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RestaurantRepository restaurantRepository;

    @Override
    public void run(String... args) {
        if (restaurantRepository.count() == 0) {
            Restaurant restaurant1 = Restaurant.builder()
                    .name("Maitre Central")
                    .build();

            Restaurant restaurant2 = Restaurant.builder()
                    .name("Maitre Sahil")
                    .build();

            restaurantRepository.saveAll(List.of(restaurant1, restaurant2));
            System.out.println("2 ilkin restoran uğurla bazaya əlavə edildi (ID: 1 və ID: 2)");
        }
    }
}