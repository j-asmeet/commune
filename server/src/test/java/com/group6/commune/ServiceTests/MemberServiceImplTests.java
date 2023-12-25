package com.group6.commune.ServiceTests;

import com.group6.commune.Enums.UserRoles;
import com.group6.commune.Model.Member;
import com.group6.commune.Model.MemberResponse;
import com.group6.commune.Repository.IMemberRepository;
import com.group6.commune.Service.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MemberServiceImplTests {

    @Mock
    private IMemberRepository memberRepository;

    @Autowired
    @InjectMocks
    private MemberServiceImpl memberServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void testAddMember() {
        Member member = new Member(1, 1, UserRoles.Member);

        when(memberRepository.addMember(any(Member.class))).thenReturn(true);

        boolean result = memberServiceImpl.addMember(member);

        assertTrue(result);
        verify(memberRepository, times(1)).addMember(member);
    }

    @Test
    public void testGetAllMembers() {
        int communityID = 1;
        List<MemberResponse> expectedMembers = new ArrayList<>();
        expectedMembers.add(new MemberResponse(1, 1, UserRoles.Member, "name"));
        expectedMembers.add(new MemberResponse(1, 2, UserRoles.Admin, "name"));

        when(memberRepository.getAllMembers(communityID)).thenReturn(expectedMembers);

        List<MemberResponse> result = memberServiceImpl.getAllMembers(communityID);

        assertEquals(expectedMembers, result);
        verify(memberRepository, times(1)).getAllMembers(communityID);
    }

    @Test
    public void testDeleteMember() {
        Member member = new Member(1, 1, UserRoles.Member);

        when(memberRepository.deleteMember(any(Member.class))).thenReturn(true);

        boolean result = memberServiceImpl.deleteMember(member);

        assertTrue(result);
        verify(memberRepository, times(1)).deleteMember(member);
    }

    @Test
    public void testChangeUserRole() {
        Member member = new Member(1, 1, UserRoles.Member);
        UserRoles newRole = UserRoles.Admin;

        when(memberRepository.changeUserRole(any(Member.class), any(UserRoles.class))).thenReturn(true);

        boolean result = memberServiceImpl.changeUserRole(member, newRole);

        assertTrue(result);
        verify(memberRepository, times(1)).changeUserRole(member, newRole);
    }
}
