package com.group6.commune.Mapper;

import com.group6.commune.Enums.UserRoles;
import com.group6.commune.Model.Member;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberRowMapper implements RowMapper<Member> {
    @Override
    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
        Member member = new Member();

        member.setUser_id(rs.getInt("user_id"));
        member.setCommunity_id(rs.getInt("community_id"));
        member.setUser_role(rs.getString("user_role").equalsIgnoreCase(UserRoles.Member.toString())?UserRoles.Member:UserRoles.Admin);
        return member;
    }
}
