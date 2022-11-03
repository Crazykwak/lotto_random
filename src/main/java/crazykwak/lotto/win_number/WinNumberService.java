package crazykwak.lotto.win_number;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class WinNumberService {

    private final WinNumberRepository winNumberRepository;

    public HistoryNumber makeHistoryNumber(int count) {

        Pageable pageable = PageRequest.of(0, count, Sort.Direction.DESC, "drawNo");

        Page<WinNumber> find = winNumberRepository.findLastIndex(pageable);
        List<WinNumber> content = find.getContent();

        int[] countNumbers = new int[45];

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

        int max = Arrays.stream(countNumbers).max().getAsInt();
        int min = Arrays.stream(countNumbers).min().getAsInt();

        int[] mostNumbers = Arrays.stream(countNumbers).filter(e -> e == max).toArray();
        int[] leastNumbers = Arrays.stream(countNumbers).filter(e -> e == min).toArray();

        return new HistoryNumber(mostNumbers, leastNumbers, countNumbers);
    }


}
