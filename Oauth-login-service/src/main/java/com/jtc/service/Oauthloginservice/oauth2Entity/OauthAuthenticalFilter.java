package com.jtc.service.Oauthloginservice.oauth2Entity;


import com.jtc.service.Oauthloginservice.exceptionClass.NoSuchNameException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class OauthAuthenticalFilter extends OncePerRequestFilter {

    @Autowired
    private PersonUserService personUserService;

    @Autowired
    private JwtService jwtService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException
    {
        final String authHead = request.getHeader("Authorization");
        final  String token;
        final String username;

        if (authHead==null || authHead.startsWith("Bearer"))
        {
            filterChain.doFilter(request,response);
            return;
        }
        token = authHead.substring(7);
        username = jwtService.getExtractedUserName(token);


        // i have to check if token is valid.
        if (username !=null && SecurityContextHolder.getContext().getAuthentication()==null) {

                PersonUser userDetails = (PersonUser) personUserService.loadUserByUsername(username);


                if (jwtService.isTokenValid(token, userDetails)) {

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    filterChain.doFilter(request, response);
                }
                else
                {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setCharacterEncoding("utf-8");
                    response.setHeader("context-type", "text/charset.utf-8");
                    response.getWriter().println("Your not who you said you're");
                }

            }
            else
            {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setCharacterEncoding("utf-8");
                response.setHeader("context-type", "text/charset.utf-8");
                response.getWriter().println("Your not who you said you're");
            }
        }


    }

