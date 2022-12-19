package online.book.store.enums;

import lombok.Getter;

@Getter
public enum RotationPriority {
    A(6, "isbn"),
    B(5, "title"),
    C(4, "authors"),
    D(3, "description"),
    E(2, "subject"),
    F(1, "publisher");

    private final int value;
    private final String coincidenceField;

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

    @Override
    public String toString() {
        return "RotationPriority{" +
                "value=" + value +
                ", coincidenceField='" + coincidenceField + '\'' +
                '}';
    }
}
