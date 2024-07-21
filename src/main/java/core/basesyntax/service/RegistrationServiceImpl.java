package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());
        checkLoginExists(user.getLogin());
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null.");
        }
    }

    private void validateLogin(String login) {
        if (isNullOrShort(login, MIN_LOGIN_LENGTH)) {
            throw new RegistrationException("Login can't be empty or less than "
                    + MIN_LOGIN_LENGTH + " characters.");
        }
    }

    private void validatePassword(String password) {
        if (isNullOrShort(password, MIN_PASSWORD_LENGTH)) {
            throw new RegistrationException("Password can't be empty or less than "
                    + MIN_PASSWORD_LENGTH + " characters.");
        }
    }

    private void validateAge(Integer age) {
        if (age == null || age < MIN_AGE) {
            throw new RegistrationException("Age can't be empty or less than "
                    + MIN_AGE + " years.");
        }
    }

    private void checkLoginExists(String login) {
        if (storageDao.get(login) != null) {
            throw new RegistrationException("Login already exists.");
        }
    }

    private boolean isNullOrShort(String value, int minLength) {
        return value == null || value.length() < minLength;
    }
}
