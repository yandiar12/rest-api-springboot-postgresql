package com.yandiar.api.filter;

import com.google.gson.Gson;
import com.yandiar.api.common.AppServer;
import com.yandiar.api.common.TokenUtil;
import com.yandiar.api.model.HeaderConstant;
import com.yandiar.api.model.UserToken;
import com.yandiar.api.model.entity.User;
import com.yandiar.api.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.GenericFilterBean;

/**
 *
 * @author Jimmy Rengga (modify by YAR)
 */
@Slf4j
public class JwtFilter extends GenericFilterBean {

    private final UserService userService;

    public JwtFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        final String authHeader = request.getHeader(HeaderConstant.AUTHORIZATION);
        final Gson gson = new Gson();
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(req, res);
        } else {

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                log.error("Invalid header");
                
                Map map = new HashMap();
                map.put("status", "401");
                map.put("message", "Missing or invalid Authorization header");
                map.put("error", "Unauthorized");
                String jsonS = gson.toJson(map);
                
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(401);
                response.getWriter().print(jsonS);
                response.getWriter().flush();
                return;
            }

            final String token = authHeader.substring(7);
            System.out.println("token : "+token);
            try {
                if (TokenUtil.validateJwtToken(token)) {
                    final Claims claims = Jwts.parser().setSigningKey(AppServer.JWT_SECRET_KEY)
                            .parseClaimsJws(token).getBody();
                    request.setAttribute("claims", claims);
                    System.out.println("Claims : "+claims);
                    System.out.println("Time : "+ new Date((new Date()).getTime()));
                    UserToken dto = AppServer.decodeResponseModel(claims.getSubject(), UserToken.class);
                    
                    System.out.println("Email : "+dto.getEmail());
                    
                    User user = userService.findUserbyEmail(dto.getEmail());
                    log.info("resUser : {}", user.toString());
                    if (user.getEmail() == null) {
                        response.setStatus(401);
                        response.getWriter().print("Invalid User Token");
                        response.getWriter().flush();
                        return;
                    }
                    request.setAttribute(HeaderConstant.USER_SESSION, user.getEmail());
                    request.setAttribute(HeaderConstant.CUSTOMER_OBJECT_SESSION, user);
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Unauthorized Error {}", e.getMessage());
                
                Map map = new HashMap();
                map.put("status", "401");
                map.put("message", e.getCause().getMessage());
                map.put("error", "Unauthorized");
                String jsonS = gson.toJson(map);
                
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(401);
                response.getWriter().print(jsonS);
                response.getWriter().flush();
                return;
            }

            chain.doFilter(req, res);
        }

    }
}
