package crazykwak.lotto.win_number;

import crazykwak.lotto.win_number.dto.WinNumberDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WinNumberRepositoryTest {

    Logger log = LoggerFactory.getLogger(WinNumberRepositoryTest.class);

    @Autowired
    WinNumberRepository winNumberRepository;

    @Test
    void findByDrawNo() {

        //given
        WinNumber winNumber = WinNumber.builder()
                .totalSellAmount(118628811000L)
                .firstWinAmount(1246819620L)
                .drawNoDate(LocalDate.parse("2022-01-29", DateTimeFormatter.ISO_DATE))
                .drawNo(1000)
                .winNumber1(2)
                .winNumber2(8)
                .winNumber3(19)
                .winNumber4(22)
                .winNumber5(32)
                .winNumber6(42)
                .bonusNumber(39).build();
        long drawNo = winNumber.getDrawNo();

        // when
        WinNumber findWinNumber = winNumberRepository.findByDrawNo(drawNo);

        //then
        assertThat(findWinNumber.getWinNumber1()).isEqualTo(winNumber.getWinNumber1());
        assertThat(findWinNumber.getWinNumber2()).isEqualTo(winNumber.getWinNumber2());
        assertThat(findWinNumber.getWinNumber3()).isEqualTo(winNumber.getWinNumber3());
        assertThat(findWinNumber.getWinNumber4()).isEqualTo(winNumber.getWinNumber4());
        assertThat(findWinNumber.getWinNumber5()).isEqualTo(winNumber.getWinNumber5());
        assertThat(findWinNumber.getWinNumber6()).isEqualTo(winNumber.getWinNumber6());
        assertThat(findWinNumber.getDrawNo()).isEqualTo(winNumber.getDrawNo());

        log.info("1000회 정보 = {}", winNumber);
        log.info("검색 된 친구 = {}",findWinNumber);

    }
}