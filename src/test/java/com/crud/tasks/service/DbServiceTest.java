package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
//@RunWith(SpringRunner.class)
public class DbServiceTest {

    @InjectMocks
    private DbService dbService;
    @Mock
    private TaskRepository taskRepository;

    @Test
    public void shouldGetAllTasks() {
        //Given
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1L, "task no 1", "Db service test"));
        tasks.add(new Task(2L, "task no 2", "Trello service test"));
        when(taskRepository.findAll()).thenReturn(tasks);

        //When
        List<Task> result = dbService.getAllTasks();

        //Then
        assertEquals(2, result.size());
        assertEquals("Trello service test", result.get(1).getContent());
    }

    @Test
    public void shouldGetEmptyTaskList() {
        //Given
        List<Task> tasks = new ArrayList<>();
        when(taskRepository.findAll()).thenReturn(tasks);

        //When
        List<Task> expectedEmptyTask = dbService.getAllTasks();

        //Then
        assertEquals(0, expectedEmptyTask.size());
        assertNotNull(expectedEmptyTask);
    }

    @Test
    public void shouldSaveTask() {
        //Given
        Task task = new Task(1l, "task no 1", "Db service test");
        when(taskRepository.save(task)).thenReturn(task);

        //When
        Task savedTask = dbService.saveTask(task);

        //Then
        assertEquals("Db service test", savedTask.getContent());
    }

}
