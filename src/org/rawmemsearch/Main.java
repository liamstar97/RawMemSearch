package org.rawmemsearch;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        String path = args.length > 0 ? args[0] : "/home/corrupt/Desktop/books";
        String query = args.length > 1 ? args[1] : "whenever";
        SlightlySmarterSearch slightlySmarterSearch = new SlightlySmarterSearch(files(path, 5));
        for (SearchDocument searchDocument: slightlySmarterSearch.search(query, 10)) {
            System.out.println(searchDocument.getTitle() + " " + searchDocument.getScore());
        }

    }

    private static List<File> files(String path, int max) {
        File directory = new File(path);
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files == null) {
                throw new IllegalArgumentException("there were no files in the directory " + directory.toString());
            }
            List<File> fileList = Arrays.asList(files);
            return max < 0 ? fileList : fileList.subList(0, max);
        } else {
            throw new IllegalArgumentException("not a directory!");
        }
    }
}