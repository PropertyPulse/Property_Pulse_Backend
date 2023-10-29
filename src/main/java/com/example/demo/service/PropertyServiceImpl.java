package com.example.demo.service;

import com.example.demo.dto.requestDto.RequestAddNewPropertyDto;
import com.example.demo.dto.responseDto.ResponseAddNewPropertyDto;
import com.example.demo.entity.FileData;
import com.example.demo.entity.ImageData;
import com.example.demo.entity.Property;
import com.example.demo.entity.PropertyOwner;
import com.example.demo.exception.UserException;
import com.example.demo.repository.FileDataRepository;
import com.example.demo.repository.ImageDataRepository;
import com.example.demo.repository.PropertyOwnerRepository;
import com.example.demo.repository.PropertyRepository;
import com.example.demo.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class PropertyServiceImpl implements PropertyService {


    @Autowired
    private final PropertyRepository propertyRepository;
    @Autowired
    private final PropertyOwnerRepository propertyOwnerRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private FileDataRepository fileDataRepository;


    @Autowired
    private ImageDataRepository imageDataRepository;


    // Use relative paths based on the project directory
    private final String PROJECT_DIRECTORY = System.getProperty("user.dir");
    private final String FOLDER_PATH_PROPERTIES = PROJECT_DIRECTORY + "/uploads/PropertyImages/";
    private final String FOLDER_PATH_PROPERTIES_LAND = PROJECT_DIRECTORY + "/uploads/LandImages/";
    private final String FOLDER_PATH_DOCS_LAND = PROJECT_DIRECTORY + "/uploads/LandDocuments/";
    private final String FOLDER_PATH_DOCS = PROJECT_DIRECTORY + "/uploads/PropertyDocuments/";

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
}
