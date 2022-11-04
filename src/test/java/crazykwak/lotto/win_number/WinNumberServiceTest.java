package crazykwak.lotto.win_number;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WinNumberServiceTest {

    Logger log = LoggerFactory.getLogger(WinNumberServiceTest.class);

    @Autowired
    WinNumberRepository winNumberRepository;

    @Autowired
    WinNumberService winNumberService;

    @Test
    void makeNumberCountTest() {

        //given
        List<WinNumber> all = winNumberRepository.findAll();
        int[] count = new int[45];

        for (WinNumber winNumber : all) {
            int winNumber1 = winNumber.getWinNumber1() - 1;
            int winNumber2 = winNumber.getWinNumber2() - 1;
            int winNumber3 = winNumber.getWinNumber3() - 1;
            int winNumber4 = winNumber.getWinNumber4() - 1;
            int winNumber5 = winNumber.getWinNumber5() - 1;
            int winNumber6 = winNumber.getWinNumber6() - 1;

            count[winNumber1]++;
            count[winNumber2]++;
            count[winNumber3]++;
            count[winNumber4]++;
            count[winNumber5]++;
            count[winNumber6]++;
        }
        int size = all.size();

        //when
        HistoryNumber historyNumber = winNumberService.makeHistoryNumber(size);
        int[] allNumbers = historyNumber.allNumbers;

        //then
        assertThat(count).isEqualTo(allNumbers);
        log.info("result = {}", Arrays.toString(allNumbers));

    }

}