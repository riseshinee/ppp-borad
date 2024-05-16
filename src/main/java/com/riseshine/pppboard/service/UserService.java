package com.riseshine.pppboard.service;

import com.riseshine.pppboard.common.ResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;

import com.riseshine.pppboard.common.exception.CustomException;
import com.riseshine.pppboard.common.utils.CommonUtil;

import com.riseshine.pppboard.provider.JwtTokenProvider;
import com.riseshine.pppboard.controller.userDto.*;
import com.riseshine.pppboard.domain.User;
import com.riseshine.pppboard.dao.UserRepository;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserService {

  private final JwtTokenProvider jwtTokenProvider;
  private final UserRepository userRepository;

  /**
   * 회원 정보 생성 (회원가입)
   * @param createUserDto
   * @return
   * @throws Exception
   */
  public UserCreateResDTO saveUser(UserCraeteReqDTO createUserDto) {
    // 아이디 중복체크
    checkSameIdExists(createUserDto.getId());

    //유저 객체 생성, 저장
    User user = createPendingUser(createUserDto);
    userRepository.save(user);

    return UserCreateResDTO.builder()
            .id(user.getId())
            .name(user.getName())
            .build();
    }

  /**
   * 회원 정보 업데이트
   * @param no
   * @param updateUserDto
   * @return
   * @throws Exception
   */
  public int putUser(int no, UserUpdateReqDTO updateUserDto) {
    //유저 조회
    userRepository.findFirstByNo(no).orElseThrow(() ->
            new CustomException(ResultCode.USER_NOT_EXIST));

    //유저 정보 업데이트
    userRepository.updateByNo(no,
            updateUserDto.getName(),
            CommonUtil.dbEncrypt(updateUserDto.getPassword()));

    return no;
  }

  /**
   * 회원 정보 조회
   * @param no
   * @return
   * @throws Exception
   */
  public UserGetResDTO getUser(int no) {
    User user = userRepository.findFirstByNo(no).orElseThrow(() ->
            new CustomException(ResultCode.USER_NOT_EXIST)
    );

    return UserGetResDTO.builder()
            .id(user.getId())
            .name(user.getName())
            .createdAt(user.getCreatedAt())
            .build();

  }

  public String login(UserLoginReqDTO loginUserDto) {
    //아이디, 비밀번호 일치 확인
    User user = validateUser(loginUserDto);
    //token 생성
    return jwtTokenProvider.createToken(user.getId());
  }

  /**
   * 아이디 중복 체크
   * @param id
   * @return
   */
  private void checkSameIdExists(String id) {
    userRepository.findFirstById(id).ifPresent(user -> {
      throw new CustomException(ResultCode.ID_DUPLICATION);
    });
  }

  /**
   * user 객체 생성
   * @param createUserDto
   * @return
   */
  protected User createPendingUser(UserCraeteReqDTO createUserDto) {
    return User.builder()
            .id(createUserDto.getId())
            .name(createUserDto.getName())
            .passsword(CommonUtil.dbEncrypt(createUserDto.getPassword()))
            .build();
  }

  /**
   * 로그인 시 유저 유효성 검증
   * @param loginUserDto
   * @return
   */
  protected User validateUser(UserLoginReqDTO loginUserDto) {
    User user = userRepository.findFirstById(loginUserDto.getId()).orElseThrow(() ->
                    new CustomException(ResultCode.INTERNAL_SERVER_ERROR)
            //new CustomException("아이디가 존재하지 않습니다.", HttpStatus.BAD_REQUEST)
    );

    String getPassword = CommonUtil.dbDecrypt(user.getPasssword());
    if(!Objects.equals(getPassword, loginUserDto.getPassword())){
      throw new CustomException(ResultCode.INTERNAL_SERVER_ERROR);
      //throw new CustomException("비밀번호가 일치하지 않습니다..", HttpStatus.BAD_REQUEST);
    }

    return user;
  }

}
