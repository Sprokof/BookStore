import online.book.store.engines.SearchQuery;
import online.book.store.engines.SiteEngine;
import online.book.store.entity.Book;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class SiteEngineTests {

    SiteEngine engine = new SiteEngine();

    @Test
    public void kmpMatcher(){
        Book book = new Book();
        book.setTitle("TITLE");
        book.setAuthors("AUTHOR");
        SearchQuery searchQuery = new SearchQuery("TITLE");
        engine.kmpMatcher(book, searchQuery);
        assertFalse(engine.getSearchResults().isEmpty());
    }
}
