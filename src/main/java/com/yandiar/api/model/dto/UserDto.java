/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yandiar.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author USER
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDto {
    
    private static final long serialVersionUID = 2676415316199858891L;
    private String mail; 
    private String userName; 
    private String firstName; 
    private String lastName; 
    private String unitId; 
    private String createBy; 
    private String createDate;
    private String status; 
    private String loginDate; 
    private String logoutDate;
    private String token;
    private String tokenExpiredDate;
    private String AppVersion;
    
}
