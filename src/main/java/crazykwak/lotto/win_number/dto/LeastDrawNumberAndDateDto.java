package crazykwak.lotto.win_number.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeastDrawNumberAndDateDto {

    private LocalDate drawNoDate;
    private long drawNo;


}
