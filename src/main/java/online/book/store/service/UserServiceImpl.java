package online.book.store.service;

import online.book.store.dao.UserDao;
import online.book.store.dto.UserLoginDto;
import online.book.store.entity.Book;
import online.book.store.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void saveUser(User user) {
        this.userDao.saveUser(user);

    }

    @Override
    public void updateUser(User user) {
        this.userDao.updateUser(user);

    }

    @Override
    public User getUserByLogin(String login) {
        return this.userDao.getUserByLogin(login);
    }

    @Override
    public User loadUserByLogin(String login) {
        User user;
        if ((user = (this.userDao.getUserByLogin(login))) != null) {
            return user;
        }
        return null;
    }

    @Override
    public boolean authUser(User userToLogin, User userFromDb) {

        return userToLogin.equals(userFromDb);
    }


    @Override
    public void addBookToWishList(User currentUser, Book book) {
        if(currentUser.getWishList().getBooksId() == null) {
            currentUser.getWishList().setBooksId(String.valueOf(book.getId()));
        }
        String booksId = currentUser.getWishList().getBooksId();
        booksId += String.format(",%d,", book.getId());
        currentUser.getWishList().setBooksId(booksId);
        this.userDao.updateUser(currentUser);
    }
}
