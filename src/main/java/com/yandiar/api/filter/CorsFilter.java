/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yandiar.api.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 *
 * @author USER
 */
@Component
@Order(1)
public class CorsFilter implements Filter { //extends iconpln.common.http.filter.CorsFilter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "accept, accepts, accept-language, authorization, cache-control, content-disposition, content-encoding, content-length, content-range, content-type, if-match, if-none-match, oauth_consumer_key, oauth_nonce, oauth_signature, oauth_signature_method, oauth_timestamp, oauth_version, origin, range, user-time-offset, x-authorization, x-range, x-requested-with, username, random, signature, imei, auth-token, ");
        response.setHeader("Access-Control-Expose-Headers", "accept, accepts, accept-language, authorization, cache-control, content-disposition, content-encoding, content-length, content-range, content-type, if-match, if-none-match, oauth_consumer_key, oauth_nonce, oauth_signature, oauth_signature_method, oauth_timestamp, oauth_version, origin, range, user-time-offset, x-authorization, x-range, x-requested-with, username, random, signature, imei, auth-token, ");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        if (request.getMethod().equals("OPTIONS")) {
          response.getWriter().print("OK");
          response.getWriter().flush();
        } else {
          chain.doFilter(req, res);
        } 
 
    }
}
