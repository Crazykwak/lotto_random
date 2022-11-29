package crazykwak.lotto.win_number;

import crazykwak.lotto.win_number.dto.WinNumberDto;
import crazykwak.lotto.win_number.dto.WinNumberSearchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping
@Slf4j
public class WinNumberController {

    private final WinNumberRepository winNumberRepository;
    private final WinNumberService winNumberService;
    private final WinNumberMapper mapper;

    @GetMapping("/win-number/{drawNo}")
    public String getLottoNumber(@PathVariable long drawNo,
                                 Model model) {

        WinNumberDto winNumberDto = mapper.WinNumberToWinNumberDto(winNumberRepository.findByDrawNo(drawNo));

        model.addAttribute("result", winNumberDto);

        return "winNumber";
    }

    @GetMapping("/search")
    public String searchLottoCountCondition(Model model) {

        model.addAttribute(new WinNumberSearchDto());

        return "searchLotto";
    }

    @PostMapping("/search")
    public String searchLottoCount(@Valid WinNumberSearchDto winNumberSearchDto,
                                   BindingResult bindingResult,
                                   Model model) {
        if (bindingResult.hasErrors()) {
            // 에러 처리
            log.error("에러 발생! = {}", bindingResult.getGlobalError());
        }

        HistoryNumber historyNumber = winNumberService.makeHistoryNumber(winNumberSearchDto.getCount());
        model.addAttribute("result", historyNumber);

        return "winCount";
    }
}
