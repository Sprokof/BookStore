package online.book.store.enums;

import lombok.Getter;

@Getter
public enum RotationPriority {
    A(5, "isbn"),
    B(4, "title"),
    C(3, "description"),
    D(2, "authors"),
    E(1, "subject");

    private int value;
    private String coincidenceField;

    RotationPriority (int value, String coincidenceField) {
        this.value = value;
        this.coincidenceField = coincidenceField;
    }

    public static RotationPriority valueOfField(String field){
        RotationPriority priority = null;
        for(RotationPriority p : RotationPriority.values()){
            if(p.getCoincidenceField().equals(field)) priority = p;
        }
    return priority;
    }

}
