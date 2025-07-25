package com.example.crud.repository;

import com.example.crud.entity.UserEntity;
import com.generic.service.repository.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends GenericRepository<UserEntity> {
}
