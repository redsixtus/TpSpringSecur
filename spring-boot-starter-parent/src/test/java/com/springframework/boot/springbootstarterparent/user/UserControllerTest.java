package com.springframework.boot.springbootstarterparent.user;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    @Test
    public void onNePeutPasAvoirUnUserSiPasAutentifier() throws Exception {

        when(userService.getAllUsers())
                .thenReturn(List.of(new User("Marius", "Marius@Legion.fr")));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("Marius"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("Marius@Legion.fr"));
    }

    @Test
    public void pasDeUser() throws Exception {
        when(userService.getUserByUsername("toto"))
                .thenThrow(new UserNotFoundException("toto is not found"));

        this.mockMvc
                .perform(get("/api/users/toto"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void CreeUnUser() throws Exception {
        this.mockMvc
                .perform(
                        post("/api/users").with(
                                        SecurityMockMvcRequestPostProcessors.
                                                user("dimitri").roles("USER","USER"))
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"username\": \"gloria\", \"email\":\"gloria@delaware.pro\"}")
                                .with(csrf())
                )
                .andExpect(status().isUnauthorized());
    }


    @Test
    public void onPeutSuppQueSiTuEsAdmin() throws Exception {
        User c = new User("harry","harry@potter.fr");
        when(userService.getUserByUsername("Harry"))
                .thenReturn(c);


        this.mockMvc
                .perform(
                        delete("/api/users/harry")
                                .with(
                                        SecurityMockMvcRequestPostProcessors.
                                                user("dimitri").roles("ADMIN","SUPER_ADMIN"))
                                .with(csrf())

                )
                .andExpect(status().isOk());

        verify(userService).deleteUser(c);

    }

}
