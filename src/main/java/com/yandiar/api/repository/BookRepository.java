/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yandiar.api.repository;

import com.yandiar.api.model.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author YAR
 */
@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    
    Page<Book> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
}
