package dev.lucho.velocity_poc.service;

import dev.lucho.velocity_poc.domain.Template;
import dev.lucho.velocity_poc.model.TemplateDTO;
import dev.lucho.velocity_poc.repository.TemplateRepository;
import dev.lucho.velocity_poc.util.NotFoundException;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TemplateService {

    private final TemplateRepository templateRepository;

    public TemplateService(final TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    public List<TemplateDTO> findAll() {
        final List<Template> templates = templateRepository.findAll(Sort.by("id"));
        return templates.stream().map(template -> mapToDTO(template, new TemplateDTO())).toList();
    }

    public TemplateDTO get(final UUID id) {
        return templateRepository.findById(id)
                .map(template -> mapToDTO(template, new TemplateDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final TemplateDTO templateDTO) {
        final Template template = new Template();
        mapToEntity(templateDTO, template);
        return templateRepository.save(template).getId();
    }

    public void update(final UUID id, final TemplateDTO templateDTO) {
        final Template template = templateRepository.findById(id).orElseThrow(NotFoundException::new);
        mapToEntity(templateDTO, template);
        templateRepository.save(template);
    }

    public void delete(final UUID id) {
        templateRepository.deleteById(id);
    }

    private TemplateDTO mapToDTO(final Template template, final TemplateDTO templateDTO) {
        templateDTO.setId(template.getId());
        templateDTO.setName(template.getName());
        templateDTO.setContent(template.getContent());
        return templateDTO;
    }

    private Template mapToEntity(final TemplateDTO templateDTO, final Template template) {
        template.setName(templateDTO.getName());
        template.setContent(templateDTO.getContent());
        return template;
    }

    public boolean nameExists(final String name) {
        return templateRepository.existsByNameIgnoreCase(name);
    }

}
