package crazykwak.lotto.security.filter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import crazykwak.lotto.jwt.JWTService;
import crazykwak.lotto.member.dto.MemberLoginDto;
import crazykwak.lotto.member.entity.Member;
import crazykwak.lotto.member.repository.MemberRepository;
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
    private final MemberRepository memberRepository;
    private final JWTService jwtService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        log.info("로그인 시도");

        try {
            ObjectMapper om = new ObjectMapper();
            MemberLoginDto memberLoginDto = om.readValue(request.getInputStream(), MemberLoginDto.class);
            // todo 예외 뱉기 ㄱ
            Member member = memberRepository.findByEmail(memberLoginDto.getEmail())
                    .orElseThrow();

            if (!bCryptPasswordEncoder.matches(memberLoginDto.getPassword(), member.getPassword())) {
                log.error("비번이 틀림");
                throw new RuntimeException();
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(member.getEmail(), member.getPassword());
            return authenticationManager.authenticate(authenticationToken);

        } catch (StreamReadException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        log.info("인증 성공 엑세스 토큰 발급");

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        String accessToken = jwtService.getAccessToken(principalDetails.getUsername(), principalDetails.getNickname(), principalDetails.getMember().getId());
        ResponseCookie responseCookie = ResponseCookie.from("AccessToken", accessToken)
                .httpOnly(true)
                .sameSite("none")
                .path("/")
                .maxAge(60*100)
                .build();
    }
}
