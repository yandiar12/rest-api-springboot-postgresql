/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yandiar.api.service;

import com.yandiar.api.exception.UnprocessableEntityException;
import com.yandiar.api.model.entity.User;
import com.yandiar.api.model.request.UserReq;
import com.yandiar.api.model.response.Response;
import com.yandiar.api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author YAR
 */
@Service
@Slf4j
public class UserService {
    
    @Autowired
    private UserRepository userRepo;
    
    public User findUserbyEmail(String email) {
        User user = userRepo.findUserByEmail(email);
        return user;
    }

    public Response getUserbyEmail(String email) {
        Response res = new Response();
        
        User user = userRepo.findUserByEmail(email);
        res.setMessage("fetch data");
        res.setData(user);
        return res;
    }
    
    public Response registerUser(UserReq user) {
        Response res = new Response();
        User userData = new User();
        
        Boolean exists = userRepo.existsByEmail(user.getEmail());
        if (exists) {
            throw new UnprocessableEntityException("Email is already exists");
        }
        
        userData.setEmail(user.getEmail());
        userData.setName(user.getName());
        userData.setPasswd(DigestUtils.sha256Hex(user.getPasswd()));
        
        userRepo.save(userData);
        res.setMessage("User successfully created");
        res.setData(null);
        return res;
    }
    
    public Response update(UserReq user) {
        Response res = new Response();
        User userData = new User();
        
        userData = userRepo.findUserByEmail(user.getEmail());
        if (userData == null) {
            throw new UnprocessableEntityException("Email not found");
        }
        
        userData.setName(user.getName());
        System.out.println("user.getName(): "+user.getName());
        userRepo.save(userData);
        res.setMessage("User successfully updated");
        res.setData(null);
        return res;
    }

}
