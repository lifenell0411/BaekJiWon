package com.KoreaIT.bjw._05_project.service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PhotoService {
    
    @Value("${photo.upload.directory}") // application.properties에서 설정한 디렉토리 경로
    private String uploadDirectory;

    public String uploadPhoto(MultipartFile file) throws IOException {
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = getFileExtension(originalFileName);
        String newFileName = generateUniqueFileName(fileExtension);

        // 파일 저장 경로
        Path filePath = Path.of(uploadDirectory, newFileName);

        // 디렉토리 생성
        Files.createDirectories(filePath.getParent());

        // 파일 복사
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // 파일 URL 반환
        return "/photos/" + newFileName;
    }

    private String getFileExtension(String fileName) {
        return StringUtils.getFilenameExtension(fileName);
    }

    private String generateUniqueFileName(String fileExtension) {
        return UUID.randomUUID().toString() + "." + fileExtension;
    }
    
    // 기타 서비스 메소드...

}
