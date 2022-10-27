package crazykwak.lotto.outer_api;

import crazykwak.lotto.win_number.dto.WinNumberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

@Controller
@RequiredArgsConstructor
public class DhLotteryApiController {

    private final RestTemplate restTemplate;

    public String getLottoNumber(long drawNo) {

        WinNumberDto winNumberDto = restTemplate.getForObject(String.valueOf(drawNo), WinNumberDto.class);

        return "test";
    }


}
