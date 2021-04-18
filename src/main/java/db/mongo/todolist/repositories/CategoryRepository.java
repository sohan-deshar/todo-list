package db.mongo.todolist.repositories;

import db.mongo.todolist.models.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {

    Boolean existsByCategoryName(String categoryName);

    Category findByCategoryName(String category);
}
