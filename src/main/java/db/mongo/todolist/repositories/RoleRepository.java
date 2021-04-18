package db.mongo.todolist.repositories;

import db.mongo.todolist.models.AuthLevel;
import db.mongo.todolist.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {

    public Optional<Role> findByRole(AuthLevel role);

    public Boolean existsByRole(AuthLevel role);
}
