package db.mongo.todolist.services.implementation;

import db.mongo.todolist.models.Category;
import db.mongo.todolist.repositories.CategoryRepository;
import db.mongo.todolist.services.interfaces.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll(){
        return this.categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) throws Exception {
        if(!this.categoryRepository.existsByCategoryName(category.getCategoryName())){
            return this.categoryRepository.save(category);
        }
        throw new Exception("The category already exists");
    }

    @Override
    public Boolean existsByCategoryName(String categoryName){
        return this.categoryRepository.existsByCategoryName(categoryName);
    }

    @Override
    public Category findByCategoryName(String category) {
        return this.categoryRepository.findByCategoryName(category);
    }


}
