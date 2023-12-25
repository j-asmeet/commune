package com.group6.commune.Repository;

import com.group6.commune.Mapper.InterestRowMapper;
import com.group6.commune.Mapper.UserInterestsRowMapper;
import com.group6.commune.Model.Event;
import com.group6.commune.Model.Interest;
import com.group6.commune.Model.UserInterests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InterestRepositoryImpl implements IInterestRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Interest> getInterestList() {
        var query = """
                    SELECT * FROM interests;
                """;
        return jdbcTemplate.query(query, new InterestRowMapper());
    }

    @Override
    public boolean saveUserInterest(int userId, int interestId) {
        var query = "INSERT INTO users_interests (user_id, interest_id) VALUES (?, ?)";

        int res = jdbcTemplate.update(query, userId, interestId);
        if(res == 1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<UserInterests> getInterestListByUserId(int user_id) {
        var query = "SELECT * FROM users_interests " +
                " WHERE user_id = " + user_id;
        List<UserInterests> userInterests = jdbcTemplate.query(query, new UserInterestsRowMapper());
        return userInterests == null ? new ArrayList<>() : userInterests;
    }
}
