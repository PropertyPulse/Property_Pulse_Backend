package com.example.demo.service;

import com.example.demo.dto.requestDto.RequestAddNewHomeDTO;
import com.example.demo.dto.requestDto.RequestAddNewLandDTO;
import com.example.demo.dto.requestDto.RequestAddNewPropertyDto;
import com.example.demo.entity.*;
import com.example.demo.exception.UserException;
import com.example.demo.repository.HomeRepository;
import com.example.demo.repository.LandRepository;
import com.example.demo.repository.PropertyOwnerRepository;
import com.example.demo.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@Service
@Transactional
public class PropertyServiceImpl implements PropertyService {

    private final LandRepository landRepository;
    private final String imageDirectory = System.getProperty("user.dir")+"/src/main/resources/static/images";
    private final HomeRepository homeRepository;

    private final String documentDirectory = System.getProperty("user.dir")+"/src/main/resources/static/documents";
    private final PropertyOwnerRepository propertyOwnerRepository;
    private final UserRepository userRepository;

    private   final  PropertyOwnerService propertyOwnerService;


    public PropertyServiceImpl(LandRepository landRepository,HomeRepository homeRepository, UserRepository userRepository, PropertyOwnerRepository propertyOwnerRepository, PropertyOwnerService propertyOwnerService) {
        this.landRepository = landRepository;
        this.homeRepository = homeRepository;
        this.userRepository = userRepository;
        this.propertyOwnerRepository = propertyOwnerRepository;
        this.propertyOwnerService = propertyOwnerService;
        makeDirectoryIfNotExist(imageDirectory);
        makeDirectoryIfNotExist(documentDirectory);

    }

    @Override
    @Transactional
    public String addNewProperty(RequestAddNewPropertyDto req) throws UserException {


//        save in te resourcs folder and save the path in the database



        if (req instanceof RequestAddNewLandDTO) {
               RequestAddNewLandDTO requestAddNewLandDTO = (RequestAddNewLandDTO) req;
               Land land = new Land();
                land.setAddress(requestAddNewLandDTO.getAddress());
                land.setHaveCrops(requestAddNewLandDTO.getHave_crops());
                land.setCrops(requestAddNewLandDTO.getCrops());
                land.setSpecialFacts(requestAddNewLandDTO.getSpecial_facts());
                land.setRegisteredStatus(requestAddNewLandDTO.getRegistered_status());
                land.setDistrict(requestAddNewLandDTO.getDistrict());
                land.setAccepted_date(LocalDate.now());
                land.setReturned_date(LocalDate.now());
                land.setWantInsurance(requestAddNewLandDTO.getWant_insurance());
                land.setDuration(requestAddNewLandDTO.getDuration());
                land.setLocation(requestAddNewLandDTO.getLocation());
                List<MultipartFile> propertyImages = requestAddNewLandDTO.getPropertyImages();
                List<MultipartFile> propertyDocuments = requestAddNewLandDTO.getPropertyDocuments();
                List<Document> files  = new ArrayList<>();
                for (MultipartFile propertyImage : propertyImages) {
                    Document doc = new Document();

                    try {

                        doc.setFileUrl(imageDirectory + "/" + propertyImage.getOriginalFilename());
                        doc.setFileName(propertyImage.getOriginalFilename());
                        doc.setProperty(land);
                        doc.setFileType("PROPERTY_IMAGE");
                        doc.setFileSize(propertyImage.getSize());
                        Path fileNameAndPath = Paths.get(imageDirectory, propertyImage.getOriginalFilename());
                        Files.write(fileNameAndPath, propertyImage.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    for(MultipartFile propertyDocument : propertyDocuments){
                        Document doc1 = new Document();
                        try {
                            doc1.setFileUrl(imageDirectory + "/" + propertyDocument.getOriginalFilename());
                            doc1.setFileName(propertyDocument.getOriginalFilename());
                            doc1.setProperty(land);
                            doc1.setFileType("PROPERTY_DOCUMENT");
                            doc1.setFileSize(propertyDocument.getSize());
                            Path fileNameAndPath = Paths.get(imageDirectory, propertyDocument.getOriginalFilename());
                            Files.write(fileNameAndPath, propertyDocument.getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        files.add(doc1);
                    }
                    files.add(doc);
                }
                PropertyOwner propertyOwner = propertyOwnerRepository.findById(requestAddNewLandDTO.getProperty_owner_id()).orElseThrow(() -> new UserException("Property Owner not found"));
                land.setPropertyOwner(propertyOwner);
                land.setFiles(files);
                landRepository.save(land);




            return "Successfully added the land property";
        } else if (req instanceof RequestAddNewHomeDTO) {

                  RequestAddNewHomeDTO requestAddNewHomeDTO = (RequestAddNewHomeDTO) req;
                  Home home = new Home();
                    home.setAddress(requestAddNewHomeDTO.getAddress());
                    home.setStories(requestAddNewHomeDTO.getStories());
                    home.setBedrooms(requestAddNewHomeDTO.getBedrooms());
                    home.setLivingRooms(requestAddNewHomeDTO.getLiving_rooms());
                    home.setBathrooms(requestAddNewHomeDTO.getBathrooms());
                    home.setDiningRooms(requestAddNewHomeDTO.getDining_rooms());
                    home.setHaveSpecialRooms(requestAddNewHomeDTO.getHave_special_rooms());
                    home.setSpecialRooms(requestAddNewHomeDTO.getSpecial_rooms());
                    home.setDistrict(requestAddNewHomeDTO.getDistrict());
                    home.setAccepted_date(LocalDate.now());
                    home.setReturned_date(LocalDate.now());
                    home.setWantInsurance(requestAddNewHomeDTO.getWant_insurance());
                    home.setDuration(requestAddNewHomeDTO.getDuration());
                    home.setLocation(requestAddNewHomeDTO.getLocation());
                    PropertyOwner propertyOwner = propertyOwnerRepository.findById(requestAddNewHomeDTO.getProperty_owner_id()).orElseThrow(() -> new UserException("Property Owner not found"));
                    home.setPropertyOwner(propertyOwner);


                    homeRepository.save(home);
                    return  "Successfully added the home property";
        } else {
            throw new UserException("Invalid Request");

        }


    }


    private void makeDirectoryIfNotExist(String imageDirectory) {
        File directory = new File(imageDirectory);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

}
