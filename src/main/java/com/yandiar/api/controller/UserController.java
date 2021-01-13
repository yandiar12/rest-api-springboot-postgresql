/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yandiar.api.controller;

import com.yandiar.api.model.request.UserReq;
import com.yandiar.api.model.response.Response;
import com.yandiar.api.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author YAR
 */
@CrossOrigin
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "User API", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"User"})
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping(value = "user")
    @ApiOperation(value = "User", response = Response.class, notes = "String")
    private ResponseEntity<?> getUser(HttpServletRequest req, HttpServletResponse resp, 
            @Valid @RequestParam String email) throws ServletException {
        Response res = userService.getUserbyEmail(email);
        return new ResponseEntity<Response>(res, HttpStatus.OK);
    }
    
    @PostMapping(value = "user/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "User Update", response = Response.class, notes = "String")
    private ResponseEntity<?> userUpdate(HttpServletRequest req, HttpServletResponse resp, 
            @Valid @RequestBody UserReq userReq) throws ServletException {
        Response res = userService.update(userReq);
        return new ResponseEntity<Response>(res, HttpStatus.OK);
    }
}
