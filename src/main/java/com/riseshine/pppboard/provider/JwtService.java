package com.riseshine.pppboard.provider;

import com.riseshine.pppboard.common.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.riseshine.pppboard.common.exception.CustomException;
import com.riseshine.pppboard.dao.UserRepository;
import com.riseshine.pppboard.domain.User;

@RequiredArgsConstructor
@Service
public class JwtService {
  private final UserRepository userRepository;
  public User getUserById(String id) {
    return userRepository.findFirstById(id)
            .orElseThrow(() -> new CustomException(ResultCode.INTERNAL_SERVER_ERROR)
                    //new CustomException("아이디가 존재하지 않습니다.", HttpStatus.BAD_REQUEST)
            );
  }

}
