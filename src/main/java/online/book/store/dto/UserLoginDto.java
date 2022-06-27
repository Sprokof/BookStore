package online.book.store.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.book.store.builder.AbstractUserBuilder;
import online.book.store.entity.User;

import java.lang.reflect.Field;

@NoArgsConstructor
@Getter
@Setter
public class UserLoginDto extends AbstractUserBuilder {

    private String email;
    private String username;
    private String password;

    public UserLoginDto(String email, String username, String password){
        this.email = email;
        this.username = username;
        this.password = password;
    }

    //implementation of builder//


    @Override
    public AbstractUserBuilder builder() {
        return new UserLoginDto();
    }

    @Override
    public AbstractUserBuilder email(String email) {
        this.email = email;
        return this;
    }

    @Override
    public AbstractUserBuilder username(String username) {
        this.username = username;
        return this;
    }

    @Override
    public AbstractUserBuilder password(String password) {
        this.password = password;
        return this;
    }

    @Override
    public User buildUser() {
        if(!containsNull()){
            return new User(this.email, this.password);
        }
    return null;
    }

    @Override
    public boolean containsNull() {
        Field[] userFields = this.getClass().getFields();

        int fieldsLength = userFields.length, index = 0;

            while(index != fieldsLength){
                if(userFields[index] == null){
                    return true;
                }
               index  ++ ;
            }
        return false;
    }

    public User doUserBuilder() {
        return this.builder().
                email(this.getEmail()).
                username(this.getUsername()).
                password(this.getPassword()).
                buildUser();
    }
}
