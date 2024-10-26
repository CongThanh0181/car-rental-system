package vn.com.carrentalsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.carrentalsystem.entities.Userz;


@Repository
public interface UserzRepository extends JpaRepository<Userz, Long> {

    Userz findByEmail(String email);
}