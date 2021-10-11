package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TaskMapper taskMapper;
    @MockBean
    private DbService service;

    @Test
    void shouldGetTasks() throws Exception {
        //Given
        List<TaskDto> tasks = new ArrayList<>();
        tasks.add(new TaskDto(1L, "task no 1", "Trello controller test"));
        tasks.add(new TaskDto(2L, "task no 2", "Task controller test"));

        when(taskMapper.mapToTaskDtoList(service.getAllTasks())).thenReturn(tasks);
        //or
        //List<Task> taskList = service.getAllTasks();
        //when(taskMapper.mapToTaskDtoList(taskList)).thenReturn(tasks);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(tasks);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/task/getTasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("task no 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content", Matchers.is("Trello controller test")));
    }

    @Test
    void shouldGetTask() throws Exception {
        //Given
        Task task = new Task(1L, "task no 1", "Task controller test");
        TaskDto taskDto = new TaskDto(1L, "taskDto no 1", "TaskDto controller test");

        when(service.getTask(task.getId())).thenReturn(Optional.of(task));
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/task/getTask?taskId=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("taskDto no 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("TaskDto controller test")));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        //Given
        Task task = new Task(1L, "task no 1", "Task controller test");
        Mockito.doNothing().when(service).deleteTask(task.getId());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/task/deleteTask?taskId=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        Mockito.verify(service, times(1)).deleteTask(task.getId());
    }

    @Test
    void shouldUpdateTask() throws Exception {
        //Given
        Task task = new Task(2L, "task no 2", "Task controller test");
        TaskDto taskDto = new TaskDto(2L, "updated task no 2", "updated task controller test");

        when(taskMapper.mapToTaskDto(service.saveTask(task))).thenReturn(taskDto);
        //or:
        //Task savedTask = service.saveTask(task);
        //when(taskMapper.mapToTaskDto(savedTask)).thenReturn(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/task/updateTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("updated task no 2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("updated task controller test")));
    }

    @Test
    void shouldCreateTask() throws Exception {
        //Given
        Task task = new Task(2L, "task no 2", "Task controller test");
        TaskDto taskDto = new TaskDto(2L, "taskDto no 2", "TaskDto controller test");

        when(service.saveTask(taskMapper.mapToTask(taskDto))).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/task/createTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(status().is(200));
        Mockito.verify(service, times(1)).saveTask(taskMapper.mapToTask(taskDto));
    }

}