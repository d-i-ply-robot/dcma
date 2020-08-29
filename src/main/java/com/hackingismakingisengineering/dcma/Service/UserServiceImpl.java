package com.hackingismakingisengineering.dcma.Service;
/*
import com.hackingismakingisengineering.dcma.data.UserRepository;
import com.hackingismakingisengineering.dcma.model.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired //TODO: Not sure if this is valid - not using DAO pattern
    private UserRepository userRepository;

    @Override
    public User findByUserName(String username) {
        return userRepository.getUserByName(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //Load user from databse (throw excetion if not found
        User user = userRepository.getUserByName(username);

        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        //Return user object

        return user;
    }
}*/
