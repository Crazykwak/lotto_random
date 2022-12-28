package crazykwak.lotto.security.filter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import crazykwak.lotto.jwt.JWTService;
import crazykwak.lotto.member.dto.MemberLoginDto;
import crazykwak.lotto.member.entity.Member;
import crazykwak.lotto.member.repository.MemberRepository;
import crazykwak.lotto.member.service.MemberService;
import crazykwak.lotto.security.principal.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final MemberService memberService;
    private final JWTService jwtService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        log.info("로그인 시도");

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        log.info("시도 이메일 = {}", email);
        // todo 예외 뱉기 ㄱ
        Member member = memberService.verifiedMemberByEmail(email);

        if (!bCryptPasswordEncoder.matches(password, member.getPassword())) {
            throw new RuntimeException("비번이 틀림");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        return authenticate;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("인증 성공 엑세스 토큰 발급");
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        String accessToken = jwtService.getAccessToken(principalDetails.getUsername(), principalDetails.getNickname(), principalDetails.getMember().getId());
        ResponseCookie responseCookie = ResponseCookie.from("AccessToken", accessToken)
                .httpOnly(true)
                .sameSite("none")
                .secure(true)
                .path("/")
                .maxAge(60*100)
                .build();
        response.addHeader("Set-Cookie", responseCookie.toString());
    }
}
