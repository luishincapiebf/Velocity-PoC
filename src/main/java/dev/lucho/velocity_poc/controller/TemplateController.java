package dev.lucho.velocity_poc.controller;

import java.util.List;
import java.util.UUID;

import dev.lucho.velocity_poc.model.TemplateDTO;
import dev.lucho.velocity_poc.service.TemplateService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/templates", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Template")
public class TemplateController {

    private final TemplateService templateService;

    public TemplateController(final TemplateService templateService) {
        this.templateService = templateService;
    }

    @GetMapping
    public ResponseEntity<List<TemplateDTO>> getAllTemplates() {
        return ResponseEntity.ok(templateService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TemplateDTO> getTemplate(
            @PathVariable(name = "id")
            final UUID id) {
        return ResponseEntity.ok(templateService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createTemplate(
            @RequestBody
            @Valid
            final TemplateDTO templateDTO) {
        final UUID createdId = templateService.create(templateDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UUID> updateTemplate(
            @PathVariable(name = "id")
            final UUID id,
            @RequestBody
            @Valid
            final TemplateDTO templateDTO) {
        templateService.update(id, templateDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTemplate(
            @PathVariable(name = "id")
            final UUID id) {
        templateService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
