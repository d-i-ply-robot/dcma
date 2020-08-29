package com.hackingismakingisengineering.dcma.data;

import com.hackingismakingisengineering.dcma.model.Category;
import com.hackingismakingisengineering.dcma.model.User.Role;
import com.hackingismakingisengineering.dcma.model.User.User;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class RoleRepository {

    private static final  List<Role> roleRepository = Arrays.asList(
            new Role("ADMINISTRATOR"),
            new Role("USER"));

    public List<Role> getRoleRepository() {
        return roleRepository;
    }

    public Role getRoleByName(String name){

        for(Role role: roleRepository){
            if(role.getName().equals(name)) {
                return role;
            }
        }
        return null;
    }

    public Role getRoleById(Long id){

        for(Role role: roleRepository){
            if(role.getId().equals(id)) {
                return role;
            }
        }
        return null;
    }

}
