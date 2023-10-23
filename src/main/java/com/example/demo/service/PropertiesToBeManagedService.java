package com.example.demo.service;

import com.example.demo.dto.responseDto.ResponsePropertiesToBeManagedDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import java.util.List;

@Service
@Transactional
public interface PropertiesToBeManagedService {
    List<ResponsePropertiesToBeManagedDto> propertiesToBeManaged (String email);
}
