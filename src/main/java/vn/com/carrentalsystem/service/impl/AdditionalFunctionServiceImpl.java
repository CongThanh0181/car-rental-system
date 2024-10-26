package vn.com.carrentalsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.com.carrentalsystem.entities.AdditionalFunction;
import vn.com.carrentalsystem.repository.AdditionalFunctionRepository;
import vn.com.carrentalsystem.service.AdditionalFunctionService;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdditionalFunctionServiceImpl implements AdditionalFunctionService {

    private final AdditionalFunctionRepository additionalFunctionRepository;

    @Override
    public AdditionalFunction save(AdditionalFunction additionalFunction) {
        return additionalFunctionRepository.save(additionalFunction);
    }
}
