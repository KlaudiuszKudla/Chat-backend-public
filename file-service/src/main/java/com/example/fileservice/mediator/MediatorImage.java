package com.example.fileservice.mediator;

import com.example.fileservice.entity.ImageDTO;
import com.example.fileservice.entity.ImageEntity;
import com.example.fileservice.entity.ImageResponse;
import com.example.fileservice.exceptions.FtpConnectionException;
import com.example.fileservice.service.FtpService;
import com.example.fileservice.service.ImageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@AllArgsConstructor
@Slf4j
public class MediatorImage {

    private final FtpService ftpService;
    private final ImageService imageService;

    public ResponseEntity<?> saveImage(MultipartFile file){
        try{
                if(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1).equals("png")){
                log.info("Start uploading files");
                ImageEntity imageEntity = ftpService.uploadFileToFtp(file);
                imageService.save(imageEntity);
                return ResponseEntity.ok(
                        ImageDTO.builder()
                                .uuid(imageEntity.getUuid())
                                .createdAt(imageEntity.getCreateAt())
                                .build());
            }
            return ResponseEntity.status(400).body(new ImageResponse("Media type not supported"));
        }catch (IOException e){
            return ResponseEntity.status(400).body(new ImageResponse("File dont exist"));
        }catch (FtpConnectionException e2){
            log.info("cant save file");
            return ResponseEntity.status(400).body(new ImageResponse("Can't save file"));
        }
    }

    public ResponseEntity<ImageResponse> delete(String uuid) {
        try {
            ImageEntity imageEntity = imageService.findByUuid(uuid);
            if(imageEntity != null) {
                ftpService.deleteFile(imageEntity.getPath());
                return ResponseEntity.ok(new ImageResponse("File deleted"));
            }
            return ResponseEntity.ok(new ImageResponse("File don't exist"));
        } catch (IOException e) {
            return ResponseEntity.status(400).body(new ImageResponse("Can't delete file"));
        }
    }

    public ResponseEntity<?> getImage(String uuid) throws IOException {
        ImageEntity imageEntity = imageService.findByUuid(uuid);
        if (imageEntity != null){
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(ftpService.getFile(imageEntity).toByteArray(), headers, HttpStatus.OK);
        }
        return ResponseEntity.status(400).body(new ImageResponse("File dont exist"));
    }

    public ResponseEntity<ImageResponse> activateImage(String uuid) {
        ImageEntity imageEntity = imageService.findByUuid(uuid);
        if (imageEntity != null){
            imageEntity.setUsed(true);
            imageService.save(imageEntity);
            return ResponseEntity.ok(new ImageResponse("Image successfully activated"));
        }
        return ResponseEntity.status(400).body(new ImageResponse("File don't exist"));
    }
}
