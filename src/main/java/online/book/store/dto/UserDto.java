package online.book.store.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.book.store.builder.AbstractUserBuilder;
import online.book.store.entity.User;

import java.lang.reflect.Field;

@NoArgsConstructor
public class UserDto extends AbstractUserBuilder {

    private String login;
    private boolean isAdmin;
    private String sessionid;
    private String password;
    private String confirmPassword;
    private String email;
    private String username;
    private boolean accepted;

    public UserDto(String username, String email) {
        this.username = username;
        this.email = email;
    }

    @Override
    public AbstractUserBuilder builder() {
        return this;
    }

    @Override
    public AbstractUserBuilder username(String username) {
        this.username = username;
        return this;
    }

    @Override
    public AbstractUserBuilder email(String email) {
        this.email = email;
        return this;
    }

    @Override
    public AbstractUserBuilder password(String password) {
        this.password = password;
        return this;
    }


    @Override
    public boolean containsNull() {
        Field[] userFields = this.getClass().getDeclaredFields();
        for(Field field : userFields){
            if(field == null) return true;
        }
        return false;
    }

    @Override
    public User buildUser() {
        User user = null;
        if(!containsNull()){
            user = new User(this.username, this.email,
                    this.password);
        }
        return user;
    }


    public User doUserBuilder(){
        return builder().
                username(this.username).
                email(this.email).
                password(this.password).
                buildUser();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
