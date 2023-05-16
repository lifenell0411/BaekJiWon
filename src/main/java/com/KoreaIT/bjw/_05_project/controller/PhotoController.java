package com.KoreaIT.bjw._05_project.controller;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.KoreaIT.bjw._05_project.service.PhotoService;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping("/photos")
public class PhotoController {
    
    private final PhotoService photoService;
    private final ObjectMapper objectMapper;

    public PhotoController(PhotoService photoService, ObjectMapper objectMapper) {
        this.photoService = photoService;
        this.objectMapper = objectMapper;
    }

    // 이미지 업로드 요청 처리
    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadPhoto(@RequestParam("file") MultipartFile file) {
        try {
            // 이미지 파일을 서비스 레이어로 전달
            String photoUrl = photoService.uploadPhoto(file);

            // 응답 데이터 생성
            Map<String, String> response = new HashMap<>();
            response.put("imageUrl", getAbsoluteUrl(photoUrl));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            return ResponseEntity.ok().headers(headers).body(response);
       
        } catch (Exception e) {
            // 업로드 실패 시 에러 메시지 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 기타 컨트롤러 메소드...

    // 이미지 URL을 절대 경로로 변환
    private String getAbsoluteUrl(String relativeUrl) {
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return baseUrl + relativeUrl;
    }
}
