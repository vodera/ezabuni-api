package com.turubini.ezabuni.repositories.UserRepository;

import com.turubini.ezabuni.domain.User;
import com.turubini.ezabuni.exceptions.EzAuthException;

import java.sql.Date;

public interface UserRepository {

    Integer create (String firstName, String middleName, String lastName, String email,  String password, String phoneNumber,  String county, String dob, String username) throws EzAuthException;

    User findByEmailAndPassword(String email, String password) throws EzAuthException;

    Integer getCountByEmail(String email);

    User findById (Integer userId);
}
