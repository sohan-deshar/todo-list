package db.mongo.todolist.models;

public class UserTO {

    private String id;
    private String username;
    private String email;
    private String[] roles;
    private Integer numberOfTodoItems;

    public UserTO(String id, String username, String email, String[] roles, Integer numberOfTodoItems) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.numberOfTodoItems = numberOfTodoItems;
    }

    public UserTO() {
    }

    public String getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public String[] getRoles() {
        return this.roles;
    }

    public Integer getNumberOfTodoItems() {
        return this.numberOfTodoItems;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public void setNumberOfTodoItems(Integer numberOfTodoItems) {
        this.numberOfTodoItems = numberOfTodoItems;
    }
}
