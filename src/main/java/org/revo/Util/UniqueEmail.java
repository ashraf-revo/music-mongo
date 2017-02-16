package org.revo.Util;

import org.revo.Domain.User;
import org.revo.Service.UserService;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by ashraf on 13/02/17.
 */
public class UniqueEmail implements Validator {
    private final UserService userService;

    public UniqueEmail(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        User user = (User) target;
        if (userService.findByEmail(user.getEmail()).isPresent())
            errors.rejectValue("email", "email", "please choose unique email");
    }
}
