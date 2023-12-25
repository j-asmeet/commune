package com.group6.commune.Service;

import com.group6.commune.Enums.UserRoles;
import com.group6.commune.Model.Member;
import com.group6.commune.Model.MemberResponse;
import com.group6.commune.Repository.IMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("MemberService")
public class MemberServiceImpl implements IMemberService{

    @Autowired
    @Qualifier("MemberRepository")
    private IMemberRepository memberRepository;

    public MemberServiceImpl(){}

    @Override
    public Boolean addMember(Member member){
        return memberRepository.addMember(member);
    }

    @Override
    public List<MemberResponse> getAllMembers(int communityID){
        return memberRepository.getAllMembers(communityID);
    }
    @Override
    public Boolean deleteMember(Member member){
        return memberRepository.deleteMember(member);
    }
    @Override
    public Boolean changeUserRole(Member member, UserRoles newRole){
        return memberRepository.changeUserRole(member, newRole);
    }
}
