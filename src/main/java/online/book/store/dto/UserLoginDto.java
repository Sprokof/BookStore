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
public class UserLoginDto{

    private String login;
    private String password;
    private boolean remembered;


    public UserLoginDto(String login, String password, boolean remembered) {
        this.login = login;
        this.password = password;
        this.remembered = remembered;
    }

}