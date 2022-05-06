package com.example.glossaryapi.controller.api;

import com.example.glossaryapi.dto.DocumentDTO;
import com.example.glossaryapi.service.DocumentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

@RestController
@RequestMapping("api/glossary")
public class DocumentController {

    final private DocumentService documentService ;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostConstruct
    public void init() {
        //documentService.insertTestData();
    }

    @GetMapping("")
    public @ResponseBody
    ArrayList<DocumentDTO> findAll(@RequestParam(required = false) String keyword, HttpServletResponse response,
                                   HttpServletRequest request) {

        Enumeration<String> headers = request.getHeaderNames();
        Iterator<String> stringIterator = headers.asIterator();
        while (stringIterator.hasNext()) {
            String next = stringIterator.next();
            String header = request.getHeader(next);
            System.out.println(next + ": " +header);
        }

        response.setStatus(HttpServletResponse.SC_OK);
        if (keyword == null || keyword.equals("")) {
            return documentService.findAll();
        } else /*if (keyword != null)*/ {
            return documentService.findByKeyword(keyword);
        }
    }

    @GetMapping("/{title}")
    public DocumentDTO getDocument(@PathVariable String title, HttpServletResponse response) {
        if (title == null || title.equals("")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }

        DocumentDTO docByTitle = documentService.getDocByTitle(title);
        if (docByTitle == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        return docByTitle;
    }

    @PostMapping("/{title}")
    public boolean createDocument(@PathVariable String title, @RequestBody DocumentDTO document, HttpServletResponse response ) {
        if (title == null || title.equals("")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }
        if (document == null || document.getTitle() == null
                || document.getTitle().equals("") || false == document.getTitle().equals(title)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }

        boolean result = documentService.createDoc(document);
        if (false == result) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return false;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        return true;
    }

    @PutMapping("/{title}")
    public boolean updateDocument(@PathVariable String title, @RequestBody DocumentDTO document, HttpServletResponse response ) {
        if (title == null || title.equals("")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }
        if (document == null || document.getTitle() == null
                || document.getTitle().equals("") || false == document.getTitle().equals(title)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }

        boolean result = documentService.updateDoc(title, document);
        if (false == result) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return false;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        return true;
    }


    @PatchMapping("/{title}")
    public boolean changeTitle(@PathVariable String title, @RequestBody String afterTitle, HttpServletResponse response ) {
        if (title == null || title.equals("") || afterTitle == null || afterTitle.equals("") ) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }

        boolean result = documentService.changeDocName(title, afterTitle);
        if (false == result) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return false;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        return true;
    }

    @DeleteMapping("/{title}")
    public boolean deleteDocument(@PathVariable String title, HttpServletResponse response ) {
        if (title == null || title.equals("") ) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }

        boolean result = documentService.delByTitle(title);
        if (false == result) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return false;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        return true;
    }
}
