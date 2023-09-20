package dev.lucho.velocity_poc.model;

import jakarta.validation.constraints.Size;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TemplateDTO {

    private UUID id;

    @Size(max = 255)
    private String name;

    private String content;

}
