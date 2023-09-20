package dev.lucho.velocity_poc.repository;

import dev.lucho.velocity_poc.domain.Template;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TemplateRepository extends JpaRepository<Template, UUID> {

    boolean existsByNameIgnoreCase(String name);

}
