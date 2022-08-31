package online.book.store.dto;

import lombok.Getter;
import lombok.Setter;
import online.book.store.builder.AbstractUserBuilder;
import online.book.store.entity.User;

import java.lang.reflect.Field;


@Getter
@Setter
public class UserDto extends AbstractUserBuilder {

    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String ipAddress;
    private boolean remembered;


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
        Field[] userFields = this.getClass().getFields();
        for(Field field : userFields){
            if(field == null) return true;
        }
    return false;
    }

    @Override
    public User buildUser() {
        if(!containsNull()){
            return new User(this.username, this.email, this.password, this.ipAddress, this.remembered);
        }
    return null;
    }

    @Override
    public AbstractUserBuilder ipAddress(String ip) {
        this.ipAddress = ip;
        return this;
    }

    @Override
    public AbstractUserBuilder remembered(boolean remembered) {
        this.remembered = remembered;
        return this;
    }

    public User doUserBuilder(){
        return builder().
                username(this.username).
                email(this.email).
                password(this.password).
                ipAddress(this.ipAddress).
                remembered(this.remembered).
                buildUser();
    }
}
