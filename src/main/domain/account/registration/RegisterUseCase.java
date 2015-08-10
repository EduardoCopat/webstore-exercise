package main.domain.account.registration;

import main.domain.account.Email;
import main.domain.account.Password;
import main.domain.account.User;
import main.persistence.account.UserRepository;

public class RegisterUseCase {
    private final UserRepository repository;
    private final Email email;
    private final Password password;
    private final Password passwordConfirmation;
    private final RegisterResponse response;

    public RegisterUseCase(UserRepository repository, RegisterRequest request, RegisterResponse response) {
        this.repository = repository;
        email = new Email(request.email);
        password = new Password(request.password);
        passwordConfirmation = new Password(request.passwordConfirmation);
        this.response = response;
    }

    public void execute() {
        if (isValidRequest())
            register();
        else
            sendErrors();
    }

    private boolean isValidRequest() {
        return email.isValid() && password.isValid() && password.equals(passwordConfirmation);
    }

    private void register() {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        repository.save(user);
        response.success = true;
        response.id = user.getId();
    }

    private void sendErrors() {
        response.invalidEmail = !email.isValid();
        response.invalidPassword = !password.isValid();
        response.invalidPasswordConfirmation = !password.equals(passwordConfirmation);
    }
}