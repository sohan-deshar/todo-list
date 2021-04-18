package db.mongo.todolist.exceptions;

public class UnfinishedTodosException extends Exception{

    public UnfinishedTodosException(String s) {
        super(s);
    }
}
