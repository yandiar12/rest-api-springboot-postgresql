/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yandiar.api.service;

import com.yandiar.api.common.AppServer;
import com.yandiar.api.common.TokenUtil;
import com.yandiar.api.model.response.ResponseModel;
import com.yandiar.api.model.dto.UserDto;
import com.yandiar.api.model.UserToken;
import com.yandiar.api.model.entity.User;
import com.yandiar.api.model.request.LoginReq;
import com.yandiar.api.model.response.Response;
import com.yandiar.api.repository.UserRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
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
        User user = userRepo.findUserByEmail(login.getEmail());
        String token = TokenUtil.buildJWTToken(new UserToken(user.getEmail(), user.getName()));
        res.setMessage("success login");
        res.setData(token);
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
