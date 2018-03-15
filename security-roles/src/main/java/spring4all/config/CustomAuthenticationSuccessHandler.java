package spring4all.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final SimpleGrantedAuthority admin = new SimpleGrantedAuthority("ADMIN");
    private final SimpleGrantedAuthority sales = new SimpleGrantedAuthority("SALES_REP");
    private final SimpleGrantedAuthority order = new SimpleGrantedAuthority("ORDER_REP");

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if(authentication.getAuthorities().contains(sales)){
            this.setDefaultTargetUrl("/sales");
        }
        if(authentication.getAuthorities().contains(order)){
            this.setDefaultTargetUrl("/order");
        }
        if(authentication.getAuthorities().contains(admin)){
            this.setDefaultTargetUrl("/admin");
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }

}
