package org.rawmemsearch;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.naming.CannotProceedException;
import javax.naming.directory.SearchResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Unit test for simple App.
 */
abstract public class SearcherIndexerTest
{
    protected Indexer indexer;
    protected Searcher searcher;

    private SearchDocument doc1 = new SearchDocument("doc1", "document 1", "whenever the man");
    private SearchDocument doc2 = new SearchDocument("doc2", "document 2", "nothing is special");
    private SearchDocument doc3 = new SearchDocument("doc3", "document 3", "nothing too special is special to special people");

    protected List<SearchDocument> searchDocuments = Arrays.asList(doc1, doc2, doc3);

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
        QueryDocument query = new QueryDocument("whenever", 10);
        assertTrue("Results are empty", searcher.search(query).size() > 0);
    }

    @Test
    public void searchLongString() {
        QueryDocument query = new QueryDocument("nothing special", 10);
        assertTrue("Results are empty", searcher.search(query).size() > 0);
    }

    @Test
    public void searchForSpecial() {
        QueryDocument query = new QueryDocument("special", 10);
        List<SearchDocument> resultList = searcher.search(query);
        assertTrue("list is not greater than one", resultList.size() > 1);
        assertTrue("document 3 is not highest ranked result", resultList.get(0).getTitle().equalsIgnoreCase("document 3"));
    }

    @Test
    public void searchDoesntCareAboutCase() {
        QueryDocument query = new QueryDocument("WHENEVER", 10);
        assertTrue("Cares about query case", searcher.search(query).size() > 0);
    }

    @Test
    public void searchRanking() {
        QueryDocument query = new QueryDocument("WHENEVER", 10);
        List<SearchDocument> resultList = searcher.search(query);
        List<SearchDocument> sortedList = new ArrayList<>(resultList);
        sortedList.sort(Comparator.comparingDouble(SearchDocument::getScore).reversed());
        Double oldRank = Double.MIN_VALUE;
        for (SearchDocument searchDocument : resultList) {
            if (searchDocument.getScore() < oldRank) {
                Assert.fail("Results are not ranked");
            }
        }
    }


}
