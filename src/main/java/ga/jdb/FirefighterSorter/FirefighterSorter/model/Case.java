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
    private String Address;

    @Column
    private String coverageUOM;

    @Column
    private Double coverageQuantity;

    @Column
    private Double calculatedPriority;

    @Column
    private Double calculatedDistance;

    @Column
    private Double calculatedTime;

    @Column
    private String notes;

    @Column
    private LocalDateTime startedAt;

    @Column
    private LocalDateTime endedAt;

    @Column
    private Boolean peopleInside;

    @Column
    private String status;

    //TODO: relation to contact class
//    @Column
//    private List<Contact> contacts;

    //TODO: relation to type class
//    @Column
//    private Type type;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;
}
