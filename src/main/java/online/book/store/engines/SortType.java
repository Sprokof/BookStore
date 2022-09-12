package online.book.store.engines;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
public enum SortType {

    POPULARITY("Popularity"),
    HIGHEST("Price High To Low"),
    LOWEST("Price Low To High"),
    LATEST("Latest Release");

    private final String type;


    SortType (String type){
        this.type = type;
    }



    public static SortType getTypeByName(String name){
        for(SortType type: SortType.values()){
            if(type.name().equalsIgnoreCase(name)) return type;
        }
    return null;
    }

}
