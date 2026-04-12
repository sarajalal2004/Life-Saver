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

@Table(name = "types")
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Type {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Integer priority;

    @OneToMany(mappedBy = "type", fetch = FetchType.EAGER)
    private List<Case> cases;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;
}
