package ga.jdb.FirefighterSorter.FirefighterSorter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
@Table(name = "branches")
@Entity
public class Branch {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @OneToMany(mappedBy = "suggestedBranch", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Case> cases;

    @OneToMany(mappedBy = "branch", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Transport> transports;

    @OneToMany(mappedBy = "branch", fetch = FetchType.EAGER)
    private List<HistoryGroup> historyGroups;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;
}
