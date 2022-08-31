package online.book.store.engines;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
@AllArgsConstructor
public enum SortType {

    POPULARITY("Popularity"),
    HIGHEST("Price High To Low"),
    LOWEST("Price Low To High"),
    LATEST("Latest Release");

    String sortType;



    public static SortType getTypeByName(String name){
        for(SortType type: SortType.values()){
            if(type.name().equalsIgnoreCase(name)) return type;
        }
    return null;
    }

}
