/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.auth.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationFilter implements Filter {
    private String redirectTo;

    public void doFilter(
        ServletRequest req,
        ServletResponse resp,
        FilterChain chain
    ) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            ((HttpServletResponse) resp).sendRedirect(redirectTo);
        } else {
            chain.doFilter(req, resp);
        }
    }

    public void destroy() {
    }

    public void init(FilterConfig config) throws ServletException {
        redirectTo = config.getInitParameter("redirectTo");
    }
}
