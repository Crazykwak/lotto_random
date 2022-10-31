package crazykwak.lotto.win_number;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WinNumber {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private long totalSellAmount;
    private long firstWinAmount;
    private LocalDate drawNoDate;
    private long drawNo;
    private int winNumber1;
    private int winNumber2;
    private int winNumber3;
    private int winNumber4;
    private int winNumber5;
    private int winNumber6;
    private int bonusNumber;

    @Builder

    public WinNumber(long totalSellAmount, long firstWinAmount, LocalDate drawNoDate, long drawNo, int winNumber1, int winNumber2, int winNumber3, int winNumber4, int winNumber5, int winNumber6, int bonusNumber) {
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
    }
}
