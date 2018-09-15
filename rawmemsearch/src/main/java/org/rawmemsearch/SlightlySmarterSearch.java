package org.rawmemsearch;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.logging.*;
import java.util.stream.Collectors;

public class SlightlySmarterSearch implements Searcher, Indexer {

    private static final Logger LOGGER = Logger.getLogger(SlightlySmarterSearch.class.getName());

    Map<String, List<TermDocument>> wordTable;

    public static SearchDocument fromFile(File file) {
        try {
            String content = String.join("\n", Files.readAllLines(file.toPath()));
            System.out.println("Creating " + file.getName() + "Search File");
            SearchDocument document = new SearchDocument(file.getAbsolutePath(), file.getName(), content);
            return document;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SlightlySmarterSearch(Iterable<SearchDocument> searchDocuments) {
        wordTable = new HashMap<>();
        for (SearchDocument document : searchDocuments) {
            System.out.println("Indexing " + document.getDocId());
            indexDoc(document);
        }
        LOGGER.info("Sorting key lists");
        Iterator<Map.Entry<String, List<TermDocument>>> iterator = wordTable.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<TermDocument>> docList = iterator.next();
            docList.getValue().sort(Comparator.comparingDouble(TermDocument::getRanking).reversed());
        }
    }

    public SlightlySmarterSearch(List<File> searchFiles) {
        this(searchFiles.stream().map(SlightlySmarterSearch::fromFile).collect(Collectors.toList()));
    }

    @Override
    public void indexDoc(SearchDocument searchDocument) {
        System.out.println("Splitting string");
        for (String term: searchDocument.getContents().toLowerCase().split("\\s+")) {
            System.out.println("Creating term document for [" + term + "] in document " + searchDocument.getDocId());
            TermDocument termDocument = new TermDocument(term, 1, searchDocument);
            if (wordTable.containsKey(term)) {
                if (wordTable.get(term).stream().map(TermDocument::getDocument).noneMatch(searchDocument::equals)) {
                    LOGGER.finer("Adding term document");
                    wordTable.get(term).add(termDocument);
                } else {
                    System.out.println("Setting term document ranking");
                    for (TermDocument termDocuments : wordTable.get(term)) {
                        if (termDocuments.getDocument().getDocId().equals(searchDocument.getDocId())) {
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
    public List<SearchDocument> search(QueryDocument query) {
        if (wordTable.containsKey(query.getQuery())) {
            System.out.printf("collecting results for the word: %s \n", query.getQuery());
            for (TermDocument termDocument: wordTable.get(query.getQuery())) {
                termDocument.getDocument().setScore(termDocument.getRanking());
                query.addSearchDocument(termDocument.getDocument());
            }
            return query.results();
        } else {
            System.out.println("There are no results");
            return Collections.emptyList();
        }
    }
}
