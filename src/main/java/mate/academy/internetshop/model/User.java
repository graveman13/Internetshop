package mate.academy.internetshop.model;

public class User {
    private String name;
    private Long userId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return this.name.equals(user.getName())
                && this.userId.equals(user.getUserId());
    }

    @Override
    public int hashCode() {
        int prime = 31;
        prime = prime + (name == null ? 0 : name.hashCode());
        prime = prime + (userId == null ? 0 : userId.hashCode());
        return prime;
    }
}
