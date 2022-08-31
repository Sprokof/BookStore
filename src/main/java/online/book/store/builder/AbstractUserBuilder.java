package online.book.store.builder;

import lombok.NoArgsConstructor;
import online.book.store.entity.User;

@NoArgsConstructor
public abstract class AbstractUserBuilder {

    public AbstractUserBuilder builder(){
        return null;
    }

    public AbstractUserBuilder username(String username){
        return null;
    }

    public AbstractUserBuilder email(String email){
        return null;
    }

    public AbstractUserBuilder password(String password){
        return null;
    }

    public AbstractUserBuilder ipAddress(String ip){return null;}

    public AbstractUserBuilder remembered(boolean remembered){return null;}


    public boolean containsNull(){
        return false;
    }

    public User buildUser(){
        return null;
    }
}
