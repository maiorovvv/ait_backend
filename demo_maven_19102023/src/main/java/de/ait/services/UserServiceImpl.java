package de.ait.services;

import de.ait.model.User;
import de.ait.repositories.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private static final String MSG_BLANK_ARGUMENT_ERR="The name and email should not be blank";
    private static final String MSG_EMAIL_NOT_CORRECT_ERR="Incorrect email format";
    private static final String MSG_EMAIL_DUPLICATE_ERR="The user with email is exist";
    private UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createUser(String name, String email) {
        try {
            checkUserArguments(name, email);
            repository.save(new User(name,email));
        } catch (Exception e){
            String msg = String.format("Error create user: name:%s email:%s%n%s%n ", name, email, e.getMessage());
            throw new RuntimeException(msg);
        }
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public void updateUser(Long userID, String newName, String newEmail) {
        try {
            checkUserArguments(newName, newEmail);
            User user = repository.findByID(userID);
            user.setName(newName);
            user.setEmail(newEmail);
            repository.update(user);
        } catch (Exception e){
            String msg = String.format("Error update user: name:%s email:%s%n%s%n ", newName, newEmail, e.getMessage());
            throw new RuntimeException(msg);
        }
    }

    private void checkUserArguments(String name,String email){
        if (name==null ||  email==null || name.isBlank() || email.isBlank() ) {
            throw new IllegalArgumentException(MSG_BLANK_ARGUMENT_ERR);
        }
        if (email.indexOf('@')<0) {
            throw new IllegalArgumentException(MSG_EMAIL_NOT_CORRECT_ERR);
        }
        if(repository.findByEmail(email)!=null) {
            throw new IllegalArgumentException(MSG_EMAIL_DUPLICATE_ERR);
        }
    }
}
