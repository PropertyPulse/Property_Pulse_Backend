package com.example.demo.service;

import com.example.demo.auth.RegisterRequest;
import com.example.demo.dto.requestDto.RequestPo;
import com.example.demo.dto.responseDto.ResponsePo;
import com.example.demo.dto.responseDto.ResponseTsdetails;
import com.example.demo.entity.PropertyOwner;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public interface PropertyOwnerService {

    public ResponsePo addPropertyOwner(RegisterRequest req);


}
