package com.group6.commune.Repository;

import com.group6.commune.Mapper.CommunityRowMapper;
import com.group6.commune.Mapper.InterestRowMapper;
import com.group6.commune.Model.Community;
import com.group6.commune.Model.Interest;
import com.group6.commune.Utils.IDgenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;

@Repository
@Qualifier("CommunityRepository")
public class CommunityRepositoryImpl implements ICommunityRepository{

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public int createCommunity(Community community) {
        int id = IDgenerator.generateId();
        String query = "INSERT INTO community (community_id, created_by, name, description, display_image) VALUES(?,?,?,?,?)";

        // Executing query
        int res = jdbcTemplate.update(query, new Object[]{id, community.getCreated_by(), community.getName(), community.getDescription(), community.getDisplay_image()});

        if(res == 1){
            return id;
        }else{
            return 0;
        }

    }

    @Override
    public Community getCommunity(int communityID) {
        String query = "SELECT * FROM community WHERE community.community_id=?";

        return jdbcTemplate.queryForObject(query, new Object[]{communityID}, new CommunityRowMapper());
    }

    @Override
    public Boolean updateCommunity(Community community) {
        String query = "UPDATE community SET community_id = ?, created_by = ?, name = ?, description = ?, display_image = ? WHERE community_id = ?";
        int res = jdbcTemplate.update(query, new Object[]{community.getCommunity_id(), community.getCreated_by(), community.getName(), community.getDescription(), community.getDisplay_image(), community.getCommunity_id()});

        if(res == 1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Boolean deleteCommunity(int communityID) {
        String query = "DELETE FROM community WHERE community_id=?";

        int res = jdbcTemplate.update(query, new Object[]{communityID});

        if(res == 1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<Community> getAllCommunity() {
        String query =  "SELECT * FROM community";
        return jdbcTemplate.query(query, new CommunityRowMapper());
    }

    @Override
    public List<Community> getAllCommunity(String keyword) {
        String query = "SELECT * FROM community WHERE community.name LIKE \"" + keyword +"\"";
        return jdbcTemplate.query(query, new CommunityRowMapper());
    }

    @Override
    public List<Community> getAllUserCommunity(int userID) {
        String query = "SELECT community.community_id as community_id, community.created_by as created_by, community.name as name, community.description as description, community.display_image as display_image " +
                "FROM community, members where community.community_id = members.community_id AND members.user_id = " + userID;
        return jdbcTemplate.query(query, new CommunityRowMapper());
    }

    @Override
    public Boolean addCommunityInterest(int communityID, int interestID) {
        String query = "INSERT INTO community_interest (community_id, interest_id) VALUES(?,?)";

        // Executing query
        int res = jdbcTemplate.update(query, new Object[]{communityID, interestID});

        if(res == 1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<Interest> getCommunityInterests(int communityID) {
        String query = "SELECT interests.interest_id as interest_id, interests.name as name, interests.category as category FROM interests, community_interest " +
                "WHERE interests.interest_id = community_interest.interest_id AND community_interest.community_id = " + communityID;
        return jdbcTemplate.query(query, new InterestRowMapper());
    }

    @Override
    public Boolean deleteCommunityInterest(int communityID, int interestID) {
        String query = "DELETE FROM community_interest WHERE community_id=? AND interest_id = ?";

        int res = jdbcTemplate.update(query, new Object[]{communityID, interestID});

        if(res == 1){
            return true;
        }else{
            return false;
        }
    }
}
