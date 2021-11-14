package pl.napierala.cinemacodechallenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.napierala.cinemacodechallenge.builder.UserEntityBuilder;
import pl.napierala.cinemacodechallenge.builder.UserLoginResponseBuilder;
import pl.napierala.cinemacodechallenge.builder.UserRegisterResponseBuilder;
import pl.napierala.cinemacodechallenge.entity.UserEntity;
import pl.napierala.cinemacodechallenge.exception.UserAlreadyExistsException;
import pl.napierala.cinemacodechallenge.exception.UserAuthorizationException;
import pl.napierala.cinemacodechallenge.extmodel.UserLoginRequest;
import pl.napierala.cinemacodechallenge.extmodel.UserLoginResponse;
import pl.napierala.cinemacodechallenge.extmodel.UserRegisterRequest;
import pl.napierala.cinemacodechallenge.extmodel.UserRegisterResponse;
import pl.napierala.cinemacodechallenge.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<UserEntity> findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public UserEntity findByUserNameOrThrowException(String userName) {
        return userRepository.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException(""));
    }

    public UserRegisterResponse register(UserRegisterRequest request) {

        if (userRepository.findByUserName(request.getUserName()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        UserEntity userEntity = UserEntityBuilder.buildRegularUserWith(request.getUserName(), encodedPassword);

        UserEntity saved = userRepository.save(userEntity);

        return UserRegisterResponseBuilder.buildWith(saved);
    }

    public UserLoginResponse login(UserLoginRequest request) {

        Optional<UserEntity> userEntityOptional = userRepository.findByUserName(request.getUserName());

        if (!userEntityOptional.isPresent()) {
            throw new UserAuthorizationException("User not authorized.");
        }

        UserEntity userEntity = userEntityOptional.get();

        if (!passwordEncoder.matches(request.getPassword(), userEntity.getPassword())) {
            throw new UserAuthorizationException("User not authorized.");
        }

        return UserLoginResponseBuilder.buildWith(userEntity);
    }
}