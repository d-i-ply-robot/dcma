package com.hackingismakingisengineering.dcma.controller;

import com.hackingismakingisengineering.dcma.data.CategoryRepository;
import com.hackingismakingisengineering.dcma.data.ProgramRepository;
import com.hackingismakingisengineering.dcma.model.Category;
import com.hackingismakingisengineering.dcma.model.Program;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProgramRepository programRepository;

    @RequestMapping("/category")
    private String listCategories(ModelMap modelMap){


        List<Category> categoryList = categoryRepository.getCategoryRepository();

        modelMap.put("categories", categoryList);

        return "categories";


    }

    @RequestMapping("/categories/{id}")
    private String listCategories(@PathVariable int id, ModelMap modelMap){

        Category category = categoryRepository.getCatergoryById(id);

        modelMap.put("category", category);

        List<Program> programList = programRepository.getProgramByCategoryId(id);

        modelMap.put("programs", programList);

        return "category-details";

    }


}
