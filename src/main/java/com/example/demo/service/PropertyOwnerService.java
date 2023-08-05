package com.example.demo.service;

import com.example.demo.auth.RegisterRequest;
import com.example.demo.dto.requestDto.RequestPo;
import com.example.demo.dto.responseDto.ResponsePo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public interface PropertyOwnerService {

    public ResponsePo addPropertyOwner(RegisterRequest reqreg);

}
