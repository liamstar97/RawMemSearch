package org.rawmemsearch;

public class TermDocument {
    private String term;
    private int ranking;
    private SearchDocument document;

    public TermDocument(String term, int ranking, SearchDocument document) {
        this.term = term;
        this.ranking = ranking;
        this.document = document;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public SearchDocument getDocument() {
        return document;
    }

    public void setDocument(SearchDocument document) {
        this.document = document;
    }
}
