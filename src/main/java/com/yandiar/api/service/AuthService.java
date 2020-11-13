/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yandiar.api.service;

import com.yandiar.api.common.TokenUtil;
import com.yandiar.api.exception.UnprocessableEntityException;
import com.yandiar.api.model.response.ResponseModel;
import com.yandiar.api.model.UserToken;
import com.yandiar.api.model.entity.User;
import com.yandiar.api.model.request.LoginReq;
import com.yandiar.api.model.response.Response;
import com.yandiar.api.repository.UserRepository;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author USER
 */
@Slf4j
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;
    
    public Response login(LoginReq login) {
        Response res = new Response();
        Map<String, String> map = new HashMap<>();
        Boolean userExists = userRepo.existsByEmail(login.getEmail());
        if (userExists) {
            User user = userRepo.findUserByEmail(login.getEmail());
            // check password
            String pass = DigestUtils.sha256Hex(login.getPassword());
            if (!pass.equals(user.getPasswd())) {
               throw new UnprocessableEntityException("Invalid email or password");
            } else {
            
                // build JWT token
                String token = TokenUtil.buildJWTToken(new UserToken(user.getEmail(), user.getName()));

                // response
                map.put("email", user.getEmail());
                map.put("name", user.getName());
                map.put("token", token);
                res.setMessage("success login");
                res.setData(map);
            }
        } else {
            throw new UnprocessableEntityException("Invalid email or password");
        }
        return res;
    }

    public ResponseModel getValidasiToken(String iduser, String appsource, String token) {
        ResponseModel res = new ResponseModel();
        
        return res;
    }

    public ResponseModel logout(String username, String token) {
        ResponseModel res = new ResponseModel();
        
        return res;
    }

}
