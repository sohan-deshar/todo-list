package db.mongo.todolist.repositories;

import db.mongo.todolist.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    public Boolean existsByUsername(String root_username);

    public Optional<User> findByUsername(String username);

    public Boolean existsByEmail(String email);

    Long deleteByUsername(String username);

}
