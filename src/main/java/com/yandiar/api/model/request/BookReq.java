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
public class BookReq implements Serializable {
    
    @ApiModelProperty(notes = "Name", position = 1, required = true)
    private String name;
    @ApiModelProperty(notes = "Author", position = 2, required = true)
    private String author;
    @ApiModelProperty(notes = "Publisher", position = 3, required = true)
    private String publisher;
    @ApiModelProperty(notes = "Price", position = 4, required = true)
    private double price;
}
