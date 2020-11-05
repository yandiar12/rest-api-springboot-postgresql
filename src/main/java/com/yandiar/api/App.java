/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yandiar.api;

import com.yandiar.api.common.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 *
 * @author USER
 */
@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class App {
    
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
    
    
}
