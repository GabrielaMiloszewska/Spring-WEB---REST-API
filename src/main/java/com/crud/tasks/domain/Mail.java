package com.crud.tasks.domain;

import com.crud.tasks.service.SimpleEmailService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class Mail {

    private final String mailTo;
    private List<String> toCc = new ArrayList<>();
    private final String subject;
    private final String message;

}
