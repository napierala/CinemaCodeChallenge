package pl.napierala.cinemacodechallenge.builder;

import pl.napierala.cinemacodechallenge.entity.UserEntity;
import pl.napierala.cinemacodechallenge.extmodel.UserRegisterResponse;

public class UserRegisterResponseBuilder {

    public static UserRegisterResponse buildWith(UserEntity user) {

        return UserRegisterResponse.builder()
                .userName(user.getUserName())
                .build();
    }
}