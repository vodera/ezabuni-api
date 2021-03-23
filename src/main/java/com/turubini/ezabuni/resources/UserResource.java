package com.turubini.ezabuni.resources;

import com.turubini.ezabuni.Constants;
import com.turubini.ezabuni.domain.User;
import com.turubini.ezabuni.services.UserService.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    @Autowired
    UserService userService;

    @PostMapping("/login2")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, Object> userMap) {
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");
        User user = userService.validateUser(email, password);

//        Map<String, String> map = new HashMap<>();
//        map.put("message", "User logged in successfully");
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registeruser(@RequestBody Map<String, Object> userMap) {
        String firstName = (String) userMap.get("firstName");
        String middleName = (String) userMap.get("middleName");
        String lastName = (String) userMap.get("lastName");
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");
        String phoneNumber = (String) userMap.get("phoneNumber");
        String county = (String) userMap.get("county");
        String dob = (String) userMap.get("dob");
        String username = (String) userMap.get("username");
        Integer departmentId = (Integer) userMap.get("departmentId");


        User user = userService.registerUser(firstName, middleName, lastName, email, password, phoneNumber, county, dob, username, departmentId );
//        Map<String, String> map = new HashMap<>();
//        map.put("message", "registered successfully");

        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers(HttpServletRequest request) {
//        int userId = (Integer) request.getAttribute("userId");
        List<User> users = userService.fetchAllUsersByUserId();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Map<String, Boolean>> updateUser(HttpServletRequest request,
                                                                 @PathVariable("userId") Integer userId,
                                                                 @RequestBody User user) {
//        int userId = (Integer) request.getAttribute("userId");
        userService.updateUser(userId, user);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(HttpServletRequest request,
                                                                 @PathVariable("userId") Integer userId) {
//        int userId = (Integer) request.getAttribute("userId");
        userService.removeUser(userId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    private Map<String, String> generateJWTToken (User user) {
        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
                .claim("userId", user.getUserId())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .claim("middleName", user.getMiddleName())
                .claim("email", user.getEmail())
                .claim("phonuNumber", user.getPhoneNumber())
                .claim("username", user.getUsername())
                .claim("county", user.getCounty())
                .claim("dob", user.getDob())
                .claim("departmentId", user.getDepartmentId())
                .compact();
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
//        System.out.println("token: "+ token);
        return map;
    }
}
