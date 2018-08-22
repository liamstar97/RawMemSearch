package org.rawmemsearch;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        SlightlySmarterSearch slightlySmarterSearch = new SlightlySmarterSearch(files());
        for (SearchDocument searchDocument: slightlySmarterSearch.search("", 10)) {
            System.out.println(searchDocument.getTitle() + " " + searchDocument.getScore());
        }

    }

    private static List<File> files() {
        File directory = new File("/home/corrupt/Desktop/books");
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files == null) {
                throw new IllegalArgumentException("there were no files in the directory " + directory.toString());
            }
            return Arrays.asList(files).subList(0, 5);
        } else {
            throw new IllegalArgumentException("not a directory!");
        }
    }
}