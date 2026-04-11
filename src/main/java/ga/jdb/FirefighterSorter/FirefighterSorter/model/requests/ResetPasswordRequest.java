package ga.jdb.FirefighterSorter.FirefighterSorter.model.requests;

import lombok.Getter;

@Getter
public class ResetPasswordRequest {
    private String token;
    private String newPassword;
}
