package org.example.btl_java.service;

import org.example.btl_java.DTO.ImagesDTO;
import org.example.btl_java.model.Images;
import org.example.btl_java.repository.ImagesRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImagesService {
    private final ImagesRepository imagesRepository;
    private final String uploadDir = "C:/Users/ACER/frontend-admin/public/images/";

    public ImagesService(ImagesRepository imagesRepository) {
        this.imagesRepository = imagesRepository;
    }

    public List<ImagesDTO> getImagesByProductId(Integer productId) {
        return imagesRepository.findByProductId(productId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ImagesDTO saveImage(Integer productId, MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("File rỗng, không thể lưu.");
        }

        // Lấy tên file gốc
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);

        // Tạo thư mục nếu chưa tồn tại
        Files.createDirectories(filePath.getParent());

        // Ghi đè file nếu đã tồn tại
        Files.write(filePath, file.getBytes());

        // Xóa ảnh cũ liên quan đến productId (nếu có)
        deleteImagesByProductId(productId);

        // Lưu thông tin vào cơ sở dữ liệu
        Images image = new Images();
        image.setProductId(productId);
        image.setImagePath(fileName); // Lưu tên file gốc
        Images savedImage = imagesRepository.save(image);

        return convertToDTO(savedImage);
    }

    private ImagesDTO convertToDTO(Images image) {
        ImagesDTO dto = new ImagesDTO();
        dto.setImageId(image.getImageId());
        dto.setProductId(image.getProductId());
        dto.setImagePath("/images/" + image.getImagePath()); // Thêm tiền tố cho frontend
        return dto;
    }

    public void deleteImagesByProductId(Integer productId) throws IOException {
        List<Images> images = imagesRepository.findByProductId(productId);
        for (Images image : images) {
            Path filePath = Paths.get(uploadDir, image.getImagePath());
            Files.deleteIfExists(filePath);
            imagesRepository.delete(image);
        }
    }
}