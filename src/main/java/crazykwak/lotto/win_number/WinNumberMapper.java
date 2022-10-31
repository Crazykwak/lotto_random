package crazykwak.lotto.win_number;

import crazykwak.lotto.win_number.dto.WinNumberDto;
import org.springframework.stereotype.Component;

@Component
public class WinNumberMapper {

    public WinNumber WinNumberDtoToWinNumber(WinNumberDto winNumberDto) {

        return WinNumber.builder()
                .totalSellAmount(winNumberDto.getTotalSellAmount())
                .firstWinAmount(winNumberDto.getFirstWinAmount())
                .drawNoDate(winNumberDto.getDrawNoDate())
                .drawNo(winNumberDto.getDrawNo())
                .winNumber1(winNumberDto.getWinNumber1())
                .winNumber2(winNumberDto.getWinNumber2())
                .winNumber3(winNumberDto.getWinNumber3())
                .winNumber4(winNumberDto.getWinNumber4())
                .winNumber5(winNumberDto.getWinNumber5())
                .winNumber6(winNumberDto.getWinNumber6())
                .bonusNumber(winNumberDto.getBonusNumber())
                .build();
    }

}
