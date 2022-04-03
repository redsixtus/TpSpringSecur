package com.springframework.boot.springbootstarterparent.task;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TaskController.class)
public class TaskControllertest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TaskService taskService;

    @Test
    public void shouldReject() throws Exception{
        this.mockMvc
                .perform(
                        post("/api/tasks/demo")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"taskTitle\":\"Learn MockMvc\"}")
                                .with(csrf())
                ).andExpect(status().isUnauthorized());
    }
    @Test
    @WithMockUser("harry")
    public void shouldRejectisOk() throws Exception{

        when(taskService.createTask(anyString())).thenReturn(82L);

        this.mockMvc
                .perform(
                        post("/api/tasks/demo")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"taskTitle\":\"Learn MockMvc\"}")
                                .with(csrf())
                ).andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", Matchers.containsString("82")));
    }
    @Test
    @WithMockUser("toto")
    public void shouldRejectisDelete()throws  Exception{
        this.mockMvc
                .perform(delete("/api/tasks/demo/42"))
                .andExpect(status().isForbidden());
    }
    @Test
    public void shouldAllowDeleteRiewies() throws Exception{
        this.mockMvc
                .perform(
                        delete("/api/tasks/demo/42")
                                .with(
                                        SecurityMockMvcRequestPostProcessors.user("gigi").roles("ADMIN")
                                ).with(csrf())
                ).andExpect(status().isOk());
        verify(taskService).deleteTask(42L);
    }
}
