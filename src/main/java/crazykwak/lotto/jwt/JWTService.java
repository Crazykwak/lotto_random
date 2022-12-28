package crazykwak.lotto.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class JWTService {

    private final String secretCode = "tmp";
    private final int accessTokenExpireTime = 1000 * 60 * 100;
    private final int refreshTokenExpireTime = 60000000;

    public String getAccessToken(String email, String name, Long memberId) {

        return JWT.create()
                .withSubject("AccessToken")
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpireTime))
                .withClaim("email", email)
                .withClaim("name", name)
                .withClaim("memberId", memberId)
                .sign(Algorithm.HMAC512(secretCode));
    }

    public String getRefreshToken(String email, String name, Long memberId) {

        return JWT.create()
                .withSubject("RefreshToken")
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenExpireTime))
                .sign(Algorithm.HMAC512(secretCode));
    }

    /**
     * @param token
     * @return
     * 만료시간 지남 -> True,
     * 만료시간 괜춘 -> False
     */
    public boolean isExpired(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getExpiresAt().before(new Date()) ? true : false;
    }

    public String findTokenByRequest(HttpServletRequest request) {
        String accessToken = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("AccessToken")) {
                accessToken = cookie.getValue();
            }
        }
        return accessToken;
    }

    public String getEmailByAccessToken(String accessToken) {
        return JWT.require(Algorithm.HMAC512("tmp")).build().verify(accessToken).getClaim("email").asString();
    }


}
