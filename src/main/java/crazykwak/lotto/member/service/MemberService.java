package crazykwak.lotto.member.service;

import crazykwak.lotto.member.dto.MemberJoinDto;
import crazykwak.lotto.member.entity.Member;
import crazykwak.lotto.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Member verifiedMemberByEmail(String email) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow();
        // todo 예외 만들기

        return member;
    }

    public void save(MemberJoinDto memberJoinDto) {

        if (memberRepository.findByEmail(memberJoinDto.getEmail()).isPresent()) {
            throw new RuntimeException("Member Exist");
        }

        Member member = Member.builder()
                .email(memberJoinDto.getEmail())
                .name(memberJoinDto.getName())
                .password(bCryptPasswordEncoder.encode(memberJoinDto.getPassword()))
                .build();

        memberRepository.save(member);
    }

    public void save(Member member) {
        if (memberRepository.findByEmail(member.getEmail()).isPresent()) {
            throw new RuntimeException("Member Exist");
        }
        memberRepository.save(member);
    }
}
