package com.hackingismakingisengineering.dcma.data;

import com.hackingismakingisengineering.dcma.model.Category;
import com.hackingismakingisengineering.dcma.model.User.User;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserRepository {

    private static final  List<User> userRepository = Arrays.asList(
            new User("user", true, "$2y$10$1qewptzS39OOa2dKe0bfieilrFBHdmf7IRyXLtgjKbb9XfGOOjk1G", 1),
            new User("admin", true, "password", 2));

    public List<User> getUserRepository() {
        return userRepository;
    }

    public User getUserByName(String name){

        for(User user: userRepository){
            if(user.getUsername().equals(name)) {
                return user;
            }
        }
        return null;
    }

    public User getUserById(Long id){

        for(User user: userRepository){
            if(user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

}
