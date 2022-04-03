package com.springframework.boot.springbootstarterparent.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("api/tasks/demo")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @PostMapping
    public ResponseEntity<Void> update(@RequestBody JsonNode playlaod, UriComponentsBuilder uriComponentsBuilder){
       Long taskId = this.taskService.createTask(playlaod.get("taskTitle").asText());
        return ResponseEntity.created(uriComponentsBuilder.path("/api/tasks/demo/{taskId}").build(taskId)).build();
    }
    @DeleteMapping("/{taskid}")
    @RolesAllowed("ADMIN")
    public void deletTask(@PathVariable Long taskid){
        this.taskService.deleteTask(taskid);
    }
}
