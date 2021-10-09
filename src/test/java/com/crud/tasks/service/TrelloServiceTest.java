package com.crud.tasks.service;

import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloList;
import com.crud.tasks.domain.TrelloListDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TrelloServiceTest {

    @InjectMocks
    private TrelloService trelloService;
    @Mock
    private TrelloClient trelloClient;

    @Test
    public void shouldFetchTrelloBoards() {
        //Given
        TrelloListDto toDoList = new TrelloListDto("1", "to do list", true);
        TrelloListDto doneList = new TrelloListDto("2", "done list", true);
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        trelloListDto.add(toDoList);
        trelloListDto.add(doneList);
        TrelloBoardDto trelloBoardDtoId1 = new TrelloBoardDto("1", "project x", trelloListDto);
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(trelloBoardDtoId1);

        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDtoList);

        //When
        List<TrelloBoardDto> expectedTrelloBoardDtoList = trelloService.fetchTrelloBoards();

        //Then
        assertEquals(1, expectedTrelloBoardDtoList.size());
        assertEquals("project x", expectedTrelloBoardDtoList.get(0).getName());
    }

}
