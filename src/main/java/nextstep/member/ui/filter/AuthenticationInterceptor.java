package nextstep.member.ui.filter;

import io.jsonwebtoken.JwtException;
import nextstep.member.application.JwtTokenProvider;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AuthenticationInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String jwt = parsingJWT(request);

        request.setAttribute("email", jwtTokenProvider.getPrincipal(jwt));

        return true;
    }

    private String parsingJWT(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("authorization");

        if(authorizationHeader.isBlank()){
            throw new JwtException("인증 정보가 없습니다.");
        }

        return authorizationHeader.substring("Bearer ".length());
    }
}