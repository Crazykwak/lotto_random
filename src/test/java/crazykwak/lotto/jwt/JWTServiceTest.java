package crazykwak.lotto.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import crazykwak.lotto.member.entity.Member;
import crazykwak.lotto.member.repository.MemberRepository;
import crazykwak.lotto.member.service.MemberService;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.Date;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class JWTServiceTest {

    @Autowired
    JWTService jwtService;
    @Autowired
    MemberService memberService;

    Logger log = LoggerFactory.getLogger(JWTServiceTest.class);

    //given
    Member member = Member.builder()
            .email("test@email.com")
            .name("테스트")
            .password("1234").build();

    String hashCode = "tmp";

    @BeforeEach
    void setMember() {
        memberService.save(member);
    }

    @Test
    void getAccessToken() {

        Member findMember = memberService.verifiedMemberByEmail(member.getEmail());

        String accessToken = jwtService.getAccessToken(findMember.getEmail(), findMember.getName(), findMember.getId());
        log.info("액세스 토큰 발급 = {}", accessToken);

        //then
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(hashCode)).build().verify(accessToken);

        String email = decodedJWT.getClaim("email").asString();
        String name = decodedJWT.getClaim("name").asString();
        long memberId = decodedJWT.getClaim("memberId").asLong();

        log.info("디코딩한 memberId = {}", memberId);
        log.info("디코딩한 email = {}", email);
        log.info("디코딩한 name = {}", name);

        assertThat(member.getId()).isEqualTo(memberId);
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getName()).isEqualTo(name);

    }

    @Test
    void isExpired() {

        Member findMember = memberService.verifiedMemberByEmail(member.getEmail());
        String accessToken = jwtService.getAccessToken(findMember.getEmail(), findMember.getName(), findMember.getId());

        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(hashCode)).build().verify(accessToken);
        Date expiresAt = decodedJWT.getExpiresAt();
        Date now = new Date();
        log.info("현재시간 확인 = {}", now);
        log.info("만료시간 확인 = {}", expiresAt);

        long nowTime = now.getTime();
        long expiresAtTime = expiresAt.getTime();
        long time = expiresAtTime - nowTime;
        log.info("시간차 = {}", time);

        assertThat(time).isCloseTo(6000000L, Percentage.withPercentage(99));
    }
}