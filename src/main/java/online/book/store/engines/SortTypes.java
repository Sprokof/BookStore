package online.book.store.engines;

import lombok.Getter;

@Getter
public enum SortTypes {

    POPULARITY("Popularity"),
    HIGHEST("High no Low"),
    LOWEST("Low to High"),
    LATEST("Latest"),

    DEFAULT("Sort");


    private String sortType;

    SortTypes(String sortType){
        this.sortType = sortType;
    }


    public static SortTypes getTypeByName(String name){
        for(SortTypes type: SortTypes.values()){
            if(type.name().equals(name)) return type;
        }
    return null;
    }

}
