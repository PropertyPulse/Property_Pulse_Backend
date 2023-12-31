package com.example.demo.service;
import com.example.demo.dto.responseDto.ResponseAddNewPropertyDto;
import com.example.demo.dto.responseDto.ResponseGetAllPropertiesByUserDto;
import com.example.demo.entity.Property;
import com.example.demo.exception.UserException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
public interface PropertyService {
//    ResponseAddNewPropertyDto addNewProperty(RequestAddNewPropertyDto req) throws UserException;

    List<ResponseGetAllPropertiesByUserDto> getAllPropertiesByUser(String email);

    ResponseAddNewPropertyDto getPropertyById(Integer id);

    Integer addNewProperty(Property property,MultipartFile file,MultipartFile propertydocument , String propertyOwnerEmail) throws UserException, IOException;
    Integer addNewLandProperty(Property property,MultipartFile file,MultipartFile propertydocument , String propertyOwnerEmail) throws UserException, IOException;

    void addValuationReport(Integer propertyId, MultipartFile file) throws IOException;

}
