package com.example.glossaryapi.repository;

import com.example.glossaryapi.repository.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlossaryContentRepository extends JpaRepository<Content, Long> {
}
