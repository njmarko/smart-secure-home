package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter @Setter
public class AlarmTemplate {
    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    private String when;
    private String then;
    private String message;
    private Boolean active;

    public AlarmTemplate() {
        this.active = true;
    }

    public AlarmTemplate(String name, String when, String then, String message) {
        this();
        this.name = name;
        this.when = when;
        this.then = then;
        this.message = message;
    }
}
