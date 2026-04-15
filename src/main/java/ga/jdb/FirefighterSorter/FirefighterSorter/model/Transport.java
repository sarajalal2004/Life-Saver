package ga.jdb.FirefighterSorter.FirefighterSorter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "transports")
@Entity
public class Transport {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String type;

    @Column
    private String description;

    @Column
    private String registerNumber;

    @Column
    private Boolean reserved;

    @Column
    private Double coverageInMeter;

    @Column
    private Double speed;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "branch_Id")
    private Branch branch;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;
}
