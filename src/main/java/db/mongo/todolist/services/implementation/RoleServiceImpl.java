package db.mongo.todolist.services.implementation;

import db.mongo.todolist.models.enums.AuthLevel;
import db.mongo.todolist.models.entity.Role;
import db.mongo.todolist.repositories.RoleRepository;
import db.mongo.todolist.services.interfaces.RoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> findByRole(AuthLevel role) {
        return this.roleRepository.findByRole(role);
    }

    @Override
    public Boolean existsByRole(AuthLevel role) {
        return null;
    }
}
