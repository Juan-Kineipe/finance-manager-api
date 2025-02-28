package com.kineipe.financemanager.unittests.mockito.service;

import com.kineipe.financemanager.domain.User;
import com.kineipe.financemanager.repository.UserRepository;
import com.kineipe.financemanager.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "user", "password");
    }

    @Test
    void findById() {
        when(repository.findById(user.getId())).thenReturn(Optional.of(user));

        User result = service.findById(1L);

        assertNotNull(result);
        assertEquals("user", result.getUsername());
        assertEquals("password", result.getPassword());
        verify(repository, times(1)).findById(user.getId());
    }

    @Test
    void findAll() {
        User user2 = new User(2L, "user2", "password2");
        User user3 = new User(3L, "user3", "password3");
        List<User> list = new ArrayList<User>();
        list.add(user);
        list.add(user2);
        list.add(user3);

        when(repository.findAll()).thenReturn(list);

        List<User> result = service.findAll();

        assertNotNull(result);
        assertEquals(3, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void update() {
        User updatedUser = new User(1L, "updatedUser", "updatedPassword");

        when(repository.findById(user.getId())).thenReturn(Optional.of(user));
        when(repository.save(updatedUser)).thenReturn(updatedUser);

        User result = service.update(updatedUser);

        assertNotNull(result);
        assertEquals("updatedUser", result.getUsername());
        assertEquals("updatedPassword", result.getPassword());
        verify(repository, times(1)).save(updatedUser);
    }

    @Test
    void delete() {
        when(repository.findById(user.getId())).thenReturn(Optional.of(user));
        doNothing().when(repository).delete(user);

        service.delete(user.getId());

        verify(repository, times(1)).findById(user.getId());
        verify(repository, times(1)).delete(user);
    }
}