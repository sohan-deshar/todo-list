package db.mongo.todolist.controllers;

import db.mongo.todolist.exceptions.NoTodoItemsException;
import db.mongo.todolist.exceptions.UsernameNotFoundException;
import db.mongo.todolist.models.transferobjs.TodoItemTO;
import db.mongo.todolist.services.interfaces.UserService;
import jdk.jfr.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(value = "http://localhost:4200")
@RestController
@RequestMapping(value = "/todo")
public class TodoController {

    private final UserService userService;

    Logger log = LoggerFactory.getLogger(TodoController.class);

    public TodoController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/gettodos")
    public ResponseEntity<?> getTodos() throws NoTodoItemsException, UsernameNotFoundException {
        return ResponseEntity.ok(this.userService.getTodoItemsOfUser());
//        try{
//        } catch (NoTodoItemsException e){
//            return ResponseEntity.badRequest().body(e);
//        } catch (UsernameNotFoundException e){
//            return ResponseEntity.badRequest().body(e);
//        } catch (Exception e){
//            return ResponseEntity.badRequest().body(e);
//        }
    }

    @PostMapping(value = "/addTodos")
    public ResponseEntity<?> addTodos(@RequestBody TodoItemTO todoItemTO) throws Exception {
        return ResponseEntity.ok(this.userService.addTodoItemToUser(todoItemTO));
//        try{
//        } catch (Exception e){
//            return ResponseEntity.badRequest().body(e);
//        }
    }

    @DeleteMapping(value = "/deleteTodo/{todoId}")
    public ResponseEntity<?> deleteTodos(@PathVariable String todoId) throws Exception {
        return ResponseEntity.ok(this.userService.deleteTodoItemFromUser(todoId));
//        try {
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exceptionHandler(Exception e){
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(e.getClass().getCanonicalName());
    }
}
