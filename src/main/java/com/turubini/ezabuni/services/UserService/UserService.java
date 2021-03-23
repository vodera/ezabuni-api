package com.turubini.ezabuni.services.UserService;

import com.turubini.ezabuni.domain.Department;
import com.turubini.ezabuni.domain.User;
import com.turubini.ezabuni.exceptions.EzAuthException;
import com.turubini.ezabuni.exceptions.EzBadRequestException;
import com.turubini.ezabuni.exceptions.EzResourceNotFoundException;

import java.sql.Date;
import java.util.List;

public interface UserService {

    User validateUser(String email, String password) throws EzAuthException;

    User registerUser ( String firstName, String middleName,
                       String lastName, String email, String password, String phoneNumber, String county,
                       String dob, String username, Integer departmentId) throws EzAuthException;

    //fetch all tender requests by passing user as the parameter
    List<User> fetchAllUsersByUserId ();

    //updating a user's information
    void updateUser(Integer userId, User user ) throws EzBadRequestException;

    //remove a user
    void removeUser(Integer userId) throws EzResourceNotFoundException;



}
