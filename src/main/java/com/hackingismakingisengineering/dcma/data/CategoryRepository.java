package com.hackingismakingisengineering.dcma.data;

import com.hackingismakingisengineering.dcma.model.Category;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CategoryRepository {

    private static final  List<Category> categoryRepository = Arrays.asList(
            new Category(1, "Streetscape"),
            new Category(2, "Motroway-widening"),
            new Category(3, "Trenchless-pipeline"),
            new Category(4, "Developement"),
            new Category(5, "Bridge"),
            new Category(5, "Earthworks"));

    public List<Category> getCategoryRepository() {
        return categoryRepository;
    }

    public Category getCatergoryByName(String name){

        for(Category category: categoryRepository){
            if(category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }

    public Category getCatergoryById(int id){

        for(Category category: categoryRepository){
            if(category.getId() ==id) {
                return category;
            }
        }
        return null;
    }

}
