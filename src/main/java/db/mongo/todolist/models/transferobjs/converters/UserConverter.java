package db.mongo.todolist.models.transferobjs.converters;

import db.mongo.todolist.models.enums.AuthLevel;
import db.mongo.todolist.models.entity.Role;
import db.mongo.todolist.models.entity.User;
import db.mongo.todolist.models.transferobjs.UserTO;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public static UserTO convert_UserTO(User source) {
        UserTO returnUTO = new UserTO();

        returnUTO.setUsername(source.getUsername());
        returnUTO.setEmail(source.getEmail());
        returnUTO.setId(source.getId());

        if(source.getRoles().size() > 0) returnUTO.setRoles(source
                .getRoles()
                .stream()
                .map(Role::getRole)
                .map(AuthLevel::toString)
                .toArray(String[]::new)
        );
        returnUTO.setNumberOfTodoItems(source.getTodoitems().size());
        return returnUTO;
    }
}
