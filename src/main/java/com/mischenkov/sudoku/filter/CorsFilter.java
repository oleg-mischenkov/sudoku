package com.mischenkov.sudoku.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class CorsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var httpServletResp = (HttpServletResponse) servletResponse;

        httpServletResp.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        httpServletResp.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        httpServletResp.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResp.setHeader("Access-Control-Allow-Origin", "http://localhost:63342");

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
