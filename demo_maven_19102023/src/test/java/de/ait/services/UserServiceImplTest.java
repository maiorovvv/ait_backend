package de.ait.services;

import de.ait.model.User;
import de.ait.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    public String EXIST_USER_EMAIL= "jack3@mail.com";
    public String NOT_EXIST_USER_EMAIL= "jack6@mail.com";
    UserRepository repository;
    UserService service;
    @BeforeEach
    void setUp() {
        repository= Mockito.mock(UserRepository.class);
        service= new UserServiceImpl(repository);

        Mockito.when(repository.findAll()).thenReturn(List.of(
                        new User(1L,"jack1","jack1@mail.com"),
                        new User(2L,"jack2","jack2@mail.com"),
                        new User(3L,"jack3","jack3@mail.com"),
                        new User(4L,"jack4","jack4@mail.com")
                )
        );

        Mockito.when(repository.findByEmail(EXIST_USER_EMAIL)).thenReturn(new User(3L,"jack3","jack3@mail.com"));
        Mockito.when(repository.findByEmail(NOT_EXIST_USER_EMAIL)).thenReturn(null);
        Mockito.when(repository.findByID(1L)).thenReturn(new User(1L,"jack1","jack1@mail.com"));


    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllUsers() {
        List<User> expected = List.of(
                new User(1L, "jack1", "jack1@mail.com"),
                new User(2L, "jack2", "jack2@mail.com"),
                new User(3L, "jack3", "jack3@mail.com"),
                new User(4L, "jack4", "jack4@mail.com")
        );

        List<User> actual = service.getAllUsers();
        Assertions.assertEquals(expected,actual);
    }

    @Nested
    class TestCreateUser {
        @Test
        void create_user_with_not_existing_email(){
            String name = "jack16";
            String email = NOT_EXIST_USER_EMAIL;
            service.createUser(name, email);
            Mockito.verify(repository, Mockito.times(1)).save(new User(name, email));
        }
        @Test
        void create_user_with_existing_email(){
            String name = "jack3";
            String email = EXIST_USER_EMAIL;

            Assertions.assertAll(
                    ()-> Assertions.assertThrows(RuntimeException.class, ()-> service.createUser(name, email)), // wait Exception
                    ()-> Mockito.verify(repository, Mockito.never()).save(Mockito.any())
            );
            //Mockito.verify(repository, Mockito.times(1)).save(new User(name, email));
        }

        @Test
        void create_user_with_empty_name(){
            String name = "";
            String email = EXIST_USER_EMAIL;

            Assertions.assertAll(
                    ()-> Assertions.assertThrows(RuntimeException.class, ()-> service.createUser(name, email)), // wait Exception
                    ()-> Mockito.verify(repository, Mockito.never()).save(Mockito.any())
            );
        }

        @Test
        void create_user_with_empty_email(){
            String name = "jack3";
            String email = null;

            Assertions.assertAll(
                    ()-> Assertions.assertThrows(RuntimeException.class, ()-> service.createUser(name, email)), // wait Exception
                    ()-> Mockito.verify(repository, Mockito.never()).save(Mockito.any())
            );
        }
    }

    @Nested
    class TestUpdateUser {
        @Test
        void update_user_with_existing_email() {
//            String name = "jack3";
//            String email = EXIST_USER_EMAIL;
//            service.updateUser(3L, name, email);
//            Mockito.verify(repository, Mockito.times(1)).update(new User(name, email));
        }


    }
}