package vn.com.carrentalsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.carrentalsystem.entities.Color;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {

}
