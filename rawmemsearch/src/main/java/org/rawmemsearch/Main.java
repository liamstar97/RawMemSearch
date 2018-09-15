package org.rawmemsearch;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException{
        String path = args.length > 0 ? args[0] : "/home/corrupt/Desktop/books";
        String query = args.length > 1 ? args[1] : "whenever";
        int numFiles = args.length > 2 ? Integer.parseInt(args[2]) : 30;
        search(path, query, numFiles);

    }

    public static void search(String path, String query, int numFiles) throws IOException {
        long startTime = System.currentTimeMillis();
//        SlightlySmarterSearch slightlySmarterSearch = new SlightlySmarterSearch(files(path, numFiles));
        Searcher slightlySmarterSearch = getNewSearcher(path, numFiles);
//        DumbSearchEngine slightlySmarterSearch = new DumbSearchEngine(path)
        long diff = System.currentTimeMillis() - startTime;
        LOGGER.info(String.format( "indexing %d files took %d milliseconds", numFiles, diff));
        startTime = System.currentTimeMillis();
        QueryDocument queryDocument = new QueryDocument(query, 5);
        List<SearchDocument> search = slightlySmarterSearch.search(queryDocument);
        diff = System.currentTimeMillis() - startTime;
        LOGGER.info(String.format( "searching for %s took %d milliseconds", query, diff));
        for (SearchDocument searchDocument: search) {
            System.out.println(searchDocument.getTitle() + " " + searchDocument.getScore());
        }

        startTime = System.currentTimeMillis();
        int i = 0;
        int numSearches = 100;
        Iterator<String> keyIterator = randomQueries(numSearches, slightlySmarterSearch).iterator();
        while (keyIterator.hasNext()) {
            if (i == numSearches) {
                break;
            }
            QueryDocument queryList = new QueryDocument(keyIterator.next(), 5);
            slightlySmarterSearch.search(queryList);
            i += 1;

        }
        diff = System.currentTimeMillis() - startTime;
        LOGGER.info(String.format("doing %d searches took %d milliseconds", numSearches, diff));
    }

    private static Searcher getNewSearcher(String path, int numFiles) {
//        return new DumbSearchEngine(files(path, numFiles), numFiles);
        return new SlightlySmarterSearch(files(path, numFiles));
    }

    private static Iterable<String> randomQueries(int num, Searcher searcher) {
        if (searcher instanceof SlightlySmarterSearch) {

            return (((SlightlySmarterSearch)searcher).wordTable
                    .keySet().stream().collect(Collectors.toList())).subList(0, num);
        } else if (searcher instanceof DumbSearchEngine) {
            return ((DumbSearchEngine)searcher).getDumbSearchDocuments()
                    .stream().flatMap((SearchDocument d) -> Arrays.stream(d.getContents().split(" ")))
                    .limit(num)
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("How could you have done this!");
        }
    }

    private static List<File> files(String path) {
        return files(path, -1);
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