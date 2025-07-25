package com.example.crud.service;

import com.example.crud.dto.UserReqDto;
import com.example.crud.dto.UserResDto;
import com.example.crud.entity.UserEntity;
import com.example.crud.repository.UserRepository;
import com.generic.service.mapper.GenericMapper;
import com.generic.service.model.GenericLoggedInUserData;
import com.generic.service.repository.GenericRepository;
import com.generic.service.service.impl.GenericService;
import com.generic.service.util.RequestContext;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService extends GenericService<UserReqDto, UserResDto, UserEntity> {
    private final UserRepository userRepository;

    public UserService(GenericRepository<UserEntity> repository,
                       UserRepository userRepository) {
        super(repository, UserResDto.class, UserEntity.class);
        this.userRepository = userRepository;
    }


    @Override
    public UserResDto create(UserReqDto createReq) {
        RequestContext.setUserFromRequestContextHolder(GenericLoggedInUserData.builder().userId(UUID.fromString("1f0665a9-733f-6606-8a89-61f81495ddb4")).tenantId(UUID.fromString("1f0665a9-733f-6606-8a89-61f81495ddb4")).build());
        UserEntity user = GenericMapper.map(createReq, UserEntity.class);
        return GenericMapper.map(userRepository.saveAndFlush(user), UserResDto.class);
    }
}
