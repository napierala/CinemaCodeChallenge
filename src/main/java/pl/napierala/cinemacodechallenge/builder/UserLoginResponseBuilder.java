package pl.napierala.cinemacodechallenge.builder;

import pl.napierala.cinemacodechallenge.entity.UserEntity;
import pl.napierala.cinemacodechallenge.extmodel.UserLoginResponse;

public class UserLoginResponseBuilder {

    public static UserLoginResponse buildWith(UserEntity userEntity) {

        return UserLoginResponse.builder()
                .createTime(userEntity.getCreateTime())
                .build();
    }
}