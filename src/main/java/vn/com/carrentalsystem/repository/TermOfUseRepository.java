package vn.com.carrentalsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.carrentalsystem.entities.TermsOfUse;

@Repository
public interface TermOfUseRepository extends JpaRepository<TermsOfUse, Long> {
}
