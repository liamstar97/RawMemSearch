package org.rawmemsearch;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Unit test for simple App.
 */
abstract public class SearcherIndexerTest
{
    protected Indexer indexer;
    protected Searcher searcher;

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
}
