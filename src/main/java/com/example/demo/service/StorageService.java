package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface StorageService {
//     String uploadImage(MultipartFile file) throws IOException;
//     byte[] downloadImage(String fileName) ;


     String uploadImageToFileSystem(MultipartFile file) throws IOException;
     byte[] downloadImageFromFileSystem(String fileName) throws IOException;
     String uploadDocumentToFileSystem(MultipartFile file) throws IOException;
     byte[] downloadDocumentFromFileSystem(String fileName) throws IOException;
     List<String> uploadImagesToFileSystem(List<MultipartFile> files) throws IOException;
     List<byte[]> downloadImagesFromFileSystem(List<String> fileNames) throws IOException;
}
