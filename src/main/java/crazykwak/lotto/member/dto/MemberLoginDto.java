package crazykwak.lotto.member.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class MemberLoginDto {

    @Email
    private String email;

    @NotBlank
    private String password;

}
