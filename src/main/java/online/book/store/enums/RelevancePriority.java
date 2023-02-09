package online.book.store.enums;

import lombok.Getter;

@Getter
public enum RelevancePriority {
    A(6, "isbn"),
    B(5, "title"),
    C(4, "authors"),
    D(3, "description"),
    E(2, "subject"),
    F(1, "publisher");

    private final int value;
    private final String coincidenceField;

    RelevancePriority(int value, String coincidenceField) {
        this.value = value;
        this.coincidenceField = coincidenceField;
    }

    public static RelevancePriority valueOfField(String field){
        RelevancePriority priority = null;
        for(RelevancePriority p : RelevancePriority.values()){
            if(p.getCoincidenceField().equals(field)) priority = p;
        }
    return priority;
    }

    @Override
    public String toString() {
        return "RotationPriority {" +
                "value=" + value +
                ", coincidenceField='" + coincidenceField + '\'' +
                '}';
    }
}
