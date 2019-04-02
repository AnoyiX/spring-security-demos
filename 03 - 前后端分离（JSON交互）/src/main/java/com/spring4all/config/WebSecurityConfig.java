package com.spring4all.config;

import com.spring4all.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    /**
     * 匹配 "/register" 路径，不需要权限即可访问
     * 匹配 "/user" 及其以下所有路径，都需要 "USER" 权限
     * 登录地址为 "/login"，登录成功返回响应状态码
     * 退出登录的地址为 "/logout"，退出成功返回响应状态码
     * 禁用 CSRF
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/register").permitAll()
                .antMatchers("/user/**").hasAuthority("USER")
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .and()
                .logout().logoutUrl("/logout")
                         .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                         .clearAuthentication(true).permitAll()
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                                    .accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .csrf().disable();

        http.addFilterAt(customJSONLoginFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 自定义认证过滤器
     */
    private CustomJSONLoginFilter customJSONLoginFilter() {
        CustomJSONLoginFilter customJSONLoginFilter = new CustomJSONLoginFilter("/login", userService);
        customJSONLoginFilter.setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler());
        customJSONLoginFilter.setAuthenticationSuccessHandler(new CustomAuthenticationSuccessHandler());
        return customJSONLoginFilter;
    }

}
