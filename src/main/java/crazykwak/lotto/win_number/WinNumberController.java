package crazykwak.lotto.win_number;

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
public class WinNumberController {

    private final WinNumberRepository winNumberRepository;
    private final WinNumberMapper mapper;

    @GetMapping("/win-number/{drawNo}")
    public String getLottoNumber(@PathVariable long drawNo,
                                 Model model) {

        WinNumberDto winNumberDto = mapper.WinNumberToWinNumberDto(winNumberRepository.findByDrawNo(drawNo));

        model.addAttribute("result", winNumberDto);

        return "winNumber";
    }
}
