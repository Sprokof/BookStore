package online.book.store.enums;

import lombok.Getter;

public enum Role {
    ADMIN("admin"),
    USER("user");

    @Getter
    private final String role;

    Role(String role){
        this.role = role;
    }

    public static Role toRole(String role){
        Role foundRole = null;
        for(Role r: Role.values()){
            if(r.getRole().equalsIgnoreCase(role)) foundRole = r;
        }
        return foundRole;
    }
}
