package com.example.comelymusic.generate.common.config;

import com.alibaba.druid.util.StringUtils;
import com.example.comelymusic.generate.common.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * description: 拦截器中
 * 需要取出header中的token，然后去redis中进行判断，如果存在，则允许操作，则返回提示信息
 *
 * @author: zhangtian
 * @since: 2022-04-19 09:34
 */
public class AuthInterceptor implements HandlerInterceptor {

    private final String HEADER="Authorization";

    @Autowired
    private RedisUtils redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        String token = request.getHeader(HEADER);
        if (StringUtils.isEmpty(token)) {
            response.getWriter().print("用户未登录，请登录后操作！");
            return false;
        }
        Object loginStatus = redisService.get(token);
        if( Objects.isNull(loginStatus)){
            response.getWriter().print("token错误，请查看！");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
