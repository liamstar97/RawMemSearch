package org.rawmemsearch;

import org.junit.Before;

import java.io.IOException;

public class SlightlySmaterIndexerSearcherTest extends SearcherIndexerTest {

    @Before
    public void setup() throws IOException {
        SearchDocument doc1 = new SearchDocument("doc1", "document 1", "whatever man");
        SlightlySmarterSearch slightlySmarterSearch = new SlightlySmarterSearch(null); // TODO: fixme
        indexer = slightlySmarterSearch;
        searcher = slightlySmarterSearch;
        // indexer.indexDoc(null);
    }

}
