package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * REST Controller for handling file-related requests.
 * This class now exposes endpoints to read classpath resources, providing a safer approach.
 */
@RestController
public class FileController {

    // Autowire the FileReadingService to use its methods.
    private final FileReadingService fileReadingService;

    @Autowired
    public FileController(FileReadingService fileReadingService) {
        this.fileReadingService = fileReadingService;
    }

    /**
     * Endpoint to read the entire content of a classpath resource.
     * Access via GET request to /read-resource?resourceName=<your_resource_name>
     *
     * @param resourceName The name of the resource (e.g., "data.txt"), provided as a request parameter.
     * This resource must be located within the application's classpath (e.g., in src/main/resources/).
     * @return A ResponseEntity containing the resource content or an error message.
     */
    @GetMapping("/read-resource")
    public ResponseEntity<String> readResource(@RequestParam("resourceName") String resourceName) {
        try {
            // Call the service to read the classpath resource.
            String content = fileReadingService.readClasspathResource(resourceName);
            // Return the content with an OK status.
            return new ResponseEntity<>(content, HttpStatus.OK);
        } catch (IOException e) {
            // If an error occurs (e.g., resource not found), return an error message.
            // This is safer as it only affects resources bundled with the app.
            return new ResponseEntity<>("Error reading resource: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to read a specific number of lines from a classpath resource.
     * Access via GET request to /read-n-lines-resource?resourceName=<your_resource_name>&lines=<number_of_lines>
     *
     * @param resourceName The name of the resource (e.g., "data.txt").
     * @param lines The number of lines to read.
     * @return A ResponseEntity containing the resource content or an error message.
     */
    @GetMapping("/read-n-lines-resource")
    public ResponseEntity<String> readNLinesResource(@RequestParam("resourceName") String resourceName,
                                                     @RequestParam(value = "lines", defaultValue = "5") int lines) {
        try {
            String content = fileReadingService.readNLinesFromClasspathResource(resourceName, lines);
            return new ResponseEntity<>(content, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error reading resource: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}