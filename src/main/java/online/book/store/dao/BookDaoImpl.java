package online.book.store.dao;

import online.book.store.entity.Book;
import online.book.store.entity.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import java.util.LinkedList;
import java.util.List;

import static online.book.store.entity.BookReview.MAX_POPULAR_RATING;
import static online.book.store.entity.BookReview.MIN_POPULAR_RATING;


@Component
public class BookDaoImpl implements BookDao {

    private final SessionFactory sessionFactory =
            SessionFactorySingleton.getInitializationFactory();

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> getAllBooks() {
        Session session = null;
        List<Book> books = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            books = session.createSQLQuery("SELECT * FROM BOOKS").
                    addEntity(Book.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return books;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Book> getPopularBooks() {
        Session session = null;
        List<Book> resultedList = new LinkedList<>();
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            resultedList = (LinkedList<Book>) session.
                    createSQLQuery("SELECT * FROM " +
                            "BOOKS as book JOIN BOOKS_REVIEWS as reviews" +
                            " on book.id = reviews.id WHERE BOOK_RATING " +
                            "BETWEEN :min_rating and :max_rating ").
                    addEntity(Book.class).
                    setParameter("min_rating", MIN_POPULAR_RATING).
                    setParameter("max_rating", MAX_POPULAR_RATING).list();

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return resultedList;
    }




    @Override
    @SuppressWarnings("unchecked")
    public List<Book> getBooksByCategory(String category) {
        Session session = null;
        List<Book> books = new LinkedList<>();
        Category currentCategory = new Category(category);
    try{
        session = this.sessionFactory.openSession();
        session.beginTransaction();
        books = (LinkedList<Book>) session.
                createSQLQuery("SELECT * FROM BOOKS").
                addEntity(Category.class);
        session.getTransaction().commit();
    }
    catch (Exception e) {
        if (session != null) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        }
    } finally {
        if (session != null) {
            session.close();
        }
    }
    List<Book> resultedBooksList = new LinkedList<>();
    for(Book book : books){
        if(getBookByCategory(book, currentCategory) != null){
            if(resultedBooksList.contains(book)){
                continue;
            }
            resultedBooksList.add(book);
        }
    }
    return resultedBooksList;

    }

    // That method is needed for getting info
    @Override
    public Book getBookByIsbn(String isbn) {
        Session session = null;
        Book book = null;
    try {
        session = this.sessionFactory.openSession();
        session.beginTransaction();
        book = (Book) session.createSQLQuery("SELECT * FROM BOOKS WHERE ISBN=:isbn").
                setParameter("isbn", isbn).addEntity(Book.class).getSingleResult();
        session.getTransaction().commit();
    }
    catch (Exception e) {
            if (session != null) {
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                    if(e instanceof NoResultException) return null;
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

    return book;

    }

    @Override
    public Book getBookById(int id) {
        Session session = null;
        Book book = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            book = session.get(Book.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return book;
    }

    @Override
    public void saveBook(Book book) {
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            session.save(book);
            session.getTransaction();
        } catch (Exception e) {
            if (session != null) {
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private Book getBookByCategory(Book book, Category bookCategory){
        if(book.getCategories().contains(bookCategory)){
            return book;
        }
    return null;
    }

    @Override
    public Book getBookByTitle(String title) {
        Session session = null;
        Book book = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            book = (Book) session.createSQLQuery("SELECT * FROM BOOKS WHERE TITLE=:title").
                    setParameter("isbn", title).addEntity(Book.class).getSingleResult();
            session.getTransaction().commit();
        }
        catch (Exception e) {
            if (session != null) {
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                    if(e instanceof NoResultException) return null;
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return book;

    }

    @Override
    public double averageRating(Integer bookId) {
        Session session = null;
        double rating = 0;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            rating = (double) session.createSQLQuery("SELECT AVG(BOOK_RATING) FROM " +
                            "BOOKS_REVIEWS WHERE id=:book_id").
                    setParameter("book_id", bookId).getSingleResult();
            session.getTransaction().commit();
        }
        catch (Exception e) {
            if (session != null) {
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                    if(e instanceof NoResultException) return 0d;
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return rating;
    }
}
