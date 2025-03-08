package com.vlrnsnk.reimbursemate.config;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class SameSiteCookieFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Get all the cookies set in the response headers
        for (String cookieHeader : httpResponse.getHeaders("Set-Cookie")) {
            // If the cookie is a session cookie, add SameSite=None; Secure attributes
            if (cookieHeader.contains("JSESSIONID")) {
                // Modify the Set-Cookie header to include SameSite=None; Secure
                String updatedCookie = cookieHeader + "; SameSite=None; Secure";
                httpResponse.setHeader("Set-Cookie", updatedCookie);
            }
        }

        // Continue with the filter chain
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup if necessary
    }
}
