package db.mongo.todolist.services.interfaces;

import db.mongo.todolist.models.enums.AuthLevel;
import db.mongo.todolist.models.entity.Role;

import java.util.Optional;

public interface RoleService {

    public Optional<Role> findByRole(AuthLevel role);

    public Boolean existsByRole(AuthLevel role);
}
