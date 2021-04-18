package db.mongo.todolist.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    @Indexed(unique = true)
    private String email;

    private String password;

    @DBRef
    @NonNull
    private Set<Role> roles = new HashSet<>();

    private List<TodoItem> todoitems = new ArrayList<>();

    public User() {
    }

    public User(String username, String email, String password, Set<Role> roles, List<TodoItem> todoitems) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.todoitems = todoitems;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<TodoItem> getTodoitems() {
        return todoitems;
    }

    public void setTodoitems(List<TodoItem> todoitems) {
        this.todoitems = todoitems;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                ", todoitems=" + todoitems +
                '}';
    }
}
