package online.book.store.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.book.store.builder.AbstractUserBuilder;
import online.book.store.entity.User;

import java.lang.reflect.Field;

@Getter
@Setter
@NoArgsConstructor
public class UserDto extends AbstractUserBuilder {

    private String login;
    private boolean isAdmin;
    private String sessionid;
    private String password;
    private String confirmPassword;
    private String email;
    private String username;
    private String remember;
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




}
