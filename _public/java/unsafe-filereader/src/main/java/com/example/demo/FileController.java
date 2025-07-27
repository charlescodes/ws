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
 * This class exposes endpoints to trigger file reading operations.
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
     * Endpoint to read the entire content of a file.
     * Access via GET request to /read-file?path=<your_file_path>
     *
     * @param path The path to the file to be read, provided as a request parameter.
     * @return A ResponseEntity containing the file content or an error message.
     */
    @GetMapping("/read-file")
    public ResponseEntity<String> readFile(@RequestParam("path") String path) {
        try {
            // Call the service to read the file.
            String content = fileReadingService.readFile(path);
            // Return the content with an OK status.
            return new ResponseEntity<>(content, HttpStatus.OK);
        } catch (IOException e) {
            // If an error occurs (e.g., file not found, permission denied), return an error message.
            return new ResponseEntity<>("Error reading file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to read a specific number of lines from a file.
     * Access via GET request to /read-n-lines?path=<your_file_path>&lines=<number_of_lines>
     *
     * @param path The path to the file.
     * @param lines The number of lines to read.
     * @return A ResponseEntity containing the file content or an error message.
     */
    @GetMapping("/read-n-lines")
    public ResponseEntity<String> readNLines(@RequestParam("path") String path,
                                             @RequestParam(value = "lines", defaultValue = "5") int lines) {
        try {
            String content = fileReadingService.readNLines(path, lines);
            return new ResponseEntity<>(content, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error reading file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
