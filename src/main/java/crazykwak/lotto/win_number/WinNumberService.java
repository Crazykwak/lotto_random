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

        int[] countNumber = new int[45];

        content.stream()
                .forEach(e -> {
                    int winNumber1 = e.getWinNumber1() - 1;
                    int winNumber2 = e.getWinNumber2() - 1;
                    int winNumber3 = e.getWinNumber3() - 1;
                    int winNumber4 = e.getWinNumber4() - 1;
                    int winNumber5 = e.getWinNumber5() - 1;
                    int winNumber6 = e.getWinNumber6() - 1;

                    countNumber[winNumber1]++;
                    countNumber[winNumber2]++;
                    countNumber[winNumber3]++;
                    countNumber[winNumber4]++;
                    countNumber[winNumber5]++;
                    countNumber[winNumber6]++;
                });

        int max = Arrays.stream(countNumber).max().getAsInt();
        int min = Arrays.stream(countNumber).min().getAsInt();

        int[] mostNumbers = Arrays.stream(countNumber).filter(e -> e == max).toArray();
        int[] leastNumbers = Arrays.stream(countNumber).filter(e -> e == min).toArray();

        return new HistoryNumber(mostNumbers, leastNumbers);
    }


}
