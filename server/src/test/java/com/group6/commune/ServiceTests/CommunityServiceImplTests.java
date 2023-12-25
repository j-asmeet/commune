package com.group6.commune.ServiceTests;

import com.group6.commune.Model.Community;
import com.group6.commune.Model.Interest;
import com.group6.commune.Repository.ICommunityRepository;
import com.group6.commune.Service.CommunityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CommunityServiceImplTests {

    @Mock
    private ICommunityRepository communityRepository;

    @Autowired
    @InjectMocks
    private CommunityServiceImpl communityServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCommunity() {
        Community community = new Community(1, 1, "Community 1", "Description", "image.png");

        when(communityRepository.createCommunity(community)).thenReturn(1);

        BindingResult Bresult = new BeanPropertyBindingResult(community, "event");

        int result = communityServiceImpl.createCommunity(community,Bresult);

        assertEquals(1, result);
        verify(communityRepository, times(1)).createCommunity(community);
    }

    @Test
    public void testGetCommunity() {
        Community expectedCommunity = new Community(1, 1, "Community 1", "Description", "image.png");

        when(communityRepository.getCommunity(1)).thenReturn(expectedCommunity);

        Community result = communityServiceImpl.getCommunity(1);

        assertEquals(expectedCommunity, result);
        verify(communityRepository, times(1)).getCommunity(1);
    }

    @Test
    public void testUpdateCommunity() {
        Community community = new Community(1, 1, "Community 1", "Description", "image.png");

        when(communityRepository.updateCommunity(community)).thenReturn(true);

        BindingResult Bresult = new BeanPropertyBindingResult(community, "event");

        boolean result = communityServiceImpl.updateCommunity(community, Bresult);

        assertEquals(true, result);
        verify(communityRepository, times(1)).updateCommunity(community);
    }

    @Test
    public void testDeleteCommunity() {
        when(communityRepository.deleteCommunity(1)).thenReturn(true);

        boolean result = communityServiceImpl.deleteCommunity(1);

        assertEquals(true, result);
        verify(communityRepository, times(1)).deleteCommunity(1);
    }

    @Test
    public void testGetAllCommunity() {
        Community community1 = new Community(1, 1, "Community 1", "Description", "image1.png");
        Community community2 = new Community(2, 1, "Community 2", "Description", "image2.png");
        List<Community> expectedCommunities = Arrays.asList(community1, community2);

        when(communityRepository.getAllCommunity()).thenReturn(expectedCommunities);

        List<Community> result = communityServiceImpl.getAllCommunity();

        assertEquals(expectedCommunities, result);
        verify(communityRepository, times(1)).getAllCommunity();
    }

    @Test
    public void testGetAllCommunityWithKeyword() {
        Community community1 = new Community(1, 1, "first community", "Description", "image1.png");
        Community community2 = new Community(2, 1, "second community", "Description", "image2.png");
        List<Community> expectedCommunities = Arrays.asList(community1);

        when(communityRepository.getAllCommunity("first")).thenReturn(expectedCommunities);

        List<Community> result = communityServiceImpl.getAllCommunity("first");

        assertEquals(expectedCommunities, result);
        verify(communityRepository, times(1)).getAllCommunity("first");
    }

    @Test
    public void testGetAllUserCommunity() {
        Community community1 = new Community(1, 1, "Community 1", "Description", "image1.png");
        Community community2 = new Community(2, 1, "Community 2", "Description", "image2.png");
        List<Community> expectedCommunities = Arrays.asList(community1, community2);

        when(communityRepository.getAllUserCommunity(1)).thenReturn(expectedCommunities);

        List<Community> result = communityServiceImpl.getAllUserCommunity(1);

        assertEquals(expectedCommunities, result);
        verify(communityRepository, times(1)).getAllUserCommunity(1);
    }

    @Test
    public void testAddCommunityInterest() {

        when(communityRepository.addCommunityInterest(1,1)).thenReturn(true);

        boolean result = communityServiceImpl.addCommunityInterest(1,1);

        assertEquals(true, result);
        verify(communityRepository, times(1)).addCommunityInterest(1,1);
    }

    @Test
    public void testGetCommunityInterests() {
        Interest interest1 = new Interest(1, "test1", "test2");
        Interest interest2 = new Interest(2, "test", "test");
        List<Interest> expectedInterests = Arrays.asList(interest1, interest2);

        when(communityRepository.getCommunityInterests(1)).thenReturn(expectedInterests);

        List<Interest> result = communityServiceImpl.getCommunityInterests(1);

        assertEquals(expectedInterests, result);
        verify(communityRepository, times(1)).getCommunityInterests(1);
    }

    @Test
    public void testDeleteCommunityInterest(){
        when(communityRepository.deleteCommunityInterest(1,1)).thenReturn(true);

        boolean result = communityServiceImpl.deleteCommunityInterest(1,1);

        assertEquals(true, result);
        verify(communityRepository, times(1)).deleteCommunityInterest(1,1);
    }
}
