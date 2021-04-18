package db.mongo.todolist.services.implementation;

import db.mongo.todolist.converter.TodoConverter;
import db.mongo.todolist.converter.UserConverter;
import db.mongo.todolist.exceptions.NoTodoItemsException;
import db.mongo.todolist.exceptions.UnfinishedTodosException;
import db.mongo.todolist.exceptions.UsernameNotFoundException;
import db.mongo.todolist.models.*;
import db.mongo.todolist.repositories.UserRepository;
import db.mongo.todolist.services.interfaces.RoleService;
import db.mongo.todolist.services.interfaces.UserService;
import db.mongo.todolist.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static db.mongo.todolist.models.AuthLevel.USER;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    private final TodoConverter todoConverter;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    UserServiceImpl(UserRepository userRepository,
                    AuthenticationManager authenticationManager,
                    JwtUtil jwtUtil,
                    RoleService roleService,
                    PasswordEncoder passwordEncoder,
                    TodoConverter todoConverter){
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.todoConverter = todoConverter;
    }

    @Override
    public Boolean existsByUsername(String root_username) {
        return this.userRepository.existsByUsername(root_username);
    }

    @Override
    public User insert(User user) {
        return this.userRepository.insert(user);
    }

    public User save(User user){
        return this.userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public List<UserTO> findAll_TO() {
        return this.userRepository
                .findAll()
                .stream()
                .map(UserConverter::convert_UserTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public UserTO getUserTO(String username) {
        return null;
    }

    @Override
    public Boolean existsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    @Override
    public void deleteByUsername(String username) throws UsernameNotFoundException, UnfinishedTodosException {
        Optional<User> optionalUser = this.findByUsername(username);

        if(optionalUser.isPresent()){
            if(optionalUser.get().getTodoitems() == null
                    || optionalUser
                    .get()
                    .getTodoitems()
                    .stream()
                    .map(TodoItem::getFinished)
                    .reduce( true , (currentStatus, accumulatorStatus) -> accumulatorStatus && currentStatus)
            ){
                this.userRepository.deleteByUsername(username);
            } else {
                throw new UnfinishedTodosException(username + " has some unfinished business");
            }
        } else {
            throw new UsernameNotFoundException(username + " not found");
        }
    }

    @Override
    public List<TodoItem> getTodoItemsOfUser() throws NoTodoItemsException, UsernameNotFoundException {
        String currentUser = this.getUserDetailsForCurrentUser().getUsername();
        User user = this.getUserByUsername(currentUser);
        if(user.getTodoitems() == null){
            throw new NoTodoItemsException(currentUser + " has no items in his todolist");
        } else {
            return user.getTodoitems();
        }
    }

    @Override
    public UserTO deleteTodoItemFromUser(String itemId) throws Exception {
        String currentUser = this.getUserDetailsForCurrentUser().getUsername();
        if (checkTodoItemInUser(itemId)) {
            User user = this.findByUsername(currentUser).get();
            user.setTodoitems(
                    user.getTodoitems()
                    .stream()
                    .filter(todoItem -> todoItem.getId().equals(itemId))
                    .collect(Collectors.toList()));

            return UserConverter.convert_UserTO(this.userRepository.save(user));
        } else {
            throw new Exception(currentUser + " doesn't have access to todoitem " + itemId);
        }
    }

    @Override
    public UserTO addNewUser(SignUpRequest signUpRequest) throws Exception {

        if(!this.existsByUsername(signUpRequest.getUsername())
                && !this.existsByEmail(signUpRequest.getEmail())){
            if(signUpRequest.getPassword().length() >= 8){

                Role _user = this.roleService.findByRole(USER).get();

                // Creating a new user
                User newUser = new User();
                newUser.setUsername(signUpRequest.getUsername());
                newUser.setPassword(this.passwordEncoder.encode(signUpRequest.getPassword()));
                newUser.setEmail(signUpRequest.getEmail());
                newUser.getRoles().add(_user);

                User retUser = this.insert(newUser);

                return UserConverter.convert_UserTO(retUser);
            } else {
                throw new Exception("Password should be longer than 7 characters");
            }

        } else {
            throw new Exception("Username or email already exists");
        }
    }

    @Override
    public UserTO addTodoItemToUser(TodoItemTO todoItemTO) throws Exception {
        String currentUser = this.getUserDetailsForCurrentUser().getUsername();
        User user = this.getUserByUsername(currentUser);
        user.getTodoitems().add(this.todoConverter.TO_TodoItem_converter(todoItemTO));
        return UserConverter.convert_UserTO(this.userRepository.save(user));
    }

    public AuthenticationResponse authenticateUser(AuthenticationRequest authRequest) throws Exception {
        Authentication auth;
        try {
            auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );
        } catch (Exception e){
            throw new Exception("Incorrect username or password ", e);
        }

        final String token = jwtUtil.generateJwtToken(auth);
        final Date date = jwtUtil.extractExpiration(token);
        final String username = jwtUtil.extractUsername(token);

        return new AuthenticationResponse(username, token, date);
    }

    private User getUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = this.findByUsername(username);
        if(optionalUser.isPresent()){
            return optionalUser.get();
        } else {
            throw new UsernameNotFoundException(username + " not found");
        }
    }

    private UserTO getUserTOByUsername(String username) throws UsernameNotFoundException {
        User userEnt = this.getUserByUsername(username);
        return UserConverter.convert_UserTO(userEnt);
    }

    private Boolean checkTodoItemInUser(String itemId) throws UsernameNotFoundException, NoTodoItemsException {
        List<TodoItem> todoItems = this.getTodoItemsOfUser();
        for(TodoItem item: todoItems){
            if(item.getId().equals(itemId)){
                return true;
            }
        }
        return false;
    }

    private UserDetails getUserDetailsForCurrentUser() throws UsernameNotFoundException {
        if(SecurityContextHolder.getContext().getAuthentication() == null){
            throw new UsernameNotFoundException("No logged in user");
        }
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
