package com.turubini.ezabuni.services.UserService;

import com.turubini.ezabuni.domain.User;
import com.turubini.ezabuni.exceptions.EzAuthException;

import java.sql.Date;

public interface UserService {

    User validateUser(String email, String password) throws EzAuthException;

    User registerUser ( String firstName, String middleName,
                       String lastName, String email, String password, String phoneNumber, String county,
                       String dob, String username) throws EzAuthException;


}
