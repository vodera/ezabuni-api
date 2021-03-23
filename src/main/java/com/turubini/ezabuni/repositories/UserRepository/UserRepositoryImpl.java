package com.turubini.ezabuni.repositories.UserRepository;

import com.turubini.ezabuni.domain.User;
import com.turubini.ezabuni.exceptions.EzAuthException;
import com.turubini.ezabuni.exceptions.EzBadRequestException;
import com.turubini.ezabuni.exceptions.EzResourceNotFoundException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final String SQL_CREATE = "INSERT INTO USERS(USERID, FIRSTNAME, MIDDLENAME, LASTNAME," +
            "EMAIL, PASSWORD, PHONENUMBER, COUNTY, DOB, USERNAME, DEPARTMENTID) VALUES(NEXTVAL('USERS_SEQ'), ?, ? ,?, ? ,? ,?, ?, ?, ?, ?)";

    private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM USERS WHERE EMAIL = ?";

    private static final String SQL_FIND_BY_ID = "SELECT USERID, FIRSTNAME, MIDDLENAME, LASTNAME, DOB, EMAIL, USERNAME," +
            "PASSWORD, COUNTY, PHONENUMBER, DEPARTMENTID FROM USERS WHERE USERID = ?";

    private static final String SQL_FIND_BY_EMAIL = "SELECT USERID, FIRSTNAME, MIDDLENAME, LASTNAME, EMAIL, PASSWORD, PHONENUMBER, COUNTY, DOB, USERNAME, DEPARTMENTID FROM USERS " +
            "WHERE EMAIL = ?";

    public static final String SQL_FIND_ALL="SELECT USERID, FIRSTNAME, MIDDLENAME, LASTNAME, EMAIL, PASSWORD, COUNTY, USERNAME, DOB, PHONENUMBER, DEPARTMENTID FROM USERS GROUP BY USERID";
    public static final String SQL_UPDATE="UPDATE USERS SET FIRSTNAME = ?, MIDDLENAME = ?, LASTNAME = ?, EMAIL = ?, PASSWORD = ?, COUNTY = ?, USERNAME = ?, DOB = ?, PHONENUMBER = ?, DEPARTMENTID = ?  WHERE USERID = ?";
    public static final String SQL_DELETE="DELETE FROM USERS WHERE USERID = ?";



    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer create(String firstName, String middleName, String lastName, String email, String password, String phoneNumber, String county, String dob, String username, Integer departmentId) throws EzAuthException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        try {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, firstName);
            ps.setString(2, middleName);
            ps.setString(3, lastName);
            ps.setString(4, email);
            ps.setString(5, hashedPassword);
            ps.setString(6, phoneNumber);
            ps.setString(7, county);
            ps.setString(8, dob);
            ps.setString(9, username);
            ps.setInt(10, departmentId);
            return ps;

        }, keyHolder);
        return (Integer) keyHolder.getKeys().get("USERID");
        }catch (Exception e) {
            throw new EzAuthException("Invalid details. Failed to create account");
    }

    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws EzAuthException {
        try {
                User user = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object[]{email}, userRowMapper);
            if(!BCrypt.checkpw(password, user.getPassword())){
                    throw new EzAuthException("Invalid email and/or password");
                }
                return user;
        }catch (EmptyResultDataAccessException e) {
               throw new EzAuthException("Invalid email and/or password");
        }
    }

    @Override
    public Integer getCountByEmail(String email) {
        return  jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, new Object[]{email}, Integer.class);
    }

    @Override
    public User findById(Integer userId) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId}, userRowMapper);
    }

    @Override
    public List<User> findAll() throws EzResourceNotFoundException {
        return jdbcTemplate.query(SQL_FIND_ALL, new Object[]{}, userRowMapper);
    }

    @Override
    public void update(Integer userId, User user) throws EzBadRequestException {
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));
        try {
            jdbcTemplate.update(SQL_UPDATE, new Object[]{
                    user.getFirstName(),
                    user.getMiddleName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getCounty(),
                    user.getUsername(),
                    user.getDob(),
                    user.getPhoneNumber(),
                    user.getDepartmentId(),
                    userId});
        }catch (Exception e){
            throw new EzBadRequestException("Invalid request");
        }
    }

    @Override
    public void removeById(Integer userId) throws EzResourceNotFoundException {
        int count = jdbcTemplate.update(SQL_DELETE, new Object[]{userId});
        if(count == 0)
            throw new EzResourceNotFoundException("User not found");
    }

    private RowMapper<User> userRowMapper =((rs, rowNum) -> {
       return new User(
               rs.getInt("USERID"),
               rs.getString("FIRSTNAME"),
               rs.getString("MIDDLENAME"),
               rs.getString("LASTNAME"),
               rs.getString("EMAIL"),
               rs.getString("PASSWORD"),
               rs.getString("PHONENUMBER"),
               rs.getString("COUNTY"),
               rs.getString("DOB"),
               rs.getString("USERNAME"),
               rs.getInt("DEPARTMENTID")

       );
    });
}
