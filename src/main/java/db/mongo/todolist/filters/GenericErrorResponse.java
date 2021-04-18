package db.mongo.todolist.filters;

import java.util.Date;

public class GenericErrorResponse {
    private String error_message;
    private String date;


    public GenericErrorResponse() {
    }

    public GenericErrorResponse(String error_message){
        this.error_message = error_message;
        this.date = new Date().toString();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getError_message() {
        return this.error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
}
