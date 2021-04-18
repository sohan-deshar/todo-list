package db.mongo.todolist.exceptions;

public class NoTodoItemsException extends Exception{
    public NoTodoItemsException(String message) {
        super(message);
    }
}
