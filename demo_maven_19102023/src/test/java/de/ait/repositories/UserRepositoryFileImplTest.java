package de.ait.repositories;

import de.ait.model.User;
import de.ait.services.UserService;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryFileImplTest {
    public static final String TEST_FILE = "users_test.txt";

    UserRepository repository;

    @BeforeEach
    void setUp() {
        repository = new UserRepositoryFileImpl(TEST_FILE);
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
        try {
            boolean newFile = file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Create file error");
        }
    }

    @AfterEach
    void tearDown() {
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void save() throws FileNotFoundException {
        //подготовка исходных данных
        //подготовка ожидаемого результата
        //вызываем тестируемый метод
        //сравниваем ожидание с тем что получилось

        User user = new User("qwer","qwert@qwert.com");
        String exp = "1;qwer;qwert@qwert.com";
        repository.save(user);

        try(BufferedReader bf = new BufferedReader(new FileReader(TEST_FILE))){
            String result = bf.readLine();
            Assertions.assertEquals(exp,result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void findByID() {
        User user1 = new User("qwer","qwert@qwert.com");
        repository.save(user1);
        User result = repository.findByID(1L);
        Assertions.assertEquals("qwer",result.getName());
    }

    @Test
    void findAll() {
        User user1 = new User("qwer","qwert@qwert.com");
        User user2 = new User("qwer2","qwert2@qwert.com");

        repository.save(user1);
        repository.save(user2);

        List<User> users = repository.findAll();

        Assertions.assertEquals(2,users.size());


        User result = repository.findByID(1L);
        Assertions.assertEquals("qwer",result.getName());
    }

    @Test
    @DisplayName("test findAll empty list")
    void testFindAll() {
        List<User> users = repository.findAll();
        Assertions.assertEquals(0,users.size());
        Assertions.assertTrue(users.isEmpty());
    }

    @Test
    void deleteById() {
    }

    @Test
    void update() {
    }

    @Test
    void findByEmail() {
        User expected = new User("qwer","qwert@qwert.com");
        repository.save(expected);
        User result = repository.findByEmail("qwert@qwert.com");
        //переопределил в классе Юзер метод equals
        Assertions.assertEquals(expected,result);
    }


}