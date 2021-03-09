package com.turubini.ezabuni.services.UserService;

import com.turubini.ezabuni.domain.User;
import com.turubini.ezabuni.exceptions.EzAuthException;
import com.turubini.ezabuni.repositories.UserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User validateUser(String email, String password) throws EzAuthException {
        if(email != null) email = email.toLowerCase();

        return userRepository.findByEmailAndPassword(email, password);
    }


    @Override
    public User registerUser(String firstName, String middleName, String lastName, String email, String password, String phoneNumber, String county,  String dob, String username) throws EzAuthException {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        if(email != null) email = email.toLowerCase();
        if (!pattern.matcher(email).matches())
            throw new EzAuthException("Invalid email format");
        Integer count = userRepository.getCountByEmail(email);
        if(count > 0)
            throw new EzAuthException("Email already in use");
        Integer userId = userRepository.create(firstName, middleName, lastName, email, password, phoneNumber, county, dob, username );
        return userRepository.findById(userId);
    }
}
