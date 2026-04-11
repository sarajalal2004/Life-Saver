package ga.jdb.FirefighterSorter.FirefighterSorter.model.requests;

import ga.jdb.FirefighterSorter.FirefighterSorter.model.User;
import lombok.Getter;

@Getter
public class UpdateRoleRequest {
    private String email;
    private User.Role role;
}
