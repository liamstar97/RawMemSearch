package org.rawmemsearch;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Unit test for simple App.
 */
abstract public class SearcherIndexerTest
{
    protected Indexer indexer;
    protected Searcher searcher;

    private SearchDocument doc1 = new SearchDocument("doc1", "document 1", "whenever man");
    private SearchDocument doc2 = new SearchDocument("doc2", "document 2", "nothing special");

    protected List<SearchDocument> searchDocuments = Arrays.asList(doc1, doc2);

    @Before
    protected abstract void setup() throws IOException;

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void searchReturnsNonEmptyResults() {
        assertTrue(searcher.search("whenever", 10).size() > 0);
    }

    @Test
    public void searchDoesntCareAboutCase() {
        assertTrue(searcher.search("WHENEVER", 10).size() > 0);
    }
}
