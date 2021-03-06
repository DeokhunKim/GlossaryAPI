package com.example.glossaryapi.service;

import com.example.glossaryapi.dto.DocumentDTO;
import com.example.glossaryapi.library.kmp.KMP;
import com.example.glossaryapi.repository.GlossaryContentRepository;
import com.example.glossaryapi.repository.GlossaryDocumentRepository;
import com.example.glossaryapi.repository.GlossarySynonymTitleRepository;
import com.example.glossaryapi.repository.entity.Content;
import com.example.glossaryapi.repository.entity.Document;
import com.example.glossaryapi.repository.entity.SynonymTitle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class DocumentServiceImpl implements DocumentService{

    final private GlossaryDocumentRepository glossaryDocumentRepository;
    final private GlossaryContentRepository glossaryContentRepository;
    final private GlossarySynonymTitleRepository glossarySynonymTitleRepository;

    @PersistenceContext
    private EntityManager em;

    public DocumentServiceImpl(GlossaryDocumentRepository glossaryDocumentRepository, GlossaryContentRepository glossaryContentRepository, GlossarySynonymTitleRepository glossarySynonymTitleRepository) {
        this.glossaryDocumentRepository = glossaryDocumentRepository;
        this.glossaryContentRepository = glossaryContentRepository;
        this.glossarySynonymTitleRepository = glossarySynonymTitleRepository;
    }

    @Override
    public ArrayList<DocumentDTO> findAll() {
        ArrayList<DocumentDTO> result = new ArrayList<>();
        ArrayList<Document> findDocuments = glossaryDocumentRepository.findLastDocumentsOrderBYTitle();
        for (Document findDocument : findDocuments) {
            result.add(DocumentDTO.fromEntity(findDocument));
        }
        return result;
    }

    @Override
    public ArrayList<DocumentDTO> findByKeyword(String keyword) {
        ArrayList<DocumentDTO> result = new ArrayList<>();
        ArrayList<Document> findDocuments = glossaryDocumentRepository.findLastDocuments();
        for (Document findDocument : findDocuments) {
            // ????????? ??????
            boolean findSynonym = false;
            List<SynonymTitle> synonyms = findDocument.getSynonyms();
            for (SynonymTitle synonym : synonyms) {
                if (synonym.getTitleStr().equalsIgnoreCase(keyword)) {
                    findSynonym = true;
                    break;
                }
            }

            // ???????????? ????????? ????????? ???????????????
            if (findSynonym
                    || KMP.isContain(findDocument.getContent().getContentHtml().toLowerCase(Locale.ROOT),
                    keyword.toLowerCase(Locale.ROOT))) {
                result.add(DocumentDTO.fromEntity(findDocument));
            }
        }
        return result;
    }

    @Override
    public DocumentDTO getDocByTitle(String title) {
        Document document = glossaryDocumentRepository.findDocumentByMainTitleStrAndIsUse(title, true);
        return DocumentDTO.fromEntity(document);
    }

    @Override
    public boolean createDoc(DocumentDTO doc) {
        Document document = null;
        Boolean isUse = glossaryDocumentRepository.findUseByMainTitleStr(doc.getTitle());
        // ?????? ?????????
        if (isUse == null) {
            document = new Document(doc.getTitle());
        }
        // ?????? ?????? ?????? ?????????
        else if (true == isUse) {
            return false;
        }
        // ????????? ?????????
        else if (false == isUse) {
            document = glossaryDocumentRepository.findDocumentByMainTitleStrAndIsUse(doc.getTitle(), false);
            document.setUse(true);
            glossaryDocumentRepository.save(document);
        }
        else {
            /// impossible..
        }


        // ?????? ??????
        Content content = new Content(doc.getContent().getDate(), doc.getContent().getUser(), doc.getContent().getContentHtml() );
        ArrayList<String> synonymStrList = doc.getSynonym();
        ArrayList<SynonymTitle> synonyms = getSynonymTitles(doc.getTitle(), document, synonymStrList);

        // ???????????? ??????
        document.setContent(content);
        document.setSynonyms(synonyms);
        content.setDocument(document);


        // ??????
        em.persist(document);
        em.persist(content);
        for (SynonymTitle synonym : synonyms) {
            em.persist(synonym);
        }
        //em.flush();

        return true;
    }

    @Override
    public boolean updateDoc(String title, DocumentDTO doc) {
        Document document = glossaryDocumentRepository.findDocumentByMainTitleStrAndIsUse(title, true);
        if (document == null) {
            return false;
        }
        Content content = new Content(doc.getContent().getDate(), doc.getContent().getUser(), doc.getContent().getContentHtml());
        ArrayList<String> synonyms = doc.getSynonym();

        ArrayList<SynonymTitle> synonymTitleArrayList = getSynonymTitles(title, document, synonyms);

        for (SynonymTitle synonym : document.getSynonyms()) {
            em.remove(synonym);
        }

        content.setDocument(document);
        document.setContent(content);
        document.setSynonyms(synonymTitleArrayList);

        em.persist(content);
        em.persist(document);
        for (SynonymTitle synonymTitle : synonymTitleArrayList) {
            em.persist(synonymTitle);
        }

        return true;
    }

    private ArrayList<SynonymTitle> getSynonymTitles(String title, Document document, ArrayList<String> synonyms) {
        ArrayList<SynonymTitle> synonymTitleArrayList = new ArrayList<>();
        for (String synonym : synonyms) {
            if (synonym.equals("")) {
                continue;
            }
            synonym = synonym.trim();
            SynonymTitle synonymTitle = new SynonymTitle(synonym, document);
            synonymTitleArrayList.add(synonymTitle);
        }

        if (false == synonyms.contains(title)) {
            SynonymTitle synonymTitle = new SynonymTitle(title, document);
            synonymTitleArrayList.add(synonymTitle);
        }
        return synonymTitleArrayList;
    }

    @Override
    public boolean changeDocName(String beforeTitle, String afterTitle) {
        Document document = glossaryDocumentRepository.findDocumentByMainTitleStrAndIsUse(beforeTitle, true);
        if (document == null) {
            return false;
        }
        document.setMainTitleStr(afterTitle);
        em.persist(document);

        return true;
    }

    @Override
    public boolean delByTitle(String title) {
        Document document = glossaryDocumentRepository.findDocumentByMainTitleStrAndIsUse(title, true);
        if (document == null) {
            return false;
        }
        document.setUse(false);
        em.persist(document);

        return true;
    }

    @Override
    public boolean isContainDocByTitle(String title) {
        // ??????????????? ????????? ?????? ??????
        return false;
    }

    @Override
    public void clearDocument() {
        glossarySynonymTitleRepository.deleteAll();
        glossaryContentRepository.deleteAll();
        glossaryDocumentRepository.deleteAll();
        glossarySynonymTitleRepository.flush();
        glossaryDocumentRepository.flush();
        glossaryContentRepository.flush();
    }

    public void insertTestData() {
        Content content = new Content(2022, "user",
                "<p>C????????? ????????? ???????????? ?????? ?????? ??? ????????? ?????????????????? ?????? ?????? ??????????????? ???????????? ??????????????? ????????????. C????????? ????????? ????????? ????????? ????????? ??? ??????. 1979?????? C???????????? ??????????????? ????????? C with Classes?????? ????????? ????????? ??????????????????, 1983?????? ????????? ????????? ?????? ?????????.</p>" );
        Document document = new Document("c++");
        SynonymTitle title = new SynonymTitle("c++", document);
        SynonymTitle title2 = new SynonymTitle("c??????", document);
        ArrayList<SynonymTitle> synonyms = new ArrayList<>(Arrays.asList(title, title2));
        document.setContent(content);
        document.setSynonyms(synonyms);
        content.setDocument(document);

        createDoc(DocumentDTO.fromEntity(document));
    }
}
