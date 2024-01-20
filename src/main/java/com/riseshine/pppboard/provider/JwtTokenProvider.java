package com.riseshine.pppboard.provider;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

import java.util.Base64;
import java.util.Date;

import com.riseshine.pppboard.domain.User;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

  private String secretKey = "abcde12345";
  private long tokenValidTime = 30 * 60 * 1000L; // 토큰 유효시간 30분

  private final JwtService jwtService;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  /**
   * jwt 토큰 생성
   * @param id
   * @return
   */
  public String createToken(String id) {  // userPK = email
    Claims claims = Jwts.claims().setSubject(id); // JWT payload 에 저장되는 정보단위
    // claims.put("roles", roles); // 정보는 key / value 쌍으로 저장
    Date now = new Date();
    return Jwts.builder()
            .setClaims(claims) // 정보 저장
            .setIssuedAt(now) // 토큰 발행 시간 정보
            .setExpiration(new Date(now.getTime() + tokenValidTime)) // 토큰 유효시각 설정
            .signWith(SignatureAlgorithm.HS256, Base64.getDecoder().decode(secretKey)) // 암호화 알고리즘과, secret 값
            .compact();
  }

  /**
   * 인증 정보 조회
   *
   * @param token
   * @return
   * @throws Exception
   */
  public Authentication getAuthentication(String token) {
    User user = jwtService.getUserById(this.getUserId(token));
    // TODO: 권한 테이블 세팅
    return new UsernamePasswordAuthenticationToken(user, "", null);
    // return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
  }

  /**
   * 토큰에서 회원 정보 추출
   *
   * @param token
   * @return
   */
  public String getUserId(String token) {
    return Jwts.parser().setSigningKey(Base64.getDecoder().decode(secretKey)).parseClaimsJws(token).getBody().getSubject();
  }

  /**
   * 토큰 유효성, 만료일자 확인
   *
   * @param jwtToken
   * @return
   */
  public boolean validateToken(String jwtToken) {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(Base64.getDecoder().decode(secretKey)).parseClaimsJws(jwtToken);
      return !claims.getBody().getExpiration().before(new Date());
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * reqeust header에서 token 값 가져옴
   *
   * @param request
   * @return
   */
  public String resolveToken(HttpServletRequest request) {
    return request.getHeader("X-AUTH-TOKEN");
  }
}
