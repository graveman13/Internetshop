package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.User;

@Dao
public class UserDaoImpl implements UserDao {
    private static Long userId = 0L;

    @Override
    public User create(User user) {
        user.setUserId(userId++);
        Storage.users.add(user);
        return user;
    }

    @Override
    public Optional<User> get(Long id) {
        return Optional.ofNullable(Storage.users.stream()
                .filter(user -> user.getUserId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Can't find user with id " + id)));
    }

    @Override
    public User update(User user) {
        User updateUser = Storage.users.stream()
                .filter(userTmp -> userTmp.getUserId().equals(user.getUserId()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Can't find user"));
        updateUser.setName(user.getName());
        return updateUser;
    }

    @Override
    public boolean deleteId(Long id) {
        return Storage.users.removeIf(user -> user.getUserId().equals(id));
    }

    @Override
    public boolean delete(User user) {
        return deleteId(user.getUserId());
    }

    @Override
    public List<User> getAll() {
        return Storage.users;
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return Storage.users.stream()
                .filter(user -> user.getLogin().equals(login)).findFirst();
    }

    @Override
    public Optional<User> getByToken(String token) {
        return Storage.users.stream()
                .filter(user -> user.getToken()
                        .equals(token)).findFirst();
    }
}
