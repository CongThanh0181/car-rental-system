package vn.com.carrentalsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.carrentalsystem.entities.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
