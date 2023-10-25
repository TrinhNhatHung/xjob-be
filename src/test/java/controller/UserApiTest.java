package controller;


import com.xjob.api.UserApi;
import com.xjob.response.UserResponse;
import com.xjob.service.ExperienceService;
import com.xjob.service.RoleService;
import com.xjob.service.SkillService;
import com.xjob.service.UserService;
import com.xjob.util.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = UserApi.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ContextConfiguration(classes = UserApi.class)
public class UserApiTest {

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UserService userService;

    @MockBean
    private RoleService roleService;

    @MockBean
    private ExperienceService experienceService;

    @MockBean
    private SkillService skillService;

    @MockBean
    private UserResponse userResponse;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testUserApi(){
        Assertions.assertTrue(false);
    }
}
