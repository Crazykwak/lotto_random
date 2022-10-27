package crazykwak.lotto.win_number;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
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

}
