package org.rawmemsearch;

import java.util.List;

public interface Searcher {
    List<SearchDocument> search(QueryDocument query);
}
