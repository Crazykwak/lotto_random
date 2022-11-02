package crazykwak.lotto.win_number;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryNumber {

    int[] mostNumbers;
    int[] leastNumbers;

}
