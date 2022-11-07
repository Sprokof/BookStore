package online.book.store.dao;

import online.book.store.entity.Book;
import online.book.store.entity.Category;
import online.book.store.service.CategoryService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
            books = session.createSQLQuery("SELECT * FROM BOOKS ORDER BY ADDED_DATE LIMIT 100").
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
        List<Book> resultedList = new ArrayList<>();
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            resultedList = (ArrayList<Book>) session.
                    createSQLQuery("SELECT * FROM " +
                            "BOOKS as book JOIN BOOKS_REVIEWS as reviews" +
                            " on book.id = reviews.book_id WHERE BOOK_RATING " +
                            "BETWEEN :min_rating and :max_rating ORDER BY BOOK_RATING").
                    addEntity(Book.class).
                    setParameter("min_rating", MIN_POPULAR_RATING).
                    setParameter("max_rating", MAX_POPULAR_RATING).list();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
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
        } catch (Exception e) {
            if (session != null) {
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                    if (e instanceof NoResultException) return null;
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
        } catch (Exception e) {
            if (session != null) {
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                    if (e instanceof NoResultException) return null;
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
        BigDecimal rating = new BigDecimal(0);
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            rating = (BigDecimal) session.createSQLQuery("SELECT AVG(BOOK_RATING) FROM " +
                            "BOOKS_REVIEWS WHERE book_id=:bookId").
                    setParameter("bookId", bookId).getSingleResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                    if (e instanceof NoResultException) return 0d;
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        if(rating == null) return 0d;
        return rating.doubleValue();
    }

    @Override
    public void insertBookAndCategories(int bookId, int categoryId) {
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            session.createSQLQuery("INSERT INTO BOOKS_CATEGORIES " +
                            "(book_id, category_id) " +
                            "VALUES (:bookId, :categoryId)").
                    setParameter("bookId", bookId).
                    setParameter("categoryId", categoryId).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void updateBook(Book book) {
        Session session = null;
    try {
        session = this.sessionFactory.openSession();
        session.beginTransaction();
        session.update(book);
        session.getTransaction().commit();
    }
    catch (Exception e){
        if(session != null && session.getTransaction() != null){
            session.getTransaction().rollback();
        }
    }
    finally {
        if(session != null){
            session.close();
        }
    }
    }

    @Override
    public boolean reviewExist(int bookId, int userId) {
        Session session = null;
        String review = null;
    try {
        session = this.sessionFactory.openSession();
        session.beginTransaction();
        review = (String) session.createSQLQuery("SELECT REVIEW FROM BOOKS_REVIEWS " +
                "WHERE book_id=:bookId AND user_id=:userId")
                .setParameter("bookId", bookId)
                .setParameter("userId", userId)
                .getSingleResult();
        session.getTransaction().commit();
    }
    catch (Exception e){
        e.printStackTrace();
        if(session != null && session.getTransaction() != null){
            session.getTransaction().rollback();
        }
    }
    finally {
        if (session != null) {
            session.close();
        }
    }
    return (review != null);
    }
}


