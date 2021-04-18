package db.mongo.todolist.models;

public class CategoryTO {

    private String category;

    public CategoryTO() {
    }

    public CategoryTO(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
