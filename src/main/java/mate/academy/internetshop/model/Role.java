package mate.academy.internetshop.model;

public class Role {
    private final Long id;
    private static Long generatorId = 0L;
    private RoleName roleName;

    public Role() {
        this.id = generatorId++;
    }

    public Role(RoleName roleName) {
        this();
        this.roleName = roleName;
    }

    public Long getID() {
        return id;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }

    public static Role of(String role) {
        return new Role(RoleName.valueOf(role));
    }

    public enum RoleName {
        USER, ADMIN;
    }
}
