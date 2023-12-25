package com.group6.commune.Mapper;

import com.group6.commune.Enums.UserRoles;
import com.group6.commune.Model.Member;
import com.group6.commune.Model.MemberResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberResponseRowMapper implements RowMapper<MemberResponse> {
    @Override
    public MemberResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        MemberResponse memberResponse = new MemberResponse();

        memberResponse.setUser_id(rs.getInt("user_id"));
        memberResponse.setCommunity_id(rs.getInt("community_id"));
        memberResponse.setUser_role(rs.getString("user_role").equalsIgnoreCase(UserRoles.Member.toString())?UserRoles.Member:UserRoles.Admin);
        memberResponse.setUser_name(rs.getString("user_name"));
        return memberResponse;
    }
}
