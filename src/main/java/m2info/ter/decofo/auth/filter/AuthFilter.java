package m2info.ter.decofo.auth.filter;

import m2info.ter.decofo.classes.User;
import m2info.ter.decofo.exceptions.DecofoException;
import m2info.ter.decofo.manager.auth.AuthManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@CrossOrigin(origins = "*")
@Component
public class AuthFilter implements Filter {

    @Autowired
    AuthManager authManager;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;

        // récupérer le token
        String token = req.getParameter("accessToken");
        System.out.println("Token: '" + token + "'");

        // vérifier
        if (!req.getRequestURI().contains("/login") && !req.getRequestURI().contains("generate")) {
            if(token == null) throw new ServletException("Non Authentifié");
            if(authManager.isTokenExpired(token)) throw new ServletException("Token expiré");
        }

        ((HttpServletResponse)servletResponse).addHeader("Access-Control-Allow-Headers", "*");
        ((HttpServletResponse)servletResponse).addHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse)servletResponse).addHeader("Access-Control-Allow-Methods", "GET,OPTIONS,HEAD,PUT,POST");


        filterChain.doFilter(servletRequest, servletResponse);
    }
}
