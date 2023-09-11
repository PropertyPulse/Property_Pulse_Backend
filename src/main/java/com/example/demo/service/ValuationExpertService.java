package com.example.demo.service;


import com.example.demo.dto.responseDto.ValuationReportDTO;
import com.example.demo.entity.ValuationReport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Service
public interface ValuationExpertService {


//    Attachment saveAttachment(MultipartFile file) throws Exception;
//    Attachment getAttachment(String fileId) throws Exception;

    ValuationReport saveReport(MultipartFile file,Long PropertyId,String RequestedDate) throws Exception;
    ValuationReport  requestReport(Long PropertyId,String RequestedDate) throws Exception;



   public  List<ValuationReportDTO>  findByStatus(String status)  throws Exception;

}
