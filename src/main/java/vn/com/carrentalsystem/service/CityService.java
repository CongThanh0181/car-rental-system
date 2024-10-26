package vn.com.carrentalsystem.service;

import vn.com.carrentalsystem.entities.City;

import java.util.List;

public interface CityService {

    List<City> findAll();

    City findByCityId(Long city);
}
