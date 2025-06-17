package com.denert.app.rest.service;

import com.denert.app.rest.dto.UserResponse;
import com.denert.app.rest.models.User;
import com.denert.app.rest.repo.UserRepo;
import org.testng.annotations.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

    @Test
    public void UserService_CreateUser_ReturnUserResponse(){
        User user = User.builder()
                .name("Grzegorz")
                .surname("Brzoza")
                .mail("grzechosz@gmail.com")
                .permissions(1)
                .phoneNumber(123456789)
                .build();

        UserResponse userResponse = UserResponse.builder()  .name("Grzegorz")
                .surname("Brzoza")
                .mail("grzechosz@gmail.com")
                .permissions(1)
                .phoneNumber(123456789).build();
    }
}
