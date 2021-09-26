package com.crud.tasks.domain;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Builder
@Getter
public class Mail {

    private final String mailTo;
    private List<String> toCc;
    private final String subject;
    private final String message;

}
