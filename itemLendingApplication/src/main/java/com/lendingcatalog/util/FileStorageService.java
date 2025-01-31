package com.lendingcatalog.util;

import com.lendingcatalog.util.exception.FileStorageException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileStorageService {

    // Requirement: File I/O
    public static void writeContentsToFile(String contents, String filename, boolean appendFile) throws FileStorageException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, appendFile))) {
            writer.print(contents);
        } catch (IOException e) {
            throw new FileStorageException("Error writing to file: " + filename, e);
        }
    }

    public static List<String> readContentsOfFile(String filename) throws FileStorageException {
            List<String> lines = new ArrayList<>();
            try (Scanner fileScanner = new Scanner(new File(filename))) {
                while (fileScanner.hasNextLine()) {
                    lines.add(fileScanner.nextLine());
                }
            } catch (FileNotFoundException e) {
                throw new FileStorageException("Error reading from file: " + filename, e);
            }
            return lines;
        }
    }
