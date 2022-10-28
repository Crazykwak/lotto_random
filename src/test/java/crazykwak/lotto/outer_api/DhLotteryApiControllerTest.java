package crazykwak.lotto.outer_api;

import crazykwak.lotto.win_number.dto.WinNumberDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class DhLotteryApiControllerTest {

    @Autowired
    DhLotteryApiController dhLotteryApiController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(dhLotteryApiController).build();
    }

    @Test
    void getLottoNumber() throws Exception {

        //given
        WinNumberDto winNumberDto = WinNumberDto.builder()
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

        ResultActions actions = mockMvc.perform(
                get("/win-number/1000")
        );

        actions
                .andExpect(status().isOk())
                .andExpect(model().attribute("result", winNumberDto));
    }
}