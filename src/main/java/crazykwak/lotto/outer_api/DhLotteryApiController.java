package crazykwak.lotto.outer_api;

import crazykwak.lotto.win_number.dto.WinNumberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Controller
@RequiredArgsConstructor
@RequestMapping
@Slf4j
public class DhLotteryApiController {

    private final RestTemplate restTemplate;

    @GetMapping("/win-number/{drawNo}")
    public String getLottoNumber(@PathVariable String drawNo,
                                 Model model) {

        URI uri = UriComponentsBuilder.fromUriString("https://www.dhlottery.co.kr/common.do")
                .queryParam("method","getLottoNumber")
                .queryParam("drwNo", drawNo)
                .build()
                .toUri();

        WinNumberDto winNumberDto = restTemplate.getForObject(uri, WinNumberDto.class);

        model.addAttribute("result", winNumberDto);

        return "winNumber";
    }
}
