package db.mongo.todolist.services.interfaces;

import db.mongo.todolist.exceptions.NoTodoItemsException;
import db.mongo.todolist.exceptions.UnfinishedTodosException;
import db.mongo.todolist.exceptions.UsernameNotFoundException;
import db.mongo.todolist.models.*;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Boolean existsByUsername(String username);

    User insert(User rootUser);

    List<User> findAll();

    Optional<User> findByUsername(String username);

    UserTO getUserTO(String username);

    Boolean existsByEmail(String email);

    void deleteByUsername(String username) throws UsernameNotFoundException, UnfinishedTodosException;

    List<TodoItem> getTodoItemsOfUser() throws NoTodoItemsException, UsernameNotFoundException;

    UserTO deleteTodoItemFromUser(String itemId) throws Exception;

    UserTO addNewUser(SignUpRequest signUpRequest) throws Exception;

    User save(User userAltered);

    AuthenticationResponse authenticateUser(AuthenticationRequest authRequest) throws Exception;

    UserTO addTodoItemToUser(TodoItemTO todoItemTO) throws Exception;

    List<UserTO> findAll_TO();
}
