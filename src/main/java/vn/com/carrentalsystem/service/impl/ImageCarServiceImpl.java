package vn.com.carrentalsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.com.carrentalsystem.entities.ImageCar;
import vn.com.carrentalsystem.repository.ImageCarRepository;
import vn.com.carrentalsystem.service.ImageCarService;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageCarServiceImpl implements ImageCarService {

    private final ImageCarRepository imageCarRepository;

    @Override
    public ImageCar save(ImageCar imageCar) {
        return imageCarRepository.save(imageCar);
    }
}
