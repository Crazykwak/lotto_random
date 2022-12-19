package crazykwak.lotto.member.service;

import crazykwak.lotto.member.entity.Member;
import crazykwak.lotto.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member verifiedMemberByEmail(String email) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow();
        // todo 예외 만들기

        return member;
    }
}
