package com.example.fileservice.fasada;

import com.example.fileservice.entity.ImageResponse;
import com.example.fileservice.mediator.MediatorImage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "api/v1/image")
@RequiredArgsConstructor
public class ImageController {

    private final MediatorImage mediatorImage;

    @PostMapping
    public ResponseEntity<?> saveFile(@RequestParam MultipartFile multipartFile){
        return mediatorImage.saveImage(multipartFile);
    }
    @DeleteMapping
    public ResponseEntity<ImageResponse> deleteFile(@RequestParam String uuid){
        return mediatorImage.delete(uuid);
    }

    @GetMapping
    public ResponseEntity<?> getFile(@RequestParam String uuid) throws IOException {
        return mediatorImage.getImage(uuid);
    }
    @PatchMapping
    public ResponseEntity<ImageResponse> activateImage(@RequestParam String uuid) throws IOException{
        return mediatorImage.activateImage(uuid);
    }



}
