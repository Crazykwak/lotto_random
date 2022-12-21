package crazykwak.lotto.member.controller;

import crazykwak.lotto.member.dto.MemberJoinDto;
import crazykwak.lotto.member.dto.MemberLoginDto;
import crazykwak.lotto.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String memberJoinView(Model model) {
        model.addAttribute(new MemberJoinDto());
        return "join";
    }

    @PostMapping("/join")
    public String memberJoin(MemberJoinDto memberJoinDto) {
        log.info("회원가입 시도");
        memberService.save(memberJoinDto);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String viewLogin(Model model) {
        model.addAttribute(new MemberLoginDto());
        return "login";
    }


}
