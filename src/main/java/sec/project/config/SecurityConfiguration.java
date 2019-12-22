package sec.project.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // no real security at the moment
        http.authorizeRequests().anyRequest().permitAll();

        http.addFilterAfter(new Filter() {
            @Override
            public void init(FilterConfig filterConfig) throws ServletException {}
            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
                HttpServletRequest req = (HttpServletRequest) request;
                HttpServletResponse res = (HttpServletResponse) response;
                String userId = req.getRequestURI().replaceAll("\\D", "");
                if (req.getRequestURI().startsWith("/todo") && !req.getRequestURI().contains("update")) {
                    Cookie[] cookies = req.getCookies();
                    if (cookies == null) {
                        res.sendError(HttpServletResponse.SC_FORBIDDEN);
                    } else {
                        boolean found = false;
                        for (Cookie c : cookies) {
                            if ("user".equals(c.getName()) && c.getValue().equals(userId)) {
                                found = true;
                            }
                        }

                        if (!found) {
                            res.sendError(HttpServletResponse.SC_FORBIDDEN);
                        } else  {
                            chain.doFilter(request, response);
                        }
                    }
                } else {
                    chain.doFilter(request, response);
                }
            }
            @Override
            public void destroy() {}
        }, BasicAuthenticationFilter.class);
    }
}
