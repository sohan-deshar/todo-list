package db.mongo.todolist.models.transferobjs.converters;

import db.mongo.todolist.models.entity.Category;
import db.mongo.todolist.models.entity.TodoItem;
import db.mongo.todolist.models.transferobjs.TodoItemTO;
import db.mongo.todolist.services.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TodoConverter {

    @Autowired
    private CategoryService categoryService;

    public TodoItemTO TodoItem_TO_converter(TodoItem todoItem){
        TodoItemTO retItem = new TodoItemTO();
        retItem.setId(todoItem.getId());
        retItem.setCategory(todoItem.getCategory().getCategoryName());
        retItem.setTodoType(todoItem.getTodoType());
        retItem.setTodoDetails(todoItem.getTodoDetails());
        retItem.setDueDate(todoItem.getDueDate());
        return retItem;
    }

    public TodoItem TO_TodoItem_converter(TodoItemTO todoItemTO) throws Exception {
        Category catOfTO;
        if(this.categoryService.existsByCategoryName(todoItemTO.getCategory())){
            catOfTO = this.categoryService.findByCategoryName(todoItemTO.getCategory());
        } else {
            catOfTO = this.categoryService.addCategory(new Category(todoItemTO.getCategory()));
        }
        TodoItem todoItem = new TodoItem(todoItemTO);
        todoItem.setCategory(catOfTO);
        return todoItem;
    }

}
