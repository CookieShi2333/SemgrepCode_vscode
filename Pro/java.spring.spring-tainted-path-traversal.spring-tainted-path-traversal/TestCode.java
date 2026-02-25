/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kalavit.javulna.controllers.rest;

import com.kalavit.javulna.dto.UploadFileResponse;
import com.kalavit.javulna.services.FileStorageService;
import java.io.IOException;
import java.io.File;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author peti
 */
@RestController
public class FileController {

    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile1(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                //ruleid: spring-tainted-path-traversal
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile2(@RequestParam("file") MultipartFile file) {
        String fileContentType = file.getContentType();
        // This is not ideal protection
        // but technically it counts for some sort of sanitization
        // so we do not highlight this findings:
        if(!SecurityProvided.FILETYPE_WITHELIST.contains(fileContentType)) {
            // throw an error or something
        }

        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                //ok: spring-tainted-path-traversal
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile3(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                //ruleid: spring-tainted-path-traversal
                .path(fileName)
                .toUriString();


        //ruleid: spring-tainted-path-traversal
        File f = new java.io.File(fileName);

        String cleanPath = FilenameUtils.getName(fileName);
        //ok: spring-tainted-path-traversal
        File f2 = new java.io.File(cleanPath);

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @GetMapping("/doAThingWithoutFiles")
    public ResponseEntityResource downloadFile(
            @RequestParam(name = "someParam") String someParam,
            HttpServletRequest request) {
        // Load file as Resource
        Resource resource = someResourceService.generateNonFileResourceFromDescriptor(someParam);
        contentType = "application/octet-stream";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                // this might still be bad, but it's OK in the context of *this rule specifically*
                //ok: spring-tainted-path-traversal
                .body(resource);
    }
}
