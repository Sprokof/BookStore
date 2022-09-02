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
    private boolean remembered;
    private String ipAddress;


    public UserLoginDto(String login, String password, boolean remembered, String ipAddress) {
        this.login = login;
        this.password = password;
        this.remembered = remembered;
        this.ipAddress = ipAddress;
    }

}