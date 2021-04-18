package db.mongo.todolist.controllers;

import db.mongo.todolist.models.Category;
import db.mongo.todolist.models.CategoryTO;
import db.mongo.todolist.services.interfaces.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = "http://localhost:4200")
@RestController
@RequestMapping(value = "/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping(value = "/getAll")
    public List<Category> getCategories(){
        return this.categoryService.findAll();
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addCategories(@RequestBody CategoryTO category){
        Category ct = new Category(category.getCategory());
        try {
            Category cat =  this.categoryService.addCategory(ct);
            return ResponseEntity.ok(cat);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
