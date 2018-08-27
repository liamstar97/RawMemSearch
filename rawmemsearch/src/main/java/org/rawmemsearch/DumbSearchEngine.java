package org.rawmemsearch;

import java.util.*;

public class DumbSearchEngine implements Searcher, Indexer{

    private List<SearchDocument> dumbSearchDocuments;

    public DumbSearchEngine(List<SearchDocument> searchDocuments) {
        dumbSearchDocuments = searchDocuments;
    }

    @Override
    public void indexDoc(SearchDocument document) {
        dumbSearchDocuments.add(document);
    }

    @Override
    public List<SearchDocument> search(String query, int numResults) {
        List<SearchDocument> rankedResults = new ArrayList<>();
        for (SearchDocument document : dumbSearchDocuments) {
            if (document.getContents().contains(query)) {
                String[] splitString = document.getContents().split(" ");
                Double score = 0.0;
                for (String string : splitString) {
                    if (string.contains(query)) {
                        score += 1.0;
                    }
                }
                document.setScore(score);
                rankedResults.add(document);
            }
        }
        rankedResults.sort(Comparator.comparingDouble(SearchDocument::getScore).reversed());
        if (rankedResults.size() > numResults) {
            rankedResults.subList(0, numResults);
        }
        return rankedResults;
    }


}
