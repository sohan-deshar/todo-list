package db.mongo.todolist.services.interfaces;

import db.mongo.todolist.models.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    Category addCategory(Category category) throws Exception;

    Boolean existsByCategoryName(String categoryName);

    Category findByCategoryName(String category);
}
