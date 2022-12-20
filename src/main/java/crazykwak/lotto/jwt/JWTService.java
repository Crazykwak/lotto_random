package crazykwak.lotto.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

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

    public boolean isExpired(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getExpiresAt().before(new Date()) ? true : false;
    }


}
