package org.rawmemsearch;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class SlightlySmarterSearch implements Searcher, Indexer {

    private Map<String, List<TermDocument>> wordTable;

    public static SearchDocument fromFile(File file) throws IOException {
        String content = String.join("\n", Files.readAllLines(file.toPath()));
        System.out.println("Creating " + file.getName() + "Search File");
        SearchDocument document = new SearchDocument(file.getAbsolutePath(), file.getName(), content);
        return document;
    }

    public SlightlySmarterSearch(Iterable<SearchDocument> searchDocuments) {
        wordTable = new HashMap<>();
        for (SearchDocument document : searchDocuments) {
            System.out.println("Indexing " + document.getDocId());
            indexDoc(document);
        }
        System.out.println("Sorting key lists");
        Iterator<Map.Entry<String, List<TermDocument>>> iterator = wordTable.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<TermDocument>> docList = iterator.next();
            docList.getValue().sort(Comparator.comparingInt(TermDocument::getRanking).reversed());
        }
    }

    public SlightlySmarterSearch(List<File> searchFiles) throws IOException {
        wordTable = new HashMap<>();
            for (File file: searchFiles) {
                SearchDocument document = fromFile(file);
                System.out.println("Indexing " + document.getDocId());
                indexDoc(document);
            }
            System.out.println("Sorting key lists");
            Iterator<Map.Entry<String, List<TermDocument>>> iterator = wordTable.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, List<TermDocument>> docList = iterator.next();
                docList.getValue().sort(Comparator.comparingInt(TermDocument::getRanking).reversed());
            }
    }

    @Override
    public void indexDoc(SearchDocument searchDocument) {
        System.out.println("Splitting string");
        for (String term: searchDocument.getContents().toLowerCase().split("\\s+")) {
            System.out.println("Creating term document for [" + term + "] in document " + searchDocument.getDocId());
            TermDocument termDocument = new TermDocument(term, 1, searchDocument);
            if (wordTable.containsKey(term)) {
                if (wordTable.get(term).stream().map(TermDocument::getDocument).noneMatch(searchDocument::equals)) {
                    System.out.println("Adding term document");
                    wordTable.get(term).add(termDocument);
                } else {
                    System.out.println("Setting term document ranking");
                    for (TermDocument termDocuments : wordTable.get(term)) {
                        if (termDocuments.getTerm().equals(term)) {
                            termDocuments.setRanking(termDocuments.getRanking() + 1);
                        }
                    }
                }
            } else {
                System.out.println("Creating key and value list for " + term);
                List<TermDocument> termDocuments = new ArrayList<>();
                termDocuments.add(termDocument);
                wordTable.put(term, termDocuments);
            }
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public List<SearchDocument> search(String query, int numResults) {
        if (wordTable.containsKey(query)) {
            System.out.println("collecting results");
            List<SearchDocument> results = new ArrayList<>();
            for (TermDocument termDocument: wordTable.get(query)) {
                results.add(termDocument.getDocument());
            }
            if (numResults < results.size()) {
                return results.subList(0, numResults);
            } else {
                return results;
            }
        } else {
            System.out.println("There are no results");
            return Collections.emptyList();
        }
    }
}
