package db.mongo.todolist.controllers;

import db.mongo.todolist.exceptions.NoTodoItemsException;
import db.mongo.todolist.exceptions.UsernameNotFoundException;
import db.mongo.todolist.models.transferobjs.TodoItemTO;
import db.mongo.todolist.services.interfaces.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(value = "http://localhost:4200")
@RestController
@RequestMapping(value = "/todo")
public class TodoController {

    private final UserService userService;

    public TodoController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/gettodos")
    public ResponseEntity<?> getTodos(){
        try{
            return ResponseEntity.ok(this.userService.getTodoItemsOfUser());
        } catch (NoTodoItemsException e){
            return ResponseEntity.badRequest().body(e);
        } catch (UsernameNotFoundException e){
            return ResponseEntity.badRequest().body(e);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping(value = "/addTodos")
    public ResponseEntity<?> addTodos(@RequestBody TodoItemTO todoItemTO){
        try{
            return ResponseEntity.ok(this.userService.addTodoItemToUser(todoItemTO));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @DeleteMapping(value = "/deleteTodo/{todoId}")
    public ResponseEntity<?> deleteTodos(@PathVariable String todoId){
        try {
            return ResponseEntity.ok(this.userService.deleteTodoItemFromUser(todoId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
