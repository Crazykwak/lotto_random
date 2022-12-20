package crazykwak.lotto.member.dto;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class MemberJoinDto {

    @Email
    private String email;
    private String name;
    private String password;

}
