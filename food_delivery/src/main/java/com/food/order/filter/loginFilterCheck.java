package com.food.order.filter;

import com.alibaba.fastjson.JSON;
import com.food.order.common.BaseContext;
import com.food.order.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class loginFilterCheck implements Filter {

    public static final AntPathMatcher PATH_MATCHER=new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI=request.getRequestURI();

        log.info("do filter,{}",requestURI);
        String [] urls=new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/sendMsg",
                "/user/login"
        };

        log.info("sadhoiasjdpasjdpakd{}",requestURI);
        boolean result=check(urls,requestURI);

        if(result){
            log.info("不需要跳转");
            filterChain.doFilter(request,response);
            return;
        }

        if(request.getSession().getAttribute("employee")!=null){
            log.info("已经登陆，id为{}",request.getSession().getAttribute("employee"));
            Long empid=(Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empid);
            filterChain.doFilter(request,response);
            return;
        }

        if(request.getSession().getAttribute("user")!=null){
            log.info("已经登陆，id为{}",request.getSession().getAttribute("user"));
            Long userid=(Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userid);
            filterChain.doFilter(request,response);
            return;
        }

        log.info("跳转至登陆界面");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    public boolean check(String[] urls,String requestURL){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURL);
            if(match){
                return true;
            }
        }
        return false;
    }

}
