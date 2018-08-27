package org.rawmemsearch;

import org.junit.Before;

import java.io.IOException;

public class DumbIndexerSearcherTest extends SearcherIndexerTest {

    @Before
    @Override
    public void setup() throws IOException {
        DumbSearchEngine searchEngine = new DumbSearchEngine(searchDocuments);
        indexer = searchEngine;
        searcher = searchEngine;
    }
}
