package mate.academy.internetshop.service;

import java.util.Optional;

import mate.academy.internetshop.exceptions.AuthenticatioException;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.model.User;

public interface UserService extends GenericService<User, Long> {
    User login(String login, String password)
            throws AuthenticatioException, DataProcessingException;

    Optional<User> getByToken(String token) throws DataProcessingException;
}
