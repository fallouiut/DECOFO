package m2info.ter.decofo.auth.filter;

import m2info.ter.decofo.classes.User;
import m2info.ter.decofo.exceptions.DecofoException;
import m2info.ter.decofo.manager.auth.AuthManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class AuthFilter implements Filter {

    @Autowired
    AuthManager authManager;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;

        // récupérer le token
        String token = req.getParameter("accessToken");
        System.err.println("Token: '" + token + "'");

        // vérifier
        if (!req.getRequestURI().contains("/login") && !req.getRequestURI().contains("generate")) {
            if(token == null) throw new ServletException("Non Authentifié");
            if(authManager.isTokenExpired(token)) throw new ServletException("Token expiré");
        }



        filterChain.doFilter(servletRequest, servletResponse);
    }
}
