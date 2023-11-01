package com.example.demo.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public interface UpdatePropertyVisitStatusService {
    Boolean updateVisitStatus(int propertyId, boolean visitStatus);
}


