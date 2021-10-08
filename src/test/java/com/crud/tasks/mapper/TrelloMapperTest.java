package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TrelloMapperTest {

    @InjectMocks
    private TrelloMapper trelloMapper;

    @Test
    void mapToBoards() {
        //Given
        TrelloListDto toDoList = new TrelloListDto("1", "to do list", true);
        TrelloListDto doneList = new TrelloListDto("2", "done list", true);
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        trelloListDto.add(toDoList);
        trelloListDto.add(doneList);
        TrelloBoardDto trelloBoardDtoId1 = new TrelloBoardDto("1", "project x", trelloListDto);
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(trelloBoardDtoId1);

        //When
        List<TrelloBoard> mappedTrelloBoardDto = trelloMapper.mapToBoards(trelloBoardDtoList);
        System.out.println("\n mapToBoards test: " + mappedTrelloBoardDto.get(0).getName());

        //Then
        assertEquals("project x", mappedTrelloBoardDto.get(0).getName());
    }

    @Test
    void mapToBoardsDto() {
        //Given
        List<TrelloList> trelloList = new ArrayList<>();
        TrelloBoard trelloBoard = new TrelloBoard("1", "project y", trelloList);
        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        trelloBoardList.add(trelloBoard);

        //When
        List<TrelloBoardDto> mappedTrelloBoards = trelloMapper.mapToBoardsDto(trelloBoardList);
        System.out.println("\n mapToBoardsDto test: " + mappedTrelloBoards.get(0).getName());

        //Then
        assertEquals("project y", mappedTrelloBoards.get(0).getName());
    }

    @Test
    void mapToList() {
        //Given
        TrelloListDto trelloListDto1 = new TrelloListDto("1", "to do list", true);
        TrelloListDto trelloListDto2 = new TrelloListDto("2", "done list", true);
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        trelloListDto.add(trelloListDto1);
        trelloListDto.add(trelloListDto2);

        //When
        List<TrelloList> mappedList = trelloMapper.mapToList(trelloListDto);
        System.out.println("\n mapToList test: " + mappedList.size() + ", " + mappedList.get(0).getName() + ", " + mappedList.get(1).getName());

        //Then
        assertEquals(2, mappedList.size());
        assertEquals("to do list", mappedList.get(0).getName());
        assertEquals("done list", mappedList.get(1).getName());
    }

    @Test
    void mapToListDto() {
        //Given
        TrelloList trelloList1 = new TrelloList("1", "to do list", true);
        TrelloList trelloList2 = new TrelloList("2", "done list", true);
        List<TrelloList> trelloList = new ArrayList<>();
        trelloList.add(trelloList1);
        trelloList.add(trelloList2);

        //When
        List<TrelloListDto> mappedListDto = trelloMapper.mapToListDto(trelloList);
        System.out.println("\n mapToListDto test: " + mappedListDto.get(1).getName());

        //Then
        assertEquals("done list", mappedListDto.get(1).getName());
    }

    @Test
    void mapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("trello mapper test", "mapToCardDto test", "first", "2");

        //When
        TrelloCardDto mappedToCartDto = trelloMapper.mapToCardDto(trelloCard);

        //Then
        assertEquals("first", mappedToCartDto.getPos());
    }

    @Test
    void mapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("trello mapper test", "mapToCard test", "second", "2");

        //When
        TrelloCard mappedToCart = trelloMapper.mapToCard(trelloCardDto);

        //Then
        assertEquals("2", mappedToCart.getListId());
    }
}