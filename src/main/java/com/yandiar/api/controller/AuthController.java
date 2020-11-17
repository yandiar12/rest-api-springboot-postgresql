/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yandiar.api.controller;

import com.yandiar.api.common.AppServer;
import com.yandiar.api.model.HeaderConstant;
import com.yandiar.api.model.response.ResponseModel;
import com.yandiar.api.model.UserToken;
import com.yandiar.api.model.request.LoginReq;
import com.yandiar.api.model.request.UserReq;
import com.yandiar.api.model.response.Response;
import com.yandiar.api.service.AuthService;
import com.yandiar.api.service.UserService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author USER
 */
@CrossOrigin
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Auth API", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"Auth"})
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;
    
    @Autowired
    private UserService userService;

    @PostMapping(value = "auth/signin", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "API Sign In", response = ResponseModel.class)
    private ResponseEntity<?> login(HttpServletRequest req, HttpServletResponse resp, 
            @Valid @RequestBody LoginReq login) throws ServletException {
        Response res = authService.login(login);
        return new ResponseEntity<Response>(res, HttpStatus.OK);
    }
    
    
    @PostMapping(value = "auth/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "User Registration", response = Response.class, notes = "String")
    private ResponseEntity<?> registerUser(HttpServletRequest req, HttpServletResponse resp, 
            @Valid @RequestBody UserReq userReq) throws ServletException {
        Response res = userService.registerUser(userReq);
        return new ResponseEntity<Response>(res, HttpStatus.OK);
    }
    
    @GetMapping(value = "test/jwt/secure")
    private ResponseEntity<?> test(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        String res = "Hello....";
        return new ResponseEntity<String>(res, HttpStatus.OK);
    }

}
