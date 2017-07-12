/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.infrastructure.web.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationFilter implements Filter {
    private String redirectTo;

    public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain chain
    ) throws ServletException, IOException {
        HttpSession session = ((HttpServletRequest) request).getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            ((HttpServletResponse) response).sendRedirect(redirectTo);
        } else {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
    }

    public void init(FilterConfig config) throws ServletException {
        redirectTo = config.getInitParameter("redirectTo");
    }
}
