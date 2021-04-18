package db.mongo.todolist.models;

import java.util.Date;

public class AuthenticationResponse {

    private final String username;
    private final Date _tokenExpirationDate;
    private final String _token;

    public AuthenticationResponse(String username, String jwt, Date expiresIn) {
        this.username = username;
        this._tokenExpirationDate = expiresIn;
        this._token = jwt;
    }

    public String get_token() {
        return _token;
    }

    public String getUsername() {
        return username;
    }

    public Date get_tokenExpirationDate() {
        return _tokenExpirationDate;
    }
}
