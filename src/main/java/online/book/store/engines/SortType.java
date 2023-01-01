package online.book.store.engines;

import lombok.Getter;

@Getter
public enum SortType {

    RELEVANCE("Relevance"),
    POPULARITY("Popularity"),
    HIGHEST("Price High To Low"),
    LOWEST("Price Low To High"),
    LATEST("Latest Release");

    private final String type;


    SortType(String type){
        this.type = type;
    }



    public static SortType getTypeByName(String name){
        for(SortType type: SortType.values()){
            if(type.getType().equalsIgnoreCase(name)) return type;
        }
    return null;
    }

}
