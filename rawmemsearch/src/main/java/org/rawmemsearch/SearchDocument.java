package org.rawmemsearch;

public class SearchDocument {
    private String docId;
    private String title;
    private String contents;
    private int score;

    public SearchDocument(String docId, String title, String contents) {
        this.docId = docId;
        this.title = title;
        this.contents = contents;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }


}
