/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yandiar.api.service;

import com.yandiar.api.model.entity.Book;
import com.yandiar.api.model.response.Response;
import com.yandiar.api.repository.BookRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author YAR
 */
@Service
@Slf4j
public class BookService {
    
    @Autowired
    private BookRepository bookRepo;
    
    public Response findAll(int pageNo, int pageSize, String sortBy) {
        Response res = new Response();
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Slice<Book> books = bookRepo.findAll(paging);
        if (books.hasContent()) {
            res.setMessage("success");
        } else {
            res.setMessage("Data Not Found");
        }
        res.setData(books);
        return res;
    }
    
    public Response findAllByName(String name, String sortBy) {
        Response res = new Response();
        Pageable paging = PageRequest.of(0, Integer.MAX_VALUE, Sort.by(sortBy));
        Slice<Book> page = bookRepo.findAllByNameContainingIgnoreCase(name, paging);
        
        res.setMessage("success");
        res.setData(page);
        return res;
    }
    
    public Book findById(Long id) {
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID doesn't exists: " + id));
        return book;
    }
    
    public Optional<Book> checkBookById(Long id) {
        Optional<Book> book = bookRepo.findById(id);
        return book;
    }
    
    public Response save(Book book) {
        Response res = new Response();
        try {
            bookRepo.save(book);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getLocalizedMessage(), ex);
        }
        res.setMessage("Book successfully added");
        res.setData(book);
        return res;
    }
    
    public Response edit(Book book) {
        Response res = new Response();
        try {
            Book b = bookRepo.findById(book.getId())
                .orElseThrow(() -> new IllegalArgumentException("ID doesn't exists: " + book.getId()));
            
            bookRepo.save(book);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getLocalizedMessage(), ex);
        }
        res.setMessage("Book successfully edited");
        res.setData(book);
        return res;
    }
    
    public Response delete(long id) {
        Response res = new Response();
        Book book = new Book();
        try {
            book = bookRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID doesn't exists: " + id));
            
            bookRepo.delete(book);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getLocalizedMessage(), ex);
        }
        res.setMessage("Book successfully deleted");
        res.setData(book);
        return res;
    }
}
