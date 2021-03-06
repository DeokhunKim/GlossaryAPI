package com.example.glossaryapi.controller.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DocumentDTOControllerTest {

    MockHttpSession mockHttpSession;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void before() {
        //SessionMemberForm sessionMemberForm = new SessionMemberForm("testuser", MemberGrade.ADMIN);
        //mockHttpSession = new MockHttpSession();
        //mockHttpSession.setAttribute(SessionConst.LOGIN, sessionMemberForm);
    }

    @Test
    public void responseStatusOK() throws Exception {


    }

}