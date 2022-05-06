package com.example.glossaryapi.dto;

import com.example.glossaryapi.repository.entity.Document;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Builder
@Getter
public class DocumentDTO {
    public String title;
    public ArrayList<String> synonym = new ArrayList<>();
    public ContentDTO content;

    public DocumentDTO(String title, ArrayList<String> synonym, ContentDTO content) {
        this.title = title;
        this.synonym = synonym;
        this.content = content;
    }

    public DocumentDTO() {
    }

    public static DocumentDTO fromEntity(Document document) {
        if (document == null) {
            return null;
        }

        ArrayList<String> synonyms = document.getSynonyms().stream()
                .map(m -> m.getTitleStr())
                .collect(Collectors.toCollection(ArrayList::new));


        return DocumentDTO.builder()
                .title(document.getMainTitleStr())
                .synonym(synonyms)
                .content(ContentDTO.fromEntity(document.getContent()))
                .build();
    }
}
