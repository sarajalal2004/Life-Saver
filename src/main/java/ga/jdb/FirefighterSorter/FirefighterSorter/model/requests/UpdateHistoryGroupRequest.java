package ga.jdb.FirefighterSorter.FirefighterSorter.model.requests;

import lombok.Getter;

@Getter
public class UpdateHistoryGroupRequest {
    private Long caseId;
    private Long branchId;
    private Long userId;
}
