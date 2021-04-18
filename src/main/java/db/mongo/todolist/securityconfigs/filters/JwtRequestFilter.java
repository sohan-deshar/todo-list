package db.mongo.todolist.securityconfigs.filters;

import com.google.gson.Gson;
import db.mongo.todolist.models.transferobjs.GenericErrorResponse;
import db.mongo.todolist.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    Logger log = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    @Lazy
    private UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public JwtRequestFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");


        String username = null;
        String jwt = null;


        try {
            if(authHeader != null && authHeader.startsWith("Bearer ")) {
                jwt = authHeader.substring(7);
                username = jwtUtil.extractUsername(jwt);
            }
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if (jwtUtil.validateToken(jwt, userDetails, request)) {
                    UsernamePasswordAuthenticationToken UAPtoken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities()
                            );
                    UAPtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(UAPtoken);
                }
            }
        } catch (Exception e) {
            GenericErrorResponse genericErrorResponse = new GenericErrorResponse(e.getMessage());
            String json = new Gson().toJson(genericErrorResponse);
            log.error(e.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
            response.getWriter().flush();

//            log.error(e.getMessage());
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            response.getWriter().write(e.getMessage());
//            response.getWriter().flush();
            return;
        }
        filterChain.doFilter(request, response);
    }
}
