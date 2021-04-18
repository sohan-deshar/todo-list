package db.mongo.todolist.controllers;

import db.mongo.todolist.exceptions.UnfinishedTodosException;
import db.mongo.todolist.exceptions.UsernameNotFoundException;
import db.mongo.todolist.models.transferobjs.AuthenticationRequest;
import db.mongo.todolist.models.transferobjs.SignUpRequest;
import db.mongo.todolist.models.transferobjs.UserTO;
import db.mongo.todolist.services.interfaces.RoleService;
import db.mongo.todolist.services.interfaces.UserService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:4200")
@RequestMapping(value = "/user")
public class UserController {

    Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authRequest) throws Exception{
        return ResponseEntity.ok(this.userService.authenticateUser(authRequest));
//        try{
//        } catch (Exception e){
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
//        }
    }


    @GetMapping( "/showAll")
    public List<UserTO> findAll(){
        return this.userService.findAll_TO();
    }


    @GetMapping( "/show/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username){
        return ResponseEntity.ok(this.userService.getUserTO(username));
        /*try{
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }*/
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<?> addNewUser(@RequestBody SignUpRequest signUpRequest) throws Exception {
        return ResponseEntity.ok(this.userService.addNewUser(signUpRequest));
//        try{
//        } catch (Exception e){
//            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
//        }
    }


    @DeleteMapping(value = "/deleteUser/{username}")
    public ResponseEntity<?> deleteUserByUsername(@PathVariable(value = "username") String username) throws UsernameNotFoundException, UnfinishedTodosException {
        this.userService.deleteByUsername(username);
        return new ResponseEntity<>(username + " has been deleted from DB", HttpStatus.ACCEPTED);
//        try{
//        } catch (UnfinishedTodosException e) {
//            return new ResponseEntity<>(username + " still has some unfinished business " + e.getMessage(), HttpStatus.CONFLICT);
//        } catch (UsernameNotFoundException e) {
//            return new ResponseEntity<>(username + " not found in the database " + e.getMessage(), HttpStatus.NOT_FOUND);
//        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exceptionHandler(Exception e){
        log.error(String.valueOf(e.getStackTrace()));
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
