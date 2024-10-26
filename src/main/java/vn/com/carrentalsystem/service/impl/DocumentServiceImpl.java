package vn.com.carrentalsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.com.carrentalsystem.entities.Document;
import vn.com.carrentalsystem.repository.DocumentRepository;
import vn.com.carrentalsystem.service.DocumentService;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    @Override
    public Document save(Document document) {
        return documentRepository.save(document);
    }
}
