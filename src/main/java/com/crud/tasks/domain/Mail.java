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

    public static class MailBuilder {
        private String mailTo;
        private List<String> toCc = new ArrayList<>();
        private String subject;
        private String message;

        public MailBuilder setMailTo(String mailTo) {
            this.mailTo = mailTo;
            return this;
        }

        public MailBuilder setToCc(String cc) {
            toCc.add(cc);
            return this;
        }

        public MailBuilder setSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public MailBuilder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Mail build() {
            return new Mail(mailTo, toCc, subject, message);
        }
    }

    private Mail (final String mailTo, List<String> toCc, String subject, String message) {
        this.mailTo = mailTo;
        this.toCc = new ArrayList<>(toCc);
        this.subject = subject;
        this.message = message;
    }
}
