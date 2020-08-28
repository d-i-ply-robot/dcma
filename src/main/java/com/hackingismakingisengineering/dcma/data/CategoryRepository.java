package com.hackingismakingisengineering.dcma.data;

import com.hackingismakingisengineering.dcma.model.Category;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CategoryRepository {

    private static final  List<Category> categoryRepository = Arrays.asList(
            new Category(1L, "Streetscape"),
            new Category(2L, "Motroway-widening"),
            new Category(3L, "Trenchless-pipeline"),
            new Category(4L, "Developement"),
            new Category(5L, "Bridge"),
            new Category(5L, "Earthworks"));

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

    public Category getCatergoryById(Long id){

        for(Category category: categoryRepository){
            if(category.getId().equals(id)) {
                return category;
            }
        }
        return null;
    }

}
