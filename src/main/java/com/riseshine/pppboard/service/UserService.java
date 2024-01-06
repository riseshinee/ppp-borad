package com.riseshine.pppboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.riseshine.pppboard.common.exception.CustomException;
import com.riseshine.pppboard.common.utils.CommonUtil;

import com.riseshine.pppboard.controller.userDto.*;
import com.riseshine.pppboard.domain.User;
import com.riseshine.pppboard.dao.UserRepository;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public UserCreateResDTO saveUser(UserCraeteReqDTO createUserDto) throws Exception {
    // 아이디 중복체크
    boolean checkIdResult = checkSameIdExists(createUserDto.getId());
    if(checkIdResult) {
      throw new CustomException("중복된 아이디가 존재합니다.", HttpStatus.BAD_REQUEST);
    }
    User user = createPendingUser(createUserDto);
    userRepository.save(user);

    return UserCreateResDTO.builder()
            .id(user.getId())
            .name(user.getName())
            .build();

    }

    /**
     * 아이디 중복 체크
     * @param id
     * @return
     */
    public boolean checkSameIdExists(String id) {
      User user = userRepository.findFirstById(id);
      //중복된 아이디가 없으면 false, 존재하면 true;
      return user != null;
    }

    /**
     * user 객체 생성
     * @param createUserDto
     * @return
     */
    private User createPendingUser(UserCraeteReqDTO createUserDto) {
      return User.builder()
              .id(createUserDto.getId())
              .name(createUserDto.getName())
              .passsword(CommonUtil.dbEncrypt(createUserDto.getPassword()))
              .build();
    }

}
