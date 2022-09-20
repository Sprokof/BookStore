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
public class UserLoginDto extends AbstractUserBuilder{

    private String login;
    private String password;




    public UserLoginDto(String login, String password) {
        this.login = login;
        this.password = password;

    }

    @Override
    public String toString() {
        return "UserLoginDto{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}