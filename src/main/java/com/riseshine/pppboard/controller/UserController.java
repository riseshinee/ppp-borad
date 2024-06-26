package com.riseshine.pppboard.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import com.riseshine.pppboard.service.UserService;
import com.riseshine.pppboard.controller.userDto.*;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "1. UserController", description = "유저 관리 API")
public class UserController {

    private final UserService userService;

    @PostMapping()
    @Operation(summary = "회원가입")
    public ResponseWrapper<UserCreateResDTO> saveUser(@RequestBody @Valid UserCraeteReqDTO user) {
        ResponseWrapper<UserCreateResDTO> responseWrapper = new ResponseWrapper<>();
        responseWrapper.setData(userService.saveUser(user));
        return responseWrapper;
    }

    @PutMapping("/{no}")
    @Operation(summary = "회원정보수정")
    public ResponseWrapper<Integer> putUser(@PathVariable("no") Integer no, @RequestBody @Valid UserUpdateReqDTO user) {
        ResponseWrapper<Integer> responseWrapper = new ResponseWrapper<>();
        responseWrapper.setData(userService.putUser(no, user));
        return responseWrapper;
    }

    @GetMapping("/{no}")
    @Operation(summary = "회원 정보 조회")
    public ResponseWrapper<UserGetResDTO> getUser(@PathVariable("no") int no) {
        ResponseWrapper<UserGetResDTO> responseWrapper = new ResponseWrapper<>();
        responseWrapper.setData(userService.getUser(no));
        return responseWrapper;
    }

    @PostMapping("/login")
    @Operation(summary = "로그인")
    public ResponseWrapper<String> login(@RequestBody @Valid UserLoginReqDTO user) {
        ResponseWrapper<String> responseWrapper = new ResponseWrapper<>();
        responseWrapper.setData(userService.login(user));
        return responseWrapper;
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃")
    public void logout(HttpSession session) {
        session.invalidate();
    }
}
