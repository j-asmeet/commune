package com.group6.commune.ControllerTests;

import com.group6.commune.Controller.CommunityController;
import com.group6.commune.Model.Community;
import com.group6.commune.Model.Interest;
import com.group6.commune.Service.CommunityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommunityController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CommunityControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommunityServiceImpl communityServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCommunity() throws Exception {
        Community community = new Community(1, 1, "Community 1", "Description", "image.png");
        BindingResult bindingResult;

        given(communityServiceImpl.createCommunity(any(Community.class), any(BindingResult.class))).willReturn(1);

        mockMvc.perform(post("/community")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"community_id\": 1, \"created_by\": 1, \"name\": \"Community 1\", \"description\": \"Description\", \"display_image\": \"image.png\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void testGetCommunity() throws Exception {
        Community community = new Community(1, 1, "Community 1", "Description", "image.png");

        when(communityServiceImpl.getCommunity(1)).thenReturn(community);

        mockMvc.perform(get("/community/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.community_id").value(1));

        verify(communityServiceImpl, times(1)).getCommunity(1);
    }

    @Test
    public void testGetAllCommunity() throws Exception {
        Community community1 = new Community(1, 1, "Community 1", "Description", "image1.png");
        Community community2 = new Community(2, 1, "Community 2", "Description", "image2.png");
        List<Community> communities = Arrays.asList(community1, community2);

        when(communityServiceImpl.getAllCommunity()).thenReturn(communities);

        mockMvc.perform(MockMvcRequestBuilders.get("/community")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].community_id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].community_id").value(2));

        verify(communityServiceImpl, times(1)).getAllCommunity();
    }

    @Test
    public void testGetAllCommunityWithKeyword() throws Exception {
        Community community1 = new Community(1, 1, "Community 1", "Description", "image1.png");
        Community community2 = new Community(2, 1, "Community 2", "Description", "image2.png");
        List<Community> communities = Arrays.asList(community1, community2);

        when(communityServiceImpl.getAllCommunity("keyword")).thenReturn(communities);

        mockMvc.perform(MockMvcRequestBuilders.get("/community?keyword=keyword")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].community_id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].community_id").value(2));

        verify(communityServiceImpl, times(1)).getAllCommunity("keyword");
    }

    @Test
    public void testUpdateCommunity() throws Exception {
        Community community = new Community(1, 1, "Community 1", "Description", "image.png");

        given(communityServiceImpl.updateCommunity(any(Community.class), any(BindingResult.class))).willReturn(true);

        mockMvc.perform(put("/community")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"community_id\": 1, \"created_by\": 1, \"name\": \"Community 1\", \"description\": \"Description\", \"display_image\": \"image.png\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }

    @Test
    public void testDeleteCommunity() throws Exception {
        when(communityServiceImpl.deleteCommunity(1)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/community/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));

        verify(communityServiceImpl, times(1)).deleteCommunity(1);
    }

    @Test
    public void testGetAllUserCommunity() throws Exception {
        Community community1 = new Community(1, 1, "Community 1", "Description", "image1.png");
        Community community2 = new Community(2, 1, "Community 2", "Description", "image2.png");
        List<Community> communities = Arrays.asList(community1, community2);

        when(communityServiceImpl.getAllUserCommunity(1)).thenReturn(communities);

        mockMvc.perform(MockMvcRequestBuilders.get("/community/user/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].community_id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].community_id").value(2));

        verify(communityServiceImpl, times(1)).getAllUserCommunity(1);
    }

    @Test
    public void testAddCommunityInterest() throws Exception{

        when(communityServiceImpl.addCommunityInterest(1,1)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/community/1/interest?interest_id=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCommunityInterests() throws Exception{
        Interest interest1 = new Interest(1, "test1", "test2");
        Interest interest2 = new Interest(2, "test", "test");
        List<Interest> expectedInterests = Arrays.asList(interest1, interest2);

        when(communityServiceImpl.getCommunityInterests(1)).thenReturn(expectedInterests);

        mockMvc.perform(MockMvcRequestBuilders.get("/community/1/interest")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].interestId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].interestId").value(2));

        verify(communityServiceImpl, times(1)).getCommunityInterests(1);
    }

    @Test
    public void testDeleteCommunityInterest() throws Exception{
        when(communityServiceImpl.deleteCommunityInterest(1,1)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/community/1/interest?interest_id=1", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));

        verify(communityServiceImpl, times(1)).deleteCommunityInterest(1,1);
    }
}
