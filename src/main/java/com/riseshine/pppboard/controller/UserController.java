package com.riseshine.pppboard.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.riseshine.pppboard.common.exception.CustomException;
import com.riseshine.pppboard.service.UserService;
import com.riseshine.pppboard.controller.userDto.*;

/**
 * 회원가입, Controller Class
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "1-1. UserController", description = "유저 관리 API")
public class UserController {

    private final UserService userService;

    @PostMapping("/")
    @Operation(summary = "회원가입")
    public ResponseWrapper<UserCreateResDTO> saveUser(@RequestBody @Valid UserCraeteReqDTO user) throws Exception {
        ResponseWrapper<UserCreateResDTO> responseWrapper = new ResponseWrapper<>();
        responseWrapper.setData(userService.saveUser(user));
        return responseWrapper;
    }

    @PutMapping("/{no}")
    @Operation(summary = "회원정보수정")
    public ResponseWrapper<Integer> putUser(@PathVariable("no") Integer no, @RequestBody @Valid UserUpdateReqDTO user) throws Exception {
        ResponseWrapper<Integer> responseWrapper = new ResponseWrapper<>();
        responseWrapper.setData(userService.putUser(no, user));
        return responseWrapper;
    }

    @GetMapping("/{no}")
    @Operation(summary = "회원 정보 조회")
    public ResponseWrapper<UserGetResDTO> getUser(@PathVariable("no") Integer no) throws Exception {
        ResponseWrapper<UserGetResDTO> responseWrapper = new ResponseWrapper<>();
        responseWrapper.setData(userService.getUser(no));
        return responseWrapper;
    }

    @PostMapping("/login")
    public ResponseWrapper<String> login(@RequestBody @Valid UserLoginReqDTO user) throws Exception {
        ResponseWrapper<String> responseWrapper = new ResponseWrapper<>();
        responseWrapper.setData(userService.login(user));
        return responseWrapper;
    }

}
