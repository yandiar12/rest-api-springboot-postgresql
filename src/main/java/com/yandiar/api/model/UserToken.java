/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yandiar.api.model;

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
public class UserToken {
    private static final long serialVersionUID = 2676415386199858891L;
    private String email; 
    private String name; 
}
