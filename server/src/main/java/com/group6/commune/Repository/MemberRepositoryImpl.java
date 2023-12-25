package com.group6.commune.Repository;

import com.group6.commune.Enums.UserRoles;
import com.group6.commune.Mapper.MemberResponseRowMapper;
import com.group6.commune.Mapper.MemberRowMapper;
import com.group6.commune.Model.Member;
import com.group6.commune.Model.MemberResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("MemberRepository")
public class MemberRepositoryImpl implements IMemberRepository{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Boolean addMember(Member member) {
        String query = "INSERT INTO members (community_id, user_id, user_role) VALUES(?,?,?)";

        // Executing query
        int res = jdbcTemplate.update(query, new Object[]{member.getCommunity_id(), member.getUser_id(), member.getUser_role().toString()});

        if(res == 1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<MemberResponse> getAllMembers(int communityID) {
        String query = "SELECT members.community_id as community_id, members.user_id as user_id, members.user_role as user_role, users.first_name as user_name FROM members,users " +
                "WHERE members.user_id = users.user_id AND members.community_id = " + communityID;
        return jdbcTemplate.query(query, new MemberResponseRowMapper());
    }

    @Override
    public Boolean deleteMember(Member member) {
        String query = "DELETE FROM members WHERE community_id = ? AND user_id = ?";

        int res = jdbcTemplate.update(query, new Object[]{member.getCommunity_id(), member.getUser_id()});

        if(res == 1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Boolean changeUserRole(Member member, UserRoles newRole) {
        String query = "UPDATE members SET user_role = ? WHERE community_id = ? AND user_id = ?";
        int res = jdbcTemplate.update(query, new Object[]{newRole.toString(), member.getCommunity_id(), member.getUser_id()});

        if(res == 1){
            return true;
        }else{
            return false;
        }
    }
}
