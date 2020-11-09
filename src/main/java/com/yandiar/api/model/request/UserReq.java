/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yandiar.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author YAR
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserReq implements Serializable {
    
    @ApiModelProperty(notes = "Email", position = 1, required = true)
    private String email;
    
    @ApiModelProperty(notes = "Name", position = 2, required = true)
    private String name;
    
    @ApiModelProperty(notes = "Password", position = 3, required = true)
    private String passwd;
    
}
