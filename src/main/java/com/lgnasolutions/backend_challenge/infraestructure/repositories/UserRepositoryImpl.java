package com.lgnasolutions.backend_challenge.infraestructure.repositories;

import com.lgnasolutions.backend_challenge.domain.entities.Role;
import com.lgnasolutions.backend_challenge.domain.entities.User;
import com.lgnasolutions.backend_challenge.domain.ports.UserRepository;
import com.lgnasolutions.backend_challenge.infraestructure.datasource.RoleEntity;
import com.lgnasolutions.backend_challenge.infraestructure.datasource.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;
    private final RoleJpaRepository roleJpaRepository;
    @Autowired
    public UserRepositoryImpl(UserJpaRepository userJpaRepository,RoleJpaRepository roleJpaRepository) {
        this.userJpaRepository = userJpaRepository;
        this.roleJpaRepository = roleJpaRepository;
    }
    private RoleEntity toRoleEntity(Role role) {
        return roleJpaRepository.findByName(role.getName())
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + role.getName()));
    }

    private Role toDomainRole(RoleEntity roleEntity) {
        return new Role(
                roleEntity.getId(),
                roleEntity.getName(),
                roleEntity.getDescription(),
                roleEntity.getPermissions()
        );
    }
    @Override
    public User registerUser(User user) {
        UserEntity entity = new UserEntity(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getFullName(),
                toRoleEntity(user.getRole())

        );
        UserEntity saved = userJpaRepository.save(entity);
        return new User(
                saved.getId(),
                saved.getEmail(),
                saved.getPassword(),
                saved.getFullName(),
                toDomainRole(saved.getRole())
        );
    }

    @Override
    public Optional<User> findUserByEmail(String email) {

        return userJpaRepository.findByEmail(email)
                .map(entity -> new User(
                        entity.getId(),
                        entity.getEmail(),
                        entity.getPassword(),
                        entity.getFullName(),
                        toDomainRole(entity.getRole())
                ));
    }
}