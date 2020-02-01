package mate.academy.internetshop.model;

import java.util.HashSet;
import java.util.Set;

public class User {
    private String name;
    private String surname;
    private String login;
    private String password;
    private byte[] salt;
    private Long userId;
    private String token;
    private Set<Role> roles;

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public User() {
        roles = new HashSet<>();
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRoles(Role role) {
        roles.add(role);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public String toString() {
        return "User "
                + "name='" + name
                + ", userId=" + userId;
    }
}
