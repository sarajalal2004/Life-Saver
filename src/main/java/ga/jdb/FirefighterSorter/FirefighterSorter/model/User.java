package ga.jdb.FirefighterSorter.FirefighterSorter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
@Entity
@ToString(exclude = {"password", "userProfile"})
public class User {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public enum Role {
        ADMIN,
        MANAGER,
        Firefighter
    }

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Status{
        Active,
        Inactive
    }

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    private Boolean verified;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id")
    private UserProfile userProfile;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<HistoryGroup> historyGroups;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;

    @JsonIgnore
    public String getPassword() {
        return password;
    }
}
