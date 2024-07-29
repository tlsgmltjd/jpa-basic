package jpql;

import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "jpql_team")
public class Team {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
//    @BatchSize(size = 100)
    // 글로벌 설정으로 뺌
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    public List<Member> getMembers() {
        return members;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id")
    private Test test;

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
