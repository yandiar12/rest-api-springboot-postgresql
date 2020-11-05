package com.yandiar.api.filter;

import com.yandiar.api.common.AppServer;
import com.yandiar.api.model.HeaderConstant;
import com.yandiar.api.model.response.ResponseModel;
import com.yandiar.api.model.dto.UserDto;
import com.yandiar.api.model.UserToken;
import com.yandiar.api.service.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

/**
 *
 * @author Jimmy Rengga
 */
@Slf4j
public class JwtFilter extends GenericFilterBean {

    private final AuthService authService;

    public JwtFilter(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        final String authHeader = request.getHeader(HeaderConstant.AUTHORIZATION);
        final String appsourceHeader = request.getHeader(HeaderConstant.APPSOURCE);

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(req, res);
        } else {

            if (StringUtils.isBlank(appsourceHeader)) {
                log.error("Appsource required");
                response.setStatus(401);
                response.getWriter().print("Appsource required");
                response.getWriter().flush();
                return;
            }
            
            if (!appsourceHeader.equals("KCT")) {
                log.error("Invalid appsource");
                response.setStatus(401);
                response.getWriter().print("Invalid Appsource");
                response.getWriter().flush();
                return;
            }
                
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                log.error("Invalid header");
                response.setStatus(401);
                response.getWriter().print("Missing or invalid Authorization header");
                response.getWriter().flush();
                return;
            }

            final String token = authHeader.substring(7);
            try {
                final Claims claims = Jwts.parser().setSigningKey(AppServer.JWT_SECRET_KEY)
                        .parseClaimsJws(token).getBody();
                request.setAttribute("claims", claims);
                UserToken dto = AppServer.decodeResponseModel(claims.getSubject(), UserToken.class);

                ResponseModel resUser = authService.getValidasiToken(dto.getUserName(), appsourceHeader, token);
                UserDto dtoUser = AppServer.decodeResponseModel(resUser, UserDto.class);
                log.info("resUser : {}", resUser);
                if (!resUser.isSuccess()) {
                    response.setStatus(401);
                    response.getWriter().print("Invalid User Token");
                    response.getWriter().flush();
                    return;
                }
                request.setAttribute(HeaderConstant.USER_SESSION, dtoUser.getUserName());
                request.setAttribute(HeaderConstant.CUSTOMER_OBJECT_SESSION, dtoUser);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("failed to extract claim {}", e.getMessage());
                response.setStatus(401);
                response.getWriter().print("Invalid token");
                response.getWriter().flush();
                return;
            }

            chain.doFilter(req, res);
        }

    }
}
