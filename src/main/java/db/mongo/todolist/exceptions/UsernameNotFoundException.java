package db.mongo.todolist.exceptions;

public class UsernameNotFoundException extends Exception{

    public UsernameNotFoundException(String s) {
        super(s);
    }
}
