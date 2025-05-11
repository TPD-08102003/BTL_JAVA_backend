package org.example.btl_java.controller;

import org.example.btl_java.DTO.ImagesDTO;
import org.example.btl_java.service.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/Images")
public class ImagesController {
    @Autowired
    private ImagesService imagesService;

    @GetMapping(value = "/product/{productId}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<Resource> getImageByProductId(@PathVariable Integer productId) throws IOException {
        List<ImagesDTO> images = imagesService.getImagesByProductId(productId);
        if (images.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Lấy imagePath (ví dụ: /images/hatgiongsen.jpg)
        String imagePath = images.get(0).getImagePath();
        // Chuyển thành đường dẫn thực tế (C:/Users/ACER/frontend-admin/public/images/hatgiongsen.jpg)
        Path filePath = Paths.get("C:/Users/ACER/frontend-admin/public/images", imagePath.substring("/images/".length()));
        Resource resource = new FileSystemResource(filePath);

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(imagePath.endsWith(".png") ? MediaType.IMAGE_PNG : MediaType.IMAGE_JPEG)
                .body(resource);
    }

    @PostMapping("/upload/{productId}")
    public ResponseEntity<ImagesDTO> uploadImage(@PathVariable Integer productId, @RequestParam("file") MultipartFile file) {
        try {
            ImagesDTO savedImage = imagesService.saveImage(productId, file);
            return ResponseEntity.ok(savedImage);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}