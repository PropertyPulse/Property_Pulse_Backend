package com.example.demo.service;




import com.example.demo.exception.UserException;
import com.example.demo.repository.*;

import com.example.demo.entity.FileData;
import com.example.demo.entity.ImageData;
import com.example.demo.entity.Property;
import com.example.demo.entity.PropertyOwner;

import com.example.demo.dto.requestDto.RequestAddNewPropertyDto;
import com.example.demo.dto.responseDto.ResponseAddNewPropertyDto;
import com.example.demo.entity.*;
import com.example.demo.exception.UserException;
import com.example.demo.repository.*;

import com.example.demo.user.UserRepository;
import jakarta.transaction.Transactional;



import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class PropertyServiceImpl implements PropertyService {



    private final PropertyOwnerRepository propertyOwnerRepository;
    private final UserRepository userRepository;






    @Autowired
    private final PropertyRepository propertyRepository;

    @Autowired
    private FileDataRepository fileDataRepository;

    @Autowired
    private ValuationReportRepository valuationReportRepository;


    @Autowired
    private ImageDataRepository imageDataRepository;


    // Use relative paths based on the project directory
    private final String PROJECT_DIRECTORY = System.getProperty("user.dir");
    private final String FOLDER_PATH_PROPERTIES = PROJECT_DIRECTORY + "/uploads/PropertyImages/";
    private final String FOLDER_PATH_PROPERTIES_LAND = PROJECT_DIRECTORY + "/uploads/LandImages/";
    private final String FOLDER_PATH_DOCS_LAND = PROJECT_DIRECTORY + "/uploads/LandDocuments/";
    private final String FOLDER_PATH_DOCS = PROJECT_DIRECTORY + "/uploads/PropertyDocuments/";
    private final String FOLDER_PATH_VAL_REPORT = PROJECT_DIRECTORY + "/uploads/ValuationReport/";

//
//    private final String FOLDER_PATH_PROPERTIES = "C:/Users/MSI/Desktop/MyFiles/Properties/";
//    private final String FOLDER_PATH_DOCS = "C:/Users/MSI/Desktop/MyFiles/Documents/";
    public PropertyServiceImpl(PropertyRepository propertyRepository, UserRepository userRepository, PropertyOwnerRepository propertyOwnerRepository) {
        this.propertyRepository = propertyRepository;

        this.userRepository = userRepository;
        this.propertyOwnerRepository = propertyOwnerRepository;


    }

    @Override
    @Transactional
    public String addNewProperty(Property property,MultipartFile propertyImage,MultipartFile propertydocument,String propertyOwnerEmail) throws UserException, IOException {
        var user = userRepository.findByEmail(propertyOwnerEmail);

        PropertyOwner propertyOwner = propertyOwnerRepository.findById(user.get().getId()).orElse(null);

        property.setPropertyOwner(propertyOwner);

        String filePath = FOLDER_PATH_PROPERTIES + propertyImage.getOriginalFilename();
        String documentpath = FOLDER_PATH_DOCS + propertydocument.getOriginalFilename();

        ImageData img = new ImageData();
        img.setName(propertyImage.getOriginalFilename());
        img.setType(propertyImage.getContentType());
        img.setFilePath(filePath);
        img.setProperty(property);

        imageDataRepository.save(img);

        propertyImage.transferTo(new File(filePath));


        FileData fileData = new FileData();
        fileData.setName(propertydocument.getName());
        fileData.setType(propertydocument.getContentType());
        fileData.setFilePath(documentpath);
        fileData.setProperty(property);
        System.out.println(propertydocument.getOriginalFilename());

        fileDataRepository.save(fileData);
        propertydocument.transferTo(new File(documentpath));


        propertyRepository.save(property);

        return "success";
    }

    @Override
    public String addNewLandProperty(Property property, MultipartFile file, MultipartFile propertydocument, String propertyOwnerEmail) throws UserException, IOException {
        var user = userRepository.findByEmail(propertyOwnerEmail);

        PropertyOwner propertyOwner = propertyOwnerRepository.findById(user.get().getId()).orElse(null);

        property.setPropertyOwner(propertyOwner);

        String filePath = FOLDER_PATH_PROPERTIES_LAND + file.getOriginalFilename();
        String documentpath = FOLDER_PATH_DOCS_LAND + propertydocument.getOriginalFilename();

        ImageData img = new ImageData();
        img.setName(file.getOriginalFilename());
        img.setType(file.getContentType());
        img.setFilePath(filePath);
        img.setProperty(property);

        imageDataRepository.save(img);

        file.transferTo(new File(filePath));


        FileData fileData = new FileData();
        fileData.setName(propertydocument.getOriginalFilename());
        fileData.setType(propertydocument.getContentType());
        fileData.setFilePath(documentpath);
        fileData.setProperty(property);
        System.out.println(propertydocument.getOriginalFilename());

        fileDataRepository.save(fileData);
        propertydocument.transferTo(new File(documentpath));


        propertyRepository.save(property);

        return "success";
    }

    @Override
    public void addValuationReport(Integer propertyId, MultipartFile file) throws IOException {
        Optional<Property> property = propertyRepository.findById(propertyId);

        Property property1 = property.get();

        String documentpath = FOLDER_PATH_VAL_REPORT + file.getOriginalFilename();

        ValuationReport fileData = new ValuationReport();
        fileData.setName(file.getOriginalFilename());
        fileData.setType(file.getContentType());
        fileData.setFilePath(documentpath);
        fileData.setProperty(property1);

        property1.setValuationReport(fileData);

        file.transferTo(new File(documentpath));

        propertyRepository.save(property1);

    }
}
