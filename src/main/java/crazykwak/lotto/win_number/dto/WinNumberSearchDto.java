package crazykwak.lotto.win_number.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
public class WinNumberSearchDto {

    @Positive
    private int count;


}
