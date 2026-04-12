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
@Table(name = "contact")
@Entity
public class Contact {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer priority;

    @Column
    private String value;

    @ManyToOne
    @JoinColumn(name = "case_id")
    @JsonIgnore
    private Case contactCase;

    @ManyToOne
    @JoinColumn(name = "contact_type")
    @JsonIgnore
    private ContactType contactType;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;
}
