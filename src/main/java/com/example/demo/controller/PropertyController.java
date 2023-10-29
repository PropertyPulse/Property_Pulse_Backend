package com.example.demo.controller;

import com.example.demo.dto.requestDto.Names;
import com.example.demo.dto.requestDto.RequestAddNewPropertyDto;
import com.example.demo.entity.Property;
import com.example.demo.entity.PropertyType;
import com.example.demo.exception.UserException;
import com.example.demo.service.PropertyService;
import com.example.demo.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@PreAuthorize("hasRole('PROPERTYOWNER')")
@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {
    private final PropertyService propertyService;

    @Autowired
    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;

    }

    @PutMapping("/addNewProperty")
    @PreAuthorize("hasAuthority('propertyowner:create')")
    public ResponseEntity<String> addNewProperty(
            @RequestParam("address") String address,
            @RequestParam("type") String type,
            @RequestParam("location") String location,
            @RequestParam("district") String district,
            @RequestParam("duration") String duration,
            @RequestParam("stories") int stories,
            @RequestParam("bathrooms") int bathrooms,
            @RequestParam("bedrooms") int bedrooms,
            @RequestParam("dining_rooms") int diningRooms,
            @RequestParam("living_rooms") int livingRooms,
            @RequestParam("have_special_rooms") String haveSpecialRooms,
            @RequestParam("special_rooms") String specialRooms,
            @RequestParam("want_insurance") boolean wantInsurance,
            @RequestParam("checklist") List<String> checklist,
            @RequestParam("property_owner_email") String propertyOwnerEmail,
            @RequestParam("image")MultipartFile file,
            @RequestParam("document") MultipartFile propertydocument
    ) throws UserException {

        try {


            Property property = new Property();
            property.setType(PropertyType.HOME);
            property.setAddress(address);
            property.setDistrict(district);
            property.setLocation(location);
            property.setDuration(duration);
            property.setStories(stories);
            property.setBathrooms(bathrooms);
            property.setBedrooms(bedrooms);
            property.setDiningRooms(diningRooms);
            property.setLivingRooms(livingRooms);
            property.setHaveSpecialRooms(haveSpecialRooms);
            property.setSpecialRooms(specialRooms);
            property.setRegisteredStatus("PENDING");
            property.setRegistered_date(LocalDate.now());
            property.setWantInsurance(wantInsurance);
            property.setChecklist(checklist);


            propertyService.addNewProperty(property,file,propertydocument,propertyOwnerEmail);

            return ResponseEntity.ok("Property added successfully.");
        } catch (Exception e) {
            return ResponseEntity.ok("Property added not successfully.");
        }

    }

    @PostMapping("/addNewLandProperty")
    public ResponseEntity<String> addNewLandProperty(
            @RequestParam("address") String address,
            @RequestParam("type") String type,
            @RequestParam("location") String location,
            @RequestParam("district") String district,
            @RequestParam("duration") String duration,
            @RequestParam("land_size") double landSize,
            @RequestParam("have_crops") String haveCrops,
            @RequestParam("crops") String crops,
            @RequestParam("special_facts") String specialFacts,
            @RequestParam("want_insurance") boolean wantInsurance,
            @RequestParam("property_owner_email") String propertyOwnerEmail,
            @RequestParam("insurance_documents") MultipartFile insuranceDocuments,
            @RequestParam("property_images") MultipartFile propertyImages
  ) throws UserException, IOException {
        // Create a new Property entity
        Property property = new Property();

        // Set properties based on the request parameters
        property.setAddress(address);
        property.setType(PropertyType.valueOf(type)); // Assuming PropertyType is an Enum
        property.setLocation(location);
        property.setDistrict(district);
        property.setDuration(duration);
        property.setLandSize(landSize);
        property.setHaveCrops(haveCrops);
        property.setCrops(crops);
        property.setSpecialFacts(specialFacts);
        property.setWantInsurance(wantInsurance);


        propertyService.addNewLandProperty(property,propertyImages,insuranceDocuments,propertyOwnerEmail);
        // You can access the values using the provided parameters and process them as needed.
        // MultipartFile insuranceDocuments and propertyImages contain the uploaded files.

        // You can save files to the server or perform other actions with the data.

        // Return an appropriate response
        return ResponseEntity.ok("Property added successfully.");
    }






    /* @PreAuthorize("hasAuthority('propertyowner:create')")
    @PostMapping("/getAllProperties")
    public ResponseEntity<List<ResponseAddNewPropertyDto>> getAllProperties(@RequestBody RequestAddNewPropertyDto req) throws UserException {
        return propertyService.getAllProperties();
    }*/

    @PostMapping("/hello")
    //read the provided form data
    public void display(@RequestParam("name")String name,@RequestParam("pass")String pass)
    {
        System.out.println(name +" " +pass);
    }


