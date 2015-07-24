package com.tri.erp.spring.service.implementations;

import com.tri.erp.spring.commons.facade.AuthenticationFacade;
import com.tri.erp.spring.commons.helpers.Checker;
import com.tri.erp.spring.commons.helpers.MessageFormatter;
import com.tri.erp.spring.model.Role;
import com.tri.erp.spring.model.User;
import com.tri.erp.spring.repo.UserRepo;
import com.tri.erp.spring.response.PostResponse;
import com.tri.erp.spring.response.PostUserResponse;
import com.tri.erp.spring.service.interfaces.UserService;
import com.tri.erp.spring.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * Created by TSI Admin on 10/9/2014.
 */


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    UserRepo userRepo;

    @Override
    public User create(User user) {
        User newUser = null;
        int row = userRepo.save(
                user.getFullName(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.isEnabled(),
                user.getCreatedBy() == null ? 0 : user.getCreatedBy().getId());

        if (row > 0) {
            newUser = userRepo.findOneByUsername(user.getUsername());
        }
        return newUser;
    }

    @Override
    public User findByEmail(String email) {
        List<User> users = userRepo.findByEmail(email);
        return this.getOne(users);
    }

    @Override
    public User findByUsername(String username) {
        User user =  userRepo.findOneByUsername(username);
        return user;
    }

    private User getOne(List<User> users) {
        if (!Checker.collectionIsEmpty(users)) {
            return users.get(0);
        } else return null;
    }

    @Override
    public User findById(Integer id) {
        User user = userRepo.findOne(id);

        if (user != null) { // get roles
            List<Object[]> rolesObj = userRepo.findRolesByUserId(user.getId());
            if (!Checker.collectionIsEmpty(rolesObj)) {
                for (Object[] obj : rolesObj) {
                    Role role = new Role();
                    role.setId((Integer)obj[0]);
                    role.setName((String)obj[1]);

                    user.getRoles().add(role);
                }
            }
        }
        return user;
    }

    @Override
    public PostResponse processUpdate(User user, BindingResult bindingResult, MessageSource messageSource) {
        return processCreate(user, bindingResult, messageSource);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    @Transactional
    public PostResponse processCreate(User user, BindingResult bindingResult, MessageSource messageSource) {
        PostResponse response = new PostUserResponse();
        MessageFormatter messageFormatter = new MessageFormatter(bindingResult, messageSource, response);

        UserValidator validator = new UserValidator();
        validator.setService(this);
        validator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            messageFormatter.buildErrorMessages();
            response = messageFormatter.getResponse();
            response.setSuccess(false);
        } else {

            user.setUpdatedAt(null); // use mysql's default

            User newUser = user;
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            if (user.getId() == null || user.getId() == 0 ) {   // insert mode
                User createdBy = authenticationFacade.getLoggedIn();
                user.setCreatedBy(createdBy);

                String hashedPassword = passwordEncoder.encode(user.getPassword());

                user.setPassword(hashedPassword);

                newUser = create(user);

                // user roles
                if (!Checker.collectionIsEmpty(user.getRoles())) {
                    for (Role role : user.getRoles()) {
                        userRepo.saveRoles(newUser.getId(), role.getId());
                    }
                }

            } else {    // update mode

                if (!Checker.isStringNullAndEmpty(user.getPassword())) { // has new password
                    String hashedPassword = passwordEncoder.encode(user.getPassword());
                    user.setPassword(hashedPassword);

                    userRepo.saveWithPassword(
                            user.getId(),
                            user.getFullName(),
                            user.getUsername(),
                            user.getEmail(),
                            user.isEnabled(),
                            user.getPassword()
                    );

                } else {
                    userRepo.saveWoPassword(
                            user.getId(),
                            user.getFullName(),
                            user.getUsername(),
                            user.getEmail(),
                            user.isEnabled()
                    );
                }

                // user roles
                userRepo.removeRoles(user.getId());

                if (!Checker.collectionIsEmpty(user.getRoles())) {
                    for (Role role : user.getRoles()) {
                        userRepo.saveRoles(newUser.getId(), role.getId());
                    }
                }
            }

            response.setModelId(newUser.getId());
            response.setSuccessMessage("User successfully saved!");
            response.setSuccess(true);
        }

        return response;
    }
}
