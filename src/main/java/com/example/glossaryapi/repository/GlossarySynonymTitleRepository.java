package com.example.glossaryapi.repository;

import com.example.glossaryapi.repository.entity.SynonymTitle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlossarySynonymTitleRepository extends JpaRepository<SynonymTitle, String> {
}
