package com.example.crud.controller;

import com.example.crud.dto.UserReqDto;
import com.example.crud.dto.UserResDto;
import com.example.crud.entity.UserEntity;
import com.example.crud.service.UserService;
import com.generic.service.controller.GenericController;
import com.generic.service.dto.GenericPaginationRes;
import com.generic.service.model.GenericLoggedInUserData;
import com.generic.service.service.impl.GenericService;
import com.generic.service.util.RequestContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@Log4j2
public class UserController extends GenericController<UserReqDto, UserResDto, UserEntity> {
    private final UserService userService;

    public UserController(GenericService<UserReqDto, UserResDto, UserEntity> service, UserService userService) {
        super(service);
        this.userService = userService;
    }

    @GetMapping("/tenant-member")
    public ResponseEntity<GenericPaginationRes<UserResDto>> getPageder(@RequestParam(name = "pageNumber",defaultValue = "0") int pageNum, @RequestParam(name = "pageSize",defaultValue = "20") int pageSize, @RequestParam(name = "sortBy",defaultValue = "createdAt") String sortFieldName, @RequestParam(name = "sortOrder",defaultValue = "ASC") Sort.Direction sortDirection) {
//        RequestContext.setUserFromRequestContextHolder(GenericLoggedInUserData.builder().userId(UUID.fromString("1f0665a9-733f-6606-8a89-61f81495ddb4")).tenantId(UUID.fromString("1f0665a9-733f-6606-8a89-61f81495ddb4")).build());
        return ResponseEntity.ok(this.userService.getAllByTenantIdAndWithPageable(null, PageRequest.of(pageNum, pageSize, Sort.by(sortDirection, new String[]{sortFieldName}))));
    }


}
