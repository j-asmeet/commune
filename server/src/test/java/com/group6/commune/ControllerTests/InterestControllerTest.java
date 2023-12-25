package com.group6.commune.ControllerTests;

import com.group6.commune.Controller.InterestController;
import com.group6.commune.Model.Interest;
import com.group6.commune.Model.UserInterests;
import com.group6.commune.Service.InterestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import static org.mockito.Mockito.*;

@WebMvcTest(InterestController.class)
@AutoConfigureMockMvc(addFilters = false)
class InterestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private InterestServiceImpl interestService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetInterestList() throws Exception {
        Interest interest1 = new Interest(1, "Music", "Music");
        Interest interest2 = new Interest(2, "Art", "Art");
        List<Interest> expectedInterestList = Arrays.asList(interest1, interest2);

        when(interestService.getInterestList()).thenReturn(expectedInterestList);

        mockMvc.perform(MockMvcRequestBuilders.get("/interest")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].interestId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].interestName").value("Music"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].interestCategory").value("Music"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].interestId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].interestName").value("Art"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].interestCategory").value("Art"));

        verify(interestService, times(1)).getInterestList();
    }

    @Test
    public void testAddUserInterests() throws Exception {
        int userId = 1;
        List<Integer> interestIds = Arrays.asList(1, 2, 3);
        UserInterests userInterests = new UserInterests(userId, interestIds);

        when(interestService.addUserInterest(userInterests)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/interest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": 1, \"interestIds\": [1, 2, 3]}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("User interests added successfully."));
    }

}