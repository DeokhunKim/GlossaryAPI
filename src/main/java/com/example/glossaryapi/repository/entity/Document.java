package com.example.glossaryapi.repository.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Document {
    @Id @GeneratedValue @Column(name = "document_id")
    private Long id;
    @Column(unique = true)
    private String mainTitleStr;
    @OneToOne
    @JoinColumn(name = "content_id")
    private Content content;
    @OneToMany
    @JoinColumn(name = "synonymtitle_documentId")
    private List<SynonymTitle> synonyms;

    private boolean isUse = true;

    public Document(String mainTitleStr) {
        this.mainTitleStr = mainTitleStr;
    }
}
