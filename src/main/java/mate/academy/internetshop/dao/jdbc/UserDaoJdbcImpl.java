package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;

@Dao
public class UserDaoJdbcImpl extends AbstractDao<User> implements UserDao {
    private static final String TABLE_USERS = "internetshop.users";
    private static final String TABLE_USER_ROLE = "internetshop.user_role";
    private static final String TABLE_ROLE = "internetshop.role";

    public UserDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Optional<User> findByLogin(String login) throws DataProcessingException {
        String query = String.format("select * from %s"
                + " join %s on users.users_id = user_role.user_id"
                + " join role on user_role.role_id = role.role_id"
                + " where users.login ='%s';", TABLE_USERS, TABLE_USER_ROLE, login);
        return Optional.of(generalGetByQuery(query));
    }

    @Override
    public Optional<User> getByToken(String token) throws DataProcessingException {
        String query = String.format("select * from %s"
                + " join %s on users.users_id = user_role.user_id"
                + " join role on user_role.role_id = role.role_id"
                + " where users.token ='%s';", TABLE_USERS, TABLE_USER_ROLE, token);
        return Optional.of(generalGetByQuery(query));
    }

    @Override
    public Optional<User> get(Long id) throws DataProcessingException {
        String query = String.format("select * from %s"
                + " join %s on users.users_id = user_role.user_id"
                + " join role on user_role.role_id = role.role_id"
                + " where users.users_id ='%s';", TABLE_USERS, TABLE_USER_ROLE, id);
        return Optional.of(generalGetByQuery(query));
    }

    private User generalGetByQuery(String query) throws DataProcessingException {
        User user = new User();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                user.setUserId(rs.getLong("users_id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setToken(rs.getString("token"));
                user.setSalt(rs.getBytes("salt"));
                Role role = new Role(Role.RoleName.valueOf(rs.getString("role")));
                Set<Role> roles = new LinkedHashSet<>();
                roles.add(role);
                user.setRoles(roles);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user " + e);
        }
        return user;
    }

    @Override
    public User create(User user) throws DataProcessingException {
        String insertUser = String.format("INSERT INTO %s (name,surname,login,password,token,salt) "
                + "VALUES(?,?,?,?,?,?)", TABLE_USERS);
        try (PreparedStatement statement =
                     connection.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getLogin());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getToken());
            statement.setBytes(6, user.getSalt());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()) {
                user.setUserId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user " + e);
        }
        String setRole = String.format("INSERT INTO %s (user_id,role_id) "
                + "VALUE (?,(SELECT role_id FROM %s WHERE role =?));", TABLE_USER_ROLE, TABLE_ROLE);
        try (PreparedStatement statement = connection.prepareStatement(setRole)) {
            statement.setLong(1, user.getUserId());
            statement.setString(2, "USER");
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user role " + e);
        }
        return user;
    }

    @Override
    public User update(User user) throws DataProcessingException {
        String updateUser = String.format("update %s set users_id =?, name =?,"
                + " surname =?, login =?, password =?, token =?, salt=?"
                + " where users_id =?", TABLE_USERS);
        try (PreparedStatement statement = connection.prepareStatement(updateUser)) {
            statement.setLong(1, user.getUserId());
            statement.setString(2, user.getName());
            statement.setString(3, user.getSurname());
            statement.setString(4, user.getLogin());
            statement.setString(5, user.getPassword());
            statement.setString(6, user.getToken());
            statement.setBytes(7, user.getSalt());
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update user " + e);
        }
        return user;
    }

    @Override
    public boolean deleteId(Long id) throws DataProcessingException {
        String deleteUser = String.format("DELETE FROM %s WHERE users_id = ?", TABLE_USERS);
        try (PreparedStatement statement = connection.prepareStatement(deleteUser)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete user " + e);
        }
    }

    @Override
    public boolean delete(User user) throws DataProcessingException {
        return deleteId(user.getUserId());
    }

    @Override
    public List<User> getAll() throws DataProcessingException {
        List<User> users = new ArrayList<>();
        String getAllUsers = String.format("SELECT * FROM %s", TABLE_USERS);
        try (PreparedStatement statement = connection.prepareStatement(getAllUsers)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getLong("users_id"));
                user.setName(rs.getString("name"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setToken(rs.getString("token"));
                user.setSalt(rs.getBytes("salt"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all users " + e);
        }
        return users;
    }
}
