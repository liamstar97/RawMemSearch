package org.rawmemsearch;

import java.util.List;

public class DumbSearchEngine implements Searcher, Indexer{

    private List<SearchDocument> dumbSearchDocuments;

    public DumbSearchEngine(List<String> searchStrings) {
        for (String string: searchStrings) {
            string.toLowerCase();
            String[] splitString = new String[2];
            splitString = string.split(":");
            indexDoc(new SearchDocument(splitString[0], splitString[0], splitString[1]));
        }
    }

    @Override
    public void indexDoc(SearchDocument document) {
        dumbSearchDocuments.add(document);
    }

    @Override
    public List<SearchDocument> search(String query, int numResults) {
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
            }
        }
        return 
    }


}
