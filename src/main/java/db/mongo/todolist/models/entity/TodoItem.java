package db.mongo.todolist.models.entity;

import db.mongo.todolist.models.entity.Category;
import db.mongo.todolist.models.transferobjs.TodoItemTO;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Random;

public class TodoItem {

    private String id;

    private String todoDetails;

    @DBRef
    private Category category;

    private String todoType;
    private String dueDate;

    private Boolean finished = false;

    public TodoItem() {
    }

    public TodoItem(String todoDetails, Category category, String todoType, String dueDate) {
        this.id = generateRandomString();
        this.todoDetails = todoDetails;
        this.category = category;
        this.todoType = todoType;
        this.dueDate = dueDate;
    }

    public TodoItem(TodoItemTO todoItemTO){
        this.id = generateRandomString();
        this.todoDetails = todoItemTO.getTodoDetails();
        this.category = null;
        this.todoType = todoItemTO.getTodoType();
        this.dueDate = todoItemTO.getDueDate();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTodoDetails() {
        return todoDetails;
    }

    public void setTodoDetails(String todoDetails) {
        this.todoDetails = todoDetails;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTodoType() {
        return todoType;
    }

    public void setTodoType(String todoType) {
        this.todoType = todoType;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }


    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public String generateRandomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }
}
