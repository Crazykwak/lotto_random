package crazykwak.lotto.win_number.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
public class WinNumberSearchDto {

    @Min(message = "최소 1 이상이어야 합니다.", value = 1)
    private int count;


}
