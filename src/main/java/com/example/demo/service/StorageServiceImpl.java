package com.example.demo.service;

import com.example.demo.entity.FileData;
import com.example.demo.entity.ImageData;
import com.example.demo.repository.FileDataRepository;
import com.example.demo.repository.StorageRepository;
import com.example.demo.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    private StorageRepository repository;

    @Autowired
    private FileDataRepository fileDataRepository;

    private final String FOLDER_PATH = "C:/Users/MSI/Desktop/MyFiles/";

//    @Override
//    public String uploadImage(MultipartFile file) throws IOException {
//        if (file.isEmpty()) {
//            throw new IllegalArgumentException("File is empty");
//        }
//
//        ImageData imageData = repository.save(ImageData.builder()
//                .name(file.getOriginalFilename())
//                .type(file.getContentType())
//                .imageData(ImageUtils.compressImage(file.getBytes()))
//                .build());
//
//        if (imageData != null) {
//            return "File uploaded successfully: " + file.getOriginalFilename();
//        }
//
//        return null;
//    }
//
//    @Override
//    public byte[] downloadImage(String fileName) {
//        Optional<ImageData> dbImageData = repository.findByName(fileName);
//
//        if (dbImageData.isPresent()) {
//            return ImageUtils.decompressImage(dbImageData.get().getImageData());
//        } else {
//            throw new IllegalArgumentException("Image not found: " + fileName);
//        }
//    }

    @Override
    public String uploadImageToFileSystem(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String filePath = FOLDER_PATH + file.getOriginalFilename();

        FileData fileData = fileDataRepository.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath)
                .build());

        file.transferTo(new File(filePath));

        if (fileData != null) {
            return "File uploaded successfully: " + filePath;
        }

        return null;
    }

    @Override
    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<FileData> fileData = fileDataRepository.findByName(fileName);

        if (fileData.isPresent()) {
            String filePath = fileData.get().getFilePath();
            return Files.readAllBytes(new File(filePath).toPath());
        } else {
            throw new IllegalArgumentException("File not found: " + fileName);
        }
    }

    @Override
    public String uploadDocumentToFileSystem(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String filePath = FOLDER_PATH + file.getOriginalFilename();

        FileData fileData = fileDataRepository.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath)
                .build());

        file.transferTo(new File(filePath));

        if (fileData != null) {
            return "File uploaded successfully: " + filePath;
        }

        return null;
    }


    @Override
    public byte[] downloadDocumentFromFileSystem(String fileName) throws IOException {
        Optional<FileData> fileData = fileDataRepository.findByName(fileName);

        if (fileData.isPresent()) {
            String filePath = fileData.get().getFilePath();
            return Files.readAllBytes(new File(filePath).toPath());
        } else {
            throw new IllegalArgumentException("File not found: " + fileName);
        }
    }


    public List<String> uploadImagesToFileSystem(List<MultipartFile> files) throws IOException {
        List<String> uploadedImages = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String filePath = FOLDER_PATH + file.getOriginalFilename();
                FileData fileData = fileDataRepository.save(FileData.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .filePath(filePath)
                        .build());
                file.transferTo(new File(filePath));
                if (fileData != null) {
                    uploadedImages.add(filePath);
                }
            }
        }

        return uploadedImages;
    }

    public List<byte[]> downloadImagesFromFileSystem(List<String> fileNames) throws IOException {
        List<byte[]> imageList = new ArrayList<>();

        for (String fileName : fileNames) {
            Optional<FileData> fileData = fileDataRepository.findByName(fileName);

            if (fileData.isPresent()) {
                String filePath = fileData.get().getFilePath();
                byte[] imageData = Files.readAllBytes(new File(filePath).toPath());
                imageList.add(imageData);
            } else {
                throw new IllegalArgumentException("File not found: " + fileName);
            }
        }

        return imageList;
    }


}
