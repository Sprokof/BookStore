package online.book.store.mail;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.book.store.dto.UserLoginDto;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@NoArgsConstructor
@Getter
@Setter
public class ConfirmCode {

    private String confirmCode;

    public ConfirmCode(String confirmCode){
        this.confirmCode = confirmCode;
    }
    public static void generateNewCode(UserLoginDto user){
        StringBuilder sb = new StringBuilder();

            for(int i = 0; i < 7; i ++){
                double randomNumericValue = (Math.random() * 9);
                sb.append((int) randomNumericValue);
            }

       user.setConfirmCode(sb.toString());


    }

}
