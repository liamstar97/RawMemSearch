package org.rawmemsearch;

import org.junit.Before;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SlightlySmarterIndexerSearcherTest extends SearcherIndexerTest {

    @Before
    public void setup() throws IOException {
        SlightlySmarterSearch slightlySmarterSearch = new SlightlySmarterSearch(searchDocuments); // TODO: fixme
        indexer = slightlySmarterSearch;
        searcher = slightlySmarterSearch;
        // indexer.indexDoc(null);
    }

}
