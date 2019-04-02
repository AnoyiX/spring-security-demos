package com.spring4all.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.spring4all.entity.UserDO;
import com.spring4all.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义 JSON 登录
 */
@Slf4j
public class CustomJSONLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final UserService userService;

    CustomJSONLoginFilter(String defaultFilterProcessesUrl, UserService userService) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl, HttpMethod.POST.name()));
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        JSONObject requestBody = getRequestBody(httpServletRequest);
        String username = requestBody.getString("username");
        String password = requestBody.getString("password");
        validateUsernameAndPassword(username, password);
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("USER"));
        return new UsernamePasswordAuthenticationToken(username, password, simpleGrantedAuthorities);
    }

    /**
     * 获取请求体
     */
    private JSONObject getRequestBody(HttpServletRequest request) throws AuthenticationException{
        try {
            StringBuilder stringBuilder = new StringBuilder();
            InputStream inputStream = request.getInputStream();
            byte[] bs = new byte[StreamUtils.BUFFER_SIZE];
            int len;
            while ((len = inputStream.read(bs)) != -1) {
                stringBuilder.append(new String(bs, 0, len));
            }
            return JSON.parseObject(stringBuilder.toString());
        } catch (IOException e) {
            log.error("get request body error.");
        }
        throw new AuthenticationServiceException("invalid request body");
    }

    /**
     * 校验用户名和密码
     */
    private void validateUsernameAndPassword(String username, String password) throws AuthenticationException {
        UserDO userDO = userService.getByUsername(username);
        if (userDO == null){
            throw new UsernameNotFoundException("user not exist");
        }
        if(!userDO.getPassword().equals(password)){
            throw new AuthenticationServiceException("error username or password");
        }
    }

}
