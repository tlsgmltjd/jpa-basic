package jpabook;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Team {
    @Id @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    @Column(name = "team_name")
    private String teamName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Team() {
    }
}
