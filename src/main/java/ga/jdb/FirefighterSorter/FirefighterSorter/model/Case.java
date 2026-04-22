package ga.jdb.FirefighterSorter.FirefighterSorter.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@Getter
@Setter
@Table(name = "cases")
@Entity
public class Case {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column
    private Double coverageInMeter;

    @Column
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double calculatedPriority;

    @Column
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double calculatedDistance;

    @Column
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double calculatedTime;

    @Column
    private String notes;

    @Column
    private LocalDateTime startedAt;

    @Column
    private LocalDateTime endedAt;

    @Column
    private Boolean peopleInside;

    @JoinColumn(name = "suggested_branch_id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToOne
    private Branch suggestedBranch;


    public enum CaseStatus{
        CREATED,
        ASSIGNED,
        PROCESSING,
        COMPLETED,
        CANCELED

    }

    @Column
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Enumerated(EnumType.STRING)
    private CaseStatus status;

    @OneToMany(mappedBy = "historyCase", fetch = FetchType.EAGER)
    private List<HistoryGroup> historyGroups;

    @OneToMany(mappedBy = "contactCase", fetch = FetchType.EAGER)
    private List<Contact> contacts;

    @ManyToOne
    @JoinColumn(name = "type_id")
    @JsonIgnore
    private Type type;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;
}
