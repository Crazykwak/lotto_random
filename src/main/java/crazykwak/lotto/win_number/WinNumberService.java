package crazykwak.lotto.win_number;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class WinNumberService {

    private final WinNumberRepository winNumberRepository;
    private int[] countNumbers = new int[45];
    private Map<Integer, Integer> mostNumbers = new TreeMap<>();
    private Map<Integer, Integer> minNumbers = new TreeMap<>();


    public HistoryNumber makeHistoryNumber(int count) {

        List<WinNumber> content = setContent(count);
        setCountNumbers(content, countNumbers);
        setTreeMaps();

        return new HistoryNumber(mostNumbers, minNumbers, countNumbers);
    }

    private List<WinNumber> setContent(int count) {
        Pageable pageable = PageRequest.of(0, count, Sort.Direction.DESC, "drawNo");
        Page<WinNumber> find = winNumberRepository.findLastIndex(pageable);
        List<WinNumber> content = find.getContent();
        return content;
    }

    private void setTreeMaps() {
        int max = Arrays.stream(countNumbers).max().getAsInt();
        int min = Arrays.stream(countNumbers).min().getAsInt();
        for (int i = 0; i < countNumbers.length; i++) {
            int countNumber = countNumbers[i];
            if (countNumber == max) {
                mostNumbers.put(i + 1, countNumber);
            } else if (countNumber == min) {
                minNumbers.put(i + 1, countNumber);
            }
        }
    }

    private static void setCountNumbers(List<WinNumber> content, int[] countNumbers) {
        content.stream()
                .forEach(e -> {
                    int winNumber1 = e.getWinNumber1() - 1;
                    int winNumber2 = e.getWinNumber2() - 1;
                    int winNumber3 = e.getWinNumber3() - 1;
                    int winNumber4 = e.getWinNumber4() - 1;
                    int winNumber5 = e.getWinNumber5() - 1;
                    int winNumber6 = e.getWinNumber6() - 1;

                    countNumbers[winNumber1]++;
                    countNumbers[winNumber2]++;
                    countNumbers[winNumber3]++;
                    countNumbers[winNumber4]++;
                    countNumbers[winNumber5]++;
                    countNumbers[winNumber6]++;

                });
    }


}