//    @PostMapping
//    public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file) throws IOException {
//        String uploadImage = storageservice.uploadImage(file);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(uploadImage);
//    }
//
//    @GetMapping("/{fileName}")
//    public ResponseEntity<?> downloadImage(@PathVariable String fileName){
//        byte[] imageData=storageservice.downloadImage(fileName);
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.valueOf("image/png"))
//                .body(imageData);
//
//    }

//
//    @PostMapping("/fileSystem")
//    public ResponseEntity<?> uploadImageToFIleSystem(@RequestParam("image")MultipartFile file) throws IOException {
//        String uploadImage = storageservice.uploadImageToFileSystem(file);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(uploadImage);
//    }
//
//    @GetMapping("/fileSystem/{fileName}")
//    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) throws IOException {
//        byte[] imageData=storageservice.downloadImageFromFileSystem(fileName);
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.valueOf("image/png"))
//                .body(imageData);
//
//    }
//
//    @PostMapping("/fileSystemDocument")
//    public ResponseEntity<?> uploadDocumentToFIleSystem(@RequestParam("document") MultipartFile file) throws IOException {
//        String uploadDocument = storageservice.uploadDocumentToFileSystem(file);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(uploadDocument);
//    }
//
//    @GetMapping("/fileSystem/documents/{fileName}")
//    public ResponseEntity<?> downloadDocumentFromFileSystem(@PathVariable String fileName) throws IOException {
//        byte[] documentData = storageservice.downloadDocumentFromFileSystem(fileName);
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.APPLICATION_PDF) // Set the content type for PDF
//                .body(documentData);
//    }

//
//    @PostMapping("/fileSystemMultipleImages")
//    public ResponseEntity<?> uploadImagesToFIleSystem(@RequestParam("images") MultipartFile[] files) throws IOException {
//        List<String> uploadedImages = new ArrayList<>();
//        for (MultipartFile file : files) {
//            String uploadImage = storageservice.uploadImageToFileSystem(file);
//            uploadedImages.add(uploadImage);
//        }
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(uploadedImages);
//    }
//
//    @PostMapping("/fileSystemGetImages")
//    public ResponseEntity<?> downloadImagesFromFileSystem(@RequestBody Names names) throws IOException {
//
//
//        List<byte[]> imageList = new ArrayList<>();
//        for (String fileName : names.getFileNames()) {
//            byte[] imageData = storageservice.downloadImageFromFileSystem(fileName);
//            imageList.add(imageData);
//        }
//
//        // Assuming all images have the same content type (e.g., "image/png")
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_PNG);
//
//        return ResponseEntity.status(HttpStatus.OK)
//                .headers(headers)
//                .body(imageList);
//    }
//
//


//
//    @PostMapping("/fileSystemMultipleImagesUpload")
//    public ResponseEntity<?> uploadImagesToFIleSystem(@RequestParam("images") List<MultipartFile> files) throws IOException {
//        List<String> uploadedImages = storageservice.uploadImagesToFileSystem(files);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(uploadedImages);
//    }
//
//    @GetMapping("/fileSystemDownloadAll")
//    public ResponseEntity<?> downloadImagesFromFileSystem(@RequestParam("fileNames") List<String> fileNames) throws IOException {
//        List<byte[]> imageList = storageservice.downloadImagesFromFileSystem(fileNames);
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.valueOf("image/png"))
//                .body(imageList);
//    }



}
