package com.BlogSite.TestBlogProject.services;

import com.BlogSite.TestBlogProject.dto.UserDto;
import com.BlogSite.TestBlogProject.mapper.UserMapper;
import com.BlogSite.TestBlogProject.models.ErrorCode;
import com.BlogSite.TestBlogProject.models.User;
import com.BlogSite.TestBlogProject.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getUsers_ShouldReturnAllUsers() {
        userService.getUsers();
        User user = new User(1L,
                "Test",
                "Test");
        List<User> expectedList = List.of(user);

        doReturn(expectedList).when(userRepository).findAll();
        List<User> actualList = userService.getUsers();

        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    void getUser_ShouldGetUserById() {
        Long id = 1L;
        User expectedUser = new User(id,
                "Test",
                null);

        doReturn(Optional.of(expectedUser))
                .when(userRepository).findById(id);
        User actualUser = userService.getUser(id).getData();

        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    void getUser_ShouldReturnErrorCode() {
        long id = 0;
        ErrorCode expectedError = ErrorCode.USER_NOT_FOUND;

        doReturn(Optional.empty())
                .when(userRepository).findById(id);
        ErrorCode actualError = userService.getUser(id).getError();

        Assertions.assertEquals(expectedError, actualError);
    }

    @Test
    void getUserByUsername_ShouldGetUserByUsername() {
        String username = "Test";
        User expectedUser = new User(1L,
                username,
                null);

        doReturn(Optional.of(expectedUser))
                .when(userRepository).findByUsername(username);
        User actualUser = userService.getUserByUsername(username).getData();

        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    void getUserByUsername_ShouldReturnErrorCode() {
        String username = "";
        ErrorCode expectedError = ErrorCode.USER_NOT_FOUND;

        doReturn(Optional.empty())
                .when(userRepository).findByUsername(username);
        ErrorCode actualError = userService.getUserByUsername(username).getError();

        Assertions.assertEquals(expectedError, actualError);
    }

    @Test
    void addUser_ShouldSaveUser() {
        String username = "Test";
        User expectedUser = new User(1L,
                username,
                username);
        User mockUser = new User(null,
                username,
                username);
        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setEmail(username);

        doReturn(Optional.empty())
                .when(userRepository).findByUsername(username);
        doReturn(mockUser)
                .when(userMapper).userDtoToUser(userDto);
        doReturn(expectedUser)
                .when(userRepository).save(mockUser);
        User actualUser = userService.addUser(userDto).getData();

        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    void addUser_ShouldReturnErrorCode() {
        UserDto userDto = new UserDto();
        ErrorCode expectedError = ErrorCode.ALREADY_EXISTS;

        doReturn(Optional.of(new User()))
                .when(userRepository).findByUsername(userDto.getUsername());
        ErrorCode actualError = userService.addUser(userDto).getError();

        Assertions.assertEquals(expectedError, actualError);
    }
}