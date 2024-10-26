package vn.com.carrentalsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.com.carrentalsystem.entities.BrandCar;
import vn.com.carrentalsystem.repository.BrandCarRepository;
import vn.com.carrentalsystem.service.BrandCarService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BrandCarServiceImpl implements BrandCarService {

    private final BrandCarRepository brandCarRepository;

    @Override
    public List<BrandCar> findAll() {
        return brandCarRepository.findAll();
    }

    @Override
    public BrandCar findByBrandCarId(Long brandCarId) {
        return brandCarRepository.findById(brandCarId).orElse(null);
    }
}
