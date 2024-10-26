package vn.com.carrentalsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.com.carrentalsystem.entities.TermsOfUse;
import vn.com.carrentalsystem.repository.TermOfUseRepository;
import vn.com.carrentalsystem.service.TermOfUseService;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TermOfUseServiceImpl implements TermOfUseService {

    private final TermOfUseRepository termOfUseRepository;

    @Override
    public TermsOfUse save(TermsOfUse termsOfUse) {
        return termOfUseRepository.save(termsOfUse);
    }
}
