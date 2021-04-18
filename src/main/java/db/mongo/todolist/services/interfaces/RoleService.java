package db.mongo.todolist.services.interfaces;

import db.mongo.todolist.models.AuthLevel;
import db.mongo.todolist.models.Role;

import java.util.Optional;

public interface RoleService {

    public Optional<Role> findByRole(AuthLevel role);

    public Boolean existsByRole(AuthLevel role);
}
