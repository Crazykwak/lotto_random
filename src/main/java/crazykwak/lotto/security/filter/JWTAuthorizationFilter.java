package crazykwak.lotto.security.filter;

import crazykwak.lotto.jwt.JWTService;
import crazykwak.lotto.member.entity.Member;
import crazykwak.lotto.member.service.MemberService;
import crazykwak.lotto.security.principal.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final JWTService jwtService;
    private final MemberService memberService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTService jwtService, MemberService memberService) {
        super(authenticationManager);
        this.jwtService = jwtService;
        this.memberService = memberService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        log.info("액세스 토큰 확인");
        String accessToken = jwtService.findTokenByRequest(request);

        if (accessToken == null) {
            log.info("액세스 토큰이 없습니다.");
            chain.doFilter(request, response);
            return;
        }

        if (jwtService.isExpired(accessToken)) {
            log.info("토큰 시간 만료");
            deleteAccessTokenCookie(response);
            chain.doFilter(request, response);
            return;
        }

        String email = jwtService.getEmailByAccessToken(accessToken);
        Member member = memberService.verifiedMemberByEmail(email);
        log.info("액세스 토큰이 정상입니다.");
        PrincipalDetails principalDetails = new PrincipalDetails(member);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private static void deleteAccessTokenCookie(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("accessToken", "")
                .maxAge(0)
                .sameSite("none")
                .path("/")
                .secure(true)
                .httpOnly(true).build();
        response.setHeader("Set-Cookie", cookie.toString());
    }
}
