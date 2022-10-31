package crazykwak.lotto.win_number.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class WinNumberDto {

    @JsonProperty("totSellamnt")
    private long totalSellAmount;

    @JsonProperty("firstWinamnt")
    private long firstWinAmount;

    @JsonProperty("drwNoDate")
    private LocalDate drawNoDate;

    @JsonProperty("drwNo")
    private long drawNo;

    @JsonProperty("drwtNo1")
    private int winNumber1;

    @JsonProperty("drwtNo2")
    private int winNumber2;

    @JsonProperty("drwtNo3")
    private int winNumber3;

    @JsonProperty("drwtNo4")
    private int winNumber4;

    @JsonProperty("drwtNo5")
    private int winNumber5;

    @JsonProperty("drwtNo6")
    private int winNumber6;

    @JsonProperty("bnusNo")
    private int bonusNumber;

    private String returnValue;

    @Builder
    public WinNumberDto(long totalSellAmount, long firstWinAmount, LocalDate drawNoDate, long drawNo, int winNumber1, int winNumber2, int winNumber3, int winNumber4, int winNumber5, int winNumber6, int bonusNumber, String returnValue) {
        this.totalSellAmount = totalSellAmount;
        this.firstWinAmount = firstWinAmount;
        this.drawNoDate = drawNoDate;
        this.drawNo = drawNo;
        this.winNumber1 = winNumber1;
        this.winNumber2 = winNumber2;
        this.winNumber3 = winNumber3;
        this.winNumber4 = winNumber4;
        this.winNumber5 = winNumber5;
        this.winNumber6 = winNumber6;
        this.bonusNumber = bonusNumber;
        this.returnValue = returnValue;
    }
}
