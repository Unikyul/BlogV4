package org.example.springv3.core.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.springv3.core.util.JwtUtil;
import org.example.springv3.core.util.Resp;
import org.example.springv3.user.User;

import java.io.IOException;
import java.io.PrintWriter;

// 책임 : 인가!!

public class JwtAuthorizationFilter  implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain Chain) throws IOException, ServletException {
        HttpServletRequest rq = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) response;

        String accessToken = rq.getHeader("Authorization");

        if(accessToken == null || accessToken.isBlank()){
            // resp 해줘야 함
            resp.setHeader( "Content-Type","application/json; charset=utf-8");
            PrintWriter out = resp.getWriter();
            //통신은 객체를 던지면 안된다. fail은 자바객체이다! 필터에서는 인식을 못 한다.
            Resp fail = Resp.fail(401, "Token이 없습니다.");
            String responseBody = new ObjectMapper().writeValueAsString(fail);
            out.print(responseBody);
            out.flush();
            return;
        }
        //try catch 를 쓴 이유는 항상 동일의 형태로 담아주기 위해서..
        try{
            User sessionUser = JwtUtil.verify(accessToken); // 서명이 위조, 만료
            HttpSession session = rq.getSession();
            session.setAttribute("sessionUser", sessionUser);
            Chain.doFilter(rq,resp); // 다음 필터로 가!! 없으면 DS로 감.
        } catch (Exception e){
            resp.setHeader( "Content-Type","application/json; charset=utf-8");
            PrintWriter out = resp.getWriter();
            //통신은 객체를 던지면 안된다. fail은 자바객체이다! 필터에서는 인식을 못 한다.
            Resp fail = Resp.fail(401, e.getMessage());
            String responseBody = new ObjectMapper().writeValueAsString(fail);
            out.print(responseBody);
            out.flush();
        }





     //   System.out.println("JwtAuthorizationFilter 필터가 동작했습니다.");

      //  resp.setHeader("Content-Type", "text/plain");

      // PrintWriter out = resp.getWriter(); //쓰기 버퍼
      //  out.println("<h1>좋아<h1>");
      //  out.flush();

    }
}
