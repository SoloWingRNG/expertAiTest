package com.ai.expert.test.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author oserret
 */
public class FileReader {

    private static final Logger FILE_READER_LOGGER = LoggerFactory.getLogger(FileReader.class);
    private ArrayList<File> files = new ArrayList<>();

    public FileReader() {
       
    }

    public void readFiles(String inputFolder) {

        File folder = new File(inputFolder);
        File[] listOfFiles = null;

        listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                FILE_READER_LOGGER.info("\t\tReading file: " + file.getName());
                getFiles().add(file);
            } else {
                FILE_READER_LOGGER.info("\t\tBegin directory reading: " + file.getPath());
                readFiles(file.getPath());
            }
        }
    }

    /**
     * @return the files
     */
    public ArrayList<File> getFiles() {
        return files;
    }

    /**
     * @param files the files to set
     */
    public void setFiles(ArrayList<File> files) {
        this.files = files;
    }

}
