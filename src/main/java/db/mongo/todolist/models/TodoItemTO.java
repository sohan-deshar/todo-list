package db.mongo.todolist.models;

public class TodoItemTO {

    private String id;
    private String todoDetails;
    private String category;
    private String todoType;
    private String dueDate;

    public TodoItemTO(String todoDetails, String todoType, String dueDate) {
        this.todoDetails = todoDetails;
        this.todoType = todoType;
        this.dueDate = dueDate;
        this.category = null;
    }

    public TodoItemTO(String todoDetails, String category, String todoType, String dueDate) {
        this.todoDetails = todoDetails;
        this.category = category;
        this.todoType = todoType;
        this.dueDate = dueDate;
    }

    public TodoItemTO() {
    }

    public String getId() {
        return this.id;
    }

    public String getTodoDetails() {
        return this.todoDetails;
    }

    public String getCategory() {
        return this.category;
    }

    public String getTodoType() {
        return this.todoType;
    }

    public String getDueDate() {
        return this.dueDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTodoDetails(String todoDetails) {
        this.todoDetails = todoDetails;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTodoType(String todoType) {
        this.todoType = todoType;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
