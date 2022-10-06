package online.book.store.engines;

import lombok.Getter;

@Getter
public enum SortTypes {

    POPULARITY("Popularity"),
    HIGHEST("Price High To Low"),
    LOWEST("Price Low To High"),
    LATEST("Latest Release");

    private final String type;


    SortTypes(String type){
        this.type = type;
    }



    public static SortTypes getTypeByName(String name){
        for(SortTypes type: SortTypes.values()){
            if(type.getType().equalsIgnoreCase(name)) return type;
        }
    return null;
    }

}
