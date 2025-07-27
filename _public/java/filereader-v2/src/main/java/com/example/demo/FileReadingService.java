package com.example.demo;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service class responsible for handling file reading operations from classpath resources.
 * This class now exclusively reads content from files located within the application's classpath.
 */
@Service
public class FileReadingService {

    /**
     * Reads all lines from a specified classpath resource.
     *
     * @param resourceName The name of the resource (e.g., "data.txt", "static/my-file.html").
     * This path is relative to the classpath root (e.g., src/main/resources/).
     * @return A string containing all lines from the resource, separated by newlines.
     * @throws IOException If an I/O error occurs reading from the resource or the resource does not exist.
     */
    public String readClasspathResource(String resourceName) throws IOException {
        // Create a ClassPathResource object for the given resource name.
        // This abstraction allows Spring to find the resource regardless of whether it's
        // in a JAR, on the file system, etc.
        Resource resource = new ClassPathResource(resourceName);

        // Check if the resource exists.
        if (!resource.exists()) {
            throw new IOException("Resource not found on classpath: " + resourceName);
        }

        // Use try-with-resources to ensure the InputStream and BufferedReader are closed automatically.
        // Get the InputStream from the resource.
        try (InputStream inputStream = resource.getInputStream();
             // Wrap the InputStream in an InputStreamReader and then a BufferedReader for line-by-line reading.
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            // Read all lines from the BufferedReader and join them with newline characters.
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    /**
     * Reads a specific number of lines from a specified classpath resource.
     *
     * @param resourceName The name of the resource (e.g., "data.txt").
     * @param numLines The number of lines to read from the beginning of the resource.
     * @return A string containing the specified number of lines from the resource, separated by newlines.
     * @throws IOException If an I/O error occurs reading from the resource or the resource does not exist.
     */
    public String readNLinesFromClasspathResource(String resourceName, int numLines) throws IOException {
        Resource resource = new ClassPathResource(resourceName);

        if (!resource.exists()) {
            throw new IOException("Resource not found on classpath: " + resourceName);
        }

        try (InputStream inputStream = resource.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            // Limit the stream to the specified number of lines and collect them.
            return reader.lines().limit(numLines).collect(Collectors.joining("\n"));
        }
    }
}