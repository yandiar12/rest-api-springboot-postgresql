/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yandiar.api.controller;

import com.yandiar.api.model.entity.Book;
import com.yandiar.api.model.request.BookReq;
import com.yandiar.api.model.response.Response;
import com.yandiar.api.service.BookService;
import io.swagger.annotations.Api;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author YAR
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Book API", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"book"})
public class BookController {
    
    @Autowired
    private BookService bookService;
    
    @GetMapping(value = "book/findall")
    public ResponseEntity<?> findAll(HttpServletRequest req, HttpServletResponse resp,
            @RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Response res = new Response();
        res = bookService.findAll(pageNo, pageSize, sortBy);
        return ResponseEntity.ok(res);
    }
    
    @GetMapping(value = "book/findallbyname")
    public ResponseEntity<?> findAllByName(HttpServletRequest req, HttpServletResponse resp,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "id") String sortBy) {
        Response res = new Response();
        res = bookService.findAllByName(name, sortBy);
        return ResponseEntity.ok(res);
    }
    
    @GetMapping(value = "book/findbyid/{id}")
    public ResponseEntity<?> findById(HttpServletRequest req, HttpServletResponse resp,
            @PathVariable Long id) {
        Response res = new Response();
        Book book = bookService.findById(id);
        res.setMessage("Success");
        res.setData(book);
        return ResponseEntity.ok(res);
    }
    
    @PostMapping(value = "book", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(HttpServletRequest req, HttpServletResponse resp,
            @RequestBody @Valid BookReq book) {
        Response res = new Response();
        Book b = new Book();
        b.setName(book.getName());
        b.setAuthor(book.getAuthor());
        b.setPublisher(book.getPublisher());
        b.setPrice(book.getPrice());
        res = bookService.save(b);
        return ResponseEntity.ok(res);
    }
    
    @PutMapping(value = "book/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> edit(HttpServletRequest req, HttpServletResponse resp,
            @PathVariable Long id, @RequestBody @Valid BookReq bookDetail) {
        Response res = new Response();
        Book b = new Book();
        b.setId(id);
        b.setName(bookDetail.getName());
        b.setAuthor(bookDetail.getAuthor());
        b.setPublisher(bookDetail.getPublisher());
        b.setPrice(bookDetail.getPrice());
        res = bookService.edit(b);
        return ResponseEntity.ok(res);
    }
    
    @DeleteMapping(value = "book/{id}")
    public ResponseEntity<?> delete(HttpServletRequest req, HttpServletResponse resp,
            @PathVariable Long id) {
        Response res = new Response();
        res = bookService.delete(id);
        return ResponseEntity.ok(res);
    }
}
