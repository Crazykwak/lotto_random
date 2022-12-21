package crazykwak.lotto.security.principal;

import crazykwak.lotto.member.entity.Member;
import crazykwak.lotto.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberService memberService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("유저디테일 서비스 발동 = {}", email);
        Member member = memberService.verifiedMemberByEmail(email);
        return new PrincipalDetails(member);
    }
}
