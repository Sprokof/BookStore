package online.book.store.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.book.store.builder.AbstractUserBuilder;
import online.book.store.entity.User;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class UserLoginDto extends AbstractUserBuilder {

    private String email;
    private String confirmCode;
    private boolean accepted;

    public UserLoginDto(String email){
        this.email = email;
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
    public User buildUser() {
        if(!containsNull()){
            return new User(this.email);
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
                buildUser();
    }


    private String generateConfirmCode() {
        String[] temp = new String[7];
        StringBuilder sb = new StringBuilder("");

        for (int i = 0; i < temp.length; i++) {
            double d = (Math.random() * 10);
            temp[i] = String.valueOf((int) d);
        }
        int index = 0;
        while (index != temp.length) {
            sb.append(temp[index]);

            index ++ ;
        }

        return sb.toString();

    }


}
