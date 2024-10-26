package vn.com.carrentalsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.com.carrentalsystem.entities.Address;
import vn.com.carrentalsystem.repository.AddressRepository;
import vn.com.carrentalsystem.service.AddressService;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }
}
