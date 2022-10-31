package crazykwak.lotto.win_number;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class WinNumberServiceTest {

    Logger log = (Logger) LoggerFactory.getLogger(WinNumberServiceTest.class);

    @Autowired
    WinNumberService winNumberService;

    @Autowired
    WinNumberRepository winNumberRepository;

    @Test
    void saveWinNumberForStart() {

        String baseDateString = "2022-10-29";
        LocalDate baseDate = LocalDate.parse(baseDateString);
        long baseDrawNo = 1039L;
        LocalDate today = LocalDate.now();

        long days = baseDate.until(today, ChronoUnit.DAYS);
        long latestDrawNo = baseDrawNo + (days / 7);

        List<WinNumber> beforeRepositorySize = winNumberRepository.findAll();

        int before = beforeRepositorySize.size();
        winNumberService.saveWinNumberForStart();

        List<WinNumber> afterRepositorySize = winNumberRepository.findAll();
        int after = afterRepositorySize.size();

        assertThat(after).isEqualTo(latestDrawNo);
        assertThat(after).isGreaterThan(before);

    }
}