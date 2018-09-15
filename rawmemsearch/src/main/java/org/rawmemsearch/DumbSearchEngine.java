package org.rawmemsearch;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class DumbSearchEngine implements Searcher, Indexer {

    private List<SearchDocument> dumbSearchDocuments;

    List<SearchDocument> getDumbSearchDocuments() {
        return dumbSearchDocuments;
    }

    public DumbSearchEngine(List<SearchDocument> searchDocuments) {
        dumbSearchDocuments = searchDocuments;
    }

    public DumbSearchEngine(List<File> files, int numFiles) {
        this(files.stream().map(SlightlySmarterSearch::fromFile).collect(Collectors.toList()).subList(0, numFiles));
    }

    @Override
    public void indexDoc(SearchDocument document) {
        dumbSearchDocuments.add(document);
    }

    @Override
    public List<SearchDocument> search(QueryDocument query) {
        List<SearchDocument> rankedResults = new ArrayList<>();
        for (SearchDocument document : dumbSearchDocuments) {
            if (document.getContents().contains(query.getQuery())) {
                String[] splitString = document.getContents().split(" ");
                int score = 0;
                for (String string : splitString) {
                    if (string.toLowerCase().contains(query.getQuery())) {
                        score += 1;
                    }
                }
                document.setScore(score);
                rankedResults.add(document);
            }
        }
        rankedResults.sort(Comparator.comparingDouble(SearchDocument::getScore).reversed());
        if (rankedResults.size() > query.getNumResults()) {
            rankedResults.subList(0, query.getNumResults());
        }
        return rankedResults;
    }


}
