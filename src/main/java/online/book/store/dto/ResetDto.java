package online.book.store.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class ResetDto {
    public final static int DAILY_RESET_LIMIT = 3;

    @Getter
    @Setter
    private String login;

    @Getter
    @Setter
    private String newPassword;

    @Getter
    @Setter
    private String confirmResetPassword;

    @Getter
    @Setter
    private String generatedCode;

    @Getter
    private String inputCode;



    @Override
    public String toString() {
        return "ResetDto{" +
                "newPassword='" + newPassword + '\'' +
                ", confirmResetPassword='" + confirmResetPassword + '\'' +
                ", confirmCode='" + generatedCode + '\'' +
                '}';
    }


    public void setInputCode(String inputCode) {
        this.inputCode = inputCode.replaceAll("\\p{P}", "");
    }
}
