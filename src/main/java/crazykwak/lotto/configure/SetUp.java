package crazykwak.lotto.configure;

import crazykwak.lotto.win_number.WinNumber;
import crazykwak.lotto.win_number.WinNumberMapper;
import crazykwak.lotto.win_number.WinNumberRepository;
import crazykwak.lotto.win_number.dto.WinNumberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SetUp {

    private final WinNumberRepository winNumberRepository;
    private final RestTemplate restTemplate;
    private final WinNumberMapper mapper;

    @PostConstruct
    public List<WinNumber> saveWinNumberForStart() {

        log.info("최신 당첨 회차 조회 시작");

        Pageable pageable = PageRequest.of(0, 1, Sort.Direction.DESC, "drawNo");
        Page<WinNumber> page = winNumberRepository.findLastIndex(pageable);
        List<WinNumber> content = page.getContent();
        List<WinNumber> total = new ArrayList<>();

        long drawNo = 0L;

        if (content.size() == 1) {
            drawNo = content.get(0).getDrawNo();
        }

        log.info("DB에 저장되어 있는 최신 회차 = {}", drawNo);

        while (drawNo <= 10000) {
            drawNo++;
            URI uri = UriComponentsBuilder.fromUriString("https://www.dhlottery.co.kr/common.do")
                    .queryParam("method","getLottoNumber")
                    .queryParam("drwNo", drawNo)
                    .build()
                    .toUri();

            WinNumberDto winNumberDto = restTemplate.getForObject(uri, WinNumberDto.class);

            log.info("새로 검색된 회차 = {}", winNumberDto.getDrawNo());

            if (winNumberDto.getReturnValue().equals("fail")) {
                break;
            }

            total.add(mapper.WinNumberDtoToWinNumber(winNumberDto));
        }

        log.info("검색 종료 DB 저장 시작");

        List<WinNumber> winNumbers = winNumberRepository.saveAll(total);

        log.info("저장된 회차 갯수 = {}", winNumbers.size());

        return winNumbers;
    }

    @Bean
    public LeastDrawNumberAndDateDto setLeastDrawNumberAndDateDto() {
        Pageable pageable = PageRequest.of(0, 1, Sort.Direction.DESC, "drawNo");
        Page<WinNumber> find = winNumberRepository.findLastIndex(pageable);
        List<WinNumber> content = find.getContent();
        WinNumber winNumber = content.get(0);
        return new LeastDrawNumberAndDateDto(winNumber.getDrawNoDate(), winNumber.getDrawNo());
    }

}
