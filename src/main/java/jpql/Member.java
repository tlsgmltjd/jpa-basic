package jpql;

import jakarta.persistence.*;

// 네임드쿼리: 쿼리에 이름을 붙혀서 재사용할 수 있는 정적 쿼리이다. 어노테이션이나 xml에 선언 가능
// 애플리케이션 로딩 시점에 하이버네이트가 네임드 쿼리를 파싱해서 캐싱하고 있기 때문에
// 애플리케이션 로딩 시점에 오류를 잡아준다. <- 굉장한 이점

// spring data jpa의 @Query 어노테이션에 jpql 작성하면 JPA가 네임드 쿼리로 등록해준다!! 그래서 앱 로딩 시점에 오류를 잡아준다.
@NamedQuery(
        name = "Member.findByUsername",
        query = "SELECT m FROM Member m where m.username = :username"
)
@Entity
@Table(name = "jpql_member")
public class Member {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private int age;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Enumerated(EnumType.STRING)
    private MemberType type;

    public MemberType getType() {
        return type;
    }

    public void setType(MemberType type) {
        this.type = type;
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
