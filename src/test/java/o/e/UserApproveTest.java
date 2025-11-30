package o.e;

import com.fasterxml.jackson.databind.ObjectMapper;
import o.e.dto.UserDTO;
import o.e.service.AuthorizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfig.class, DbConfig.class})
@WebAppConfiguration
public class UserApproveTest {
    @MockitoBean
    @Autowired
    AuthorizationService authorizationService;
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testApproveSuccess() throws Exception {
        var user = new UserDTO("artiom", "nev", "1234", "artemnevmerz@gmail.com", "ADMIN");
        authorizationService.addUserToConfirmation(user);
        verify(authorizationService).addUserToConfirmation(user);
        mockMvc.perform(get("/admin/approve/user/{userId}",1L))
                .andExpect(status().isOk())
                .andExpect(content().string("User approved"));
        verify(authorizationService).approveUser(1L);
    }
    @Test
    void testApproveFailed() throws Exception {
        var user = new UserDTO("artiom", "nev", "1234", "artemnevmerz@gmail.com", "ADMIN");
        authorizationService.addUserToConfirmation(user);
        verify(authorizationService).addUserToConfirmation(user);
        mockMvc.perform(get("/admin/approve/user/{userId}",1L))
                .andExpect(status().isOk())
                .andExpect(content().string("User approved"));
        verify(authorizationService).approveUser(7L);
    }
}
