package org.rawmemsearch;

import java.util.*;

public class QueryDocument {

    private final String query;
    private final List<SearchDocument> results;
    private final int numResults;

    public QueryDocument(String query, int numResults) {
        this.query = query.toLowerCase();
        this.results = new ArrayList<>(numResults);
        this.numResults = numResults;
    }

    public String getQuery() {
        return query;
    }


    public List<SearchDocument> getResults() {
        return results;
    }


    public void addSearchDocument(SearchDocument searchDocument) {
        this.results.add(searchDocument);
    }

    public int getNumResults() {
        return numResults;
    }


    public List<SearchDocument> results() {
        results.sort(Comparator.comparingInt(SearchDocument::getScore).reversed());
        if (getNumResults() < getResults().size()) {
            return results.subList(0, numResults);
        } else {
            return results;
        }
    }
}