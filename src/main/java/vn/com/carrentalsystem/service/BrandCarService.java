package vn.com.carrentalsystem.service;

import vn.com.carrentalsystem.entities.BrandCar;

import java.util.List;

public interface BrandCarService {
    List<BrandCar> findAll();

    BrandCar findByBrandCarId(Long brandCarId);
}
