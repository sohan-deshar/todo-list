package db.mongo.todolist.models.entity;

import db.mongo.todolist.models.enums.AuthLevel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Role {

    @Id
    private String id;

    private AuthLevel role;

    public Role(AuthLevel role) {
        this.role = role;
    }

    public Role() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AuthLevel getRole() {
        return role;
    }

    public void setRole(AuthLevel role) {
        this.role = role;
    }
}
