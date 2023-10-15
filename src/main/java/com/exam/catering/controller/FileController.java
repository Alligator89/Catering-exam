package com.exam.catering.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/file")
@SecurityRequirement(name = "Bearer Authentication")
public class FileController {
    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    private final Path ROOT_FILE_PATH = Paths.get("data");

    @Operation(summary = "Uploading file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File is uploaded"),
            @ApiResponse(responseCode = "404", description = "File is not uploaded"),
            @ApiResponse(responseCode = "403", description = "No rights to the content"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @PostMapping("/upload")
    public ResponseEntity<HttpStatus> upload(@RequestParam("file") MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.ROOT_FILE_PATH.resolve(file.getOriginalFilename()));
            log.info("File with name " + file.getName() + " is uploaded!");
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException e) {
            log.info("Invalid uploading file " + file.getName() + "!");
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @Operation(summary = "Get file", description = "Get one file, need to pass the input parameter file`s name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File is found"),
            @ApiResponse(responseCode = "404", description = "File is not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Path path = ROOT_FILE_PATH.resolve(filename);
        try {
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() || resource.isReadable()) {
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
                return new ResponseEntity<>(resource, headers, HttpStatus.OK);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        log.info("File with name " + filename + " is not found!");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get list of files")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of files is found"),
            @ApiResponse(responseCode = "404", description = "List of files is not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @GetMapping("/list")
    public ResponseEntity<ArrayList<String>> getFiles() {
        try {
            ArrayList<String> filenames = (ArrayList<String>) Files.walk(this.ROOT_FILE_PATH, 1)
                    .filter(path -> !path.equals(this.ROOT_FILE_PATH))
                    .map(Path::toString)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(filenames, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("List of files is not found!");
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.CONFLICT);
    }

    @Operation(summary = "Deleting file", description = "Delete file, need to pass the input parameter file`s name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "File is deleted"),
            @ApiResponse(responseCode = "409", description = "File is not deleted"),
            @ApiResponse(responseCode = "403", description = "No rights to the content"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @DeleteMapping("/{filename}")
    public ResponseEntity<HttpStatus> deleteFile(@PathVariable String filename) {
        Path path = ROOT_FILE_PATH.resolve(filename);

        File file = new File(path.toString());
        if (file.delete()) {
            log.info("File with name " + filename + " is deleted!");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        log.info("File with name " + filename + " is not deleted!");
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
