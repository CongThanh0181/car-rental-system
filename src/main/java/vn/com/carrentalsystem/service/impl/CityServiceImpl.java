package vn.com.carrentalsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.com.carrentalsystem.entities.City;
import vn.com.carrentalsystem.repository.CityRepository;
import vn.com.carrentalsystem.service.CityService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Override
    public List<City> findAll() {
        return cityRepository.findAll();
    }

    @Override
    public City findByCityId(Long city) {
        return cityRepository.findById(city).orElse(null);
    }

}
