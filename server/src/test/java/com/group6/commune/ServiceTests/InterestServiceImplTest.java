package com.group6.commune.ServiceTests;

import com.group6.commune.Model.Interest;
import com.group6.commune.Model.UserInterests;
import com.group6.commune.Repository.IInterestRepository;
import com.group6.commune.Service.InterestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class InterestServiceImplTest {
    @Mock
    private IInterestRepository interestRepository;

    @Autowired
    @InjectMocks
    private InterestServiceImpl interestService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getInterestList() {
        Interest interest1 = new Interest(1, "Art", "Art");
        Interest interest2 = new Interest(2, "Music", "Music");
        List<Interest> expectedInterests = Arrays.asList(interest1, interest2);

        when(interestRepository.getInterestList()).thenReturn(expectedInterests);

        List<Interest> result = interestService.getInterestList();

        assertEquals(expectedInterests, result);
        verify(interestRepository, times(2)).getInterestList();
    }

    @Test
    void addUserInterest() {
//        int userId = 1;
//        List<Integer> interestIds = Arrays.asList(1, 2, 3);
//        UserInterests userInterests = new UserInterests(userId, interestIds);
//
//        when(interestRepository.saveUserInterest(1,1)).thenReturn(true);
//
//        boolean result = interestService.addUserInterest(userInterests);
//
//        assertEquals(true, result);
//        verify(interestRepository, times(1)).saveUserInterest(1,1);
    }
}