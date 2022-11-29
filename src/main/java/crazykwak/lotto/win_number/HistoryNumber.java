package crazykwak.lotto.win_number;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryNumber {

    Map<Integer, Integer> mostNumbers;
    Map<Integer, Integer> minNumbers;
    int[] allNumbers;

}
