package com.example.demo.service;

import com.example.demo.dto.responseDto.ResponseAddNewPropertyDto;
import com.example.demo.dto.responseDto.ResponseGetAllPropertiesByUserDto;
import com.example.demo.entity.Property;
import com.example.demo.entity.PropertyOwner;
import com.example.demo.exception.UserException;
import com.example.demo.repository.PropertyOwnerRepository;
import com.example.demo.repository.PropertyRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import jakarta.transaction.Transactional;
import com.example.demo.repository.*;
import com.example.demo.entity.FileData;
import com.example.demo.entity.ImageData;
import com.example.demo.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private final PropertyRepository propertyRepository;

    @Autowired
    private FileDataRepository fileDataRepository;

    @Autowired
    private ValuationReportRepository valuationReportRepository;

    @Autowired
    private ImageDataRepository imageDataRepository;

    private final PropertyOwnerRepository propertyOwnerRepository;
    private final UserRepository userRepository;


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
// <<<<<<< PP-83
//     public ResponseAddNewPropertyDto addNewProperty(RequestAddNewPropertyDto req) throws UserException {
//         ResponseAddNewPropertyDto responseAddNewPropertyDto = new ResponseAddNewPropertyDto();
// =======
    public Integer addNewProperty(Property property,MultipartFile propertyImage,MultipartFile propertydocument,String propertyOwnerEmail) throws UserException, IOException {
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


        Property property1 = propertyRepository.save(property);

        return property1.getId();
    }

    @Override
    public Integer addNewLandProperty(Property property, MultipartFile file, MultipartFile propertydocument, String propertyOwnerEmail) throws UserException, IOException {
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


        Property property1 = propertyRepository.save(property);

        return property1.getId();
// >>>>>>> main
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

    @Override
    public List<ResponseGetAllPropertiesByUserDto> getAllPropertiesByUser(String email) {
        Optional<User> propertyOwner = userRepository.findByEmail(email);

        List<Property> properties = propertyRepository.findByPropertyOwnerId(propertyOwner.get().getPropertyOwner().getId());

        List<ResponseGetAllPropertiesByUserDto> responseDtos = properties
                .stream()
                .map(this::convertToDtos)
                .collect(Collectors.toList());

        return responseDtos;
    }

    private ResponseGetAllPropertiesByUserDto convertToDtos(Property property) {
        ResponseGetAllPropertiesByUserDto responseDto = new ResponseGetAllPropertiesByUserDto();

        responseDto.setPropertyId(property.getId());
        responseDto.setPropertyType(property.getType().toString());
        responseDto.setAddress(property.getAddress());
        responseDto.setDistrict(property.getDistrict());
        responseDto.setTaskSupervisor(property.getTaskSupervisor());
        responseDto.setRegisteredStatus(property.getRegisteredStatus());
        responseDto.setRegisteredDate(property.getRegistered_date());
        responseDto.setDuration(property.getDuration());

        return responseDto;
    }


    @Override
    public ResponseAddNewPropertyDto getPropertyById(Integer id) {
        Optional<Property> propertyOptional = propertyRepository.findById(id);

        Property property = propertyOptional.get();

        ResponseAddNewPropertyDto responseDto = convertToDto(property);

        return responseDto;
    }

    private ResponseAddNewPropertyDto convertToDto(Property property) {
        ResponseAddNewPropertyDto dto = new ResponseAddNewPropertyDto();

        dto.setType(property.getType());
        dto.setDuration(property.getDuration());
        dto.setAddress(property.getAddress());
        dto.setDistrict(property.getDistrict());
        dto.setAccepted_date(property.getAccepted_date());
        dto.setProperty_owner(property.getPropertyOwner().getId());
        dto.setRegistered_date(property.getRegistered_date());

        return dto;
    }

}
