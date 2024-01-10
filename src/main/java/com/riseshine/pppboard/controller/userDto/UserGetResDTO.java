package com.riseshine.pppboard.controller.userDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserGetResDTO {

  String id;
  String name;
  String createdAt;

}
