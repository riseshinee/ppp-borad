package com.riseshine.pppboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.riseshine.pppboard.controller.userDto.*;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserService {

    public UserCreateResDTO saveUser(UserCraeteReqDTO createUserDto){
        // 아이디 중복체크
        // 비밀번호 암호화
        // 유저 정보 저장
    }

    /**
     * 아이디 중복 확인
     * @param id
     * @return
     */
    public boolean checkIDduplication (String id) {
        //db에서 같은 아이디가 있는지 조회

        return true;
    }

    private String passwordEncrypt (String password) {

    }
}
