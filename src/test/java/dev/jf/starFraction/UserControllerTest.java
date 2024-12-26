package dev.jf.starFraction;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dev.jf.starFraction.Models.User;
import dev.jf.starFraction.Models.enums.UserRole;
import dev.jf.starFraction.auth.infra.security.SecurityConfigurations;
import dev.jf.starFraction.auth.infra.security.SecurityFilter;
import dev.jf.starFraction.auth.infra.security.TokenService;
import dev.jf.starFraction.controllers.AuthenticationController;
import dev.jf.starFraction.controllers.UserController;
import dev.jf.starFraction.services.AuthorizationService;
import dev.jf.starFraction.services.UserService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AuthenticationController.class, AuthorizationService.class, TokenService.class, SecurityConfigurations.class, SecurityFilter.class})
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserController userControllerMock;

    @InjectMocks
    private UserService userServiceMock;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
          .webAppContextSetup(context)
          .apply(springSecurity())
          .build();
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void given_users_exist_when_get_all_users_return_all_users() throws Exception {
        // Given
        User user = new User("test@example.com", "password", "testuser", UserRole.USER);

        when(userServiceMock.getAllUsers()).thenReturn(List.of(user));

        /* // When
        ResponseEntity<List<UserDTO>> userListResponse = userControllerMock.getAllUsers();
        List<UserDTO> userList = userListResponse.getBody();
        
        //Then
        assertNotNull(userList, "The user list should not be null");
        assertFalse(userList.isEmpty(), "The user list should not be empty");
        assertEquals(1, userList.size(), "The user list should contain exactly one user");
        assertEquals("testuser", userList.get(0).username(), "The username of the user should be testuser");
        assertEquals(UserRole.USER, userList.get(0).role(), "The role of the user should be USER"); */

        // When & Then
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(1L))
                .andExpect(jsonPath("$[0].email").value("test@example.com"))
                .andExpect(jsonPath("$[0].username").value("testuser"))
                .andExpect(jsonPath("$[0].role").value("USER"));
    }

}
