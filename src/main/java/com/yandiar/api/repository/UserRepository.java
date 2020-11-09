/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yandiar.api.repository;

import com.yandiar.api.model.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author YAR
 */
@Repository
public interface UserRepository extends JpaRepository<User, Object>{
    
    User findUserByEmail(String email);
    
    Boolean existsByEmail(String email);
    
}
