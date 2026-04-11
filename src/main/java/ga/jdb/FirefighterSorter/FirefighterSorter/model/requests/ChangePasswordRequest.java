package ga.jdb.FirefighterSorter.FirefighterSorter.model.requests;

import lombok.Getter;

@Getter
public class ChangePasswordRequest {
    private String oldPassword;
    private String newPassword;
}
