package com.turubini.ezabuni.repositories.UserRepository;

import com.turubini.ezabuni.domain.User;
import com.turubini.ezabuni.exceptions.EzAuthException;
import com.turubini.ezabuni.exceptions.EzBadRequestException;
import com.turubini.ezabuni.exceptions.EzResourceNotFoundException;

import java.util.List;

public interface UserRepository {

    Integer create(String firstName, String middleName, String lastName, String email, String password, String phoneNumber, String county, String dob, String username, Integer departmentId) throws EzAuthException;

    User findByEmailAndPassword(String email, String password) throws EzAuthException;

    Integer getCountByEmail(String email);

    User findById (Integer userId);

    List<User> findAll() throws EzResourceNotFoundException;

    void update (Integer userId, User department) throws EzBadRequestException;

    void removeById (Integer userId) throws EzResourceNotFoundException;
}
