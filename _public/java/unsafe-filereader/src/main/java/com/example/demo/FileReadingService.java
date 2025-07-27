package com.example.demo;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service class responsible for handling file reading operations.
 * This class contains the core logic for reading content from a file.
 */
@Service
public class FileReadingService {

    /**
     * Reads all lines from a specified file.
     *
     * @param filePath The path to the file to be read.
     * @return A string containing all lines from the file, separated by newlines.
     * @throws IOException If an I/O error occurs reading from the file or the file does not exist.
     */
    public String readFile(String filePath) throws IOException {
        // Create a Path object from the given file path string.
        Path path = Paths.get(filePath);

        // Check if the file exists before attempting to read.
        if (!Files.exists(path)) {
            throw new IOException("File not found at path: " + filePath);
        }

        // Use try-with-resources to ensure the stream is closed automatically.
        // Files.lines() reads all lines from a file as a Stream of strings.
        try (Stream<String> lines = Files.lines(path)) {
            // Collect all lines into a List and then join them with newline characters.
            return lines.collect(Collectors.joining("\n"));
        }
    }

    /**
     * Reads a specific number of lines from a specified file.
     *
     * @param filePath The path to the file to be read.
     * @param numLines The number of lines to read from the beginning of the file.
     * @return A string containing the specified number of lines from the file, separated by newlines.
     * @throws IOException If an I/O error occurs reading from the file or the file does not exist.
     */
    public String readNLines(String filePath, int numLines) throws IOException {
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            throw new IOException("File not found at path: " + filePath);
        }

        try (Stream<String> lines = Files.lines(path)) {
            // Limit the stream to the specified number of lines and collect them.
            return lines.limit(numLines).collect(Collectors.joining("\n"));
        }
    }
}