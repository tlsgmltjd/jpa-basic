package hellojpa;

import jakarta.persistence.*;

@Entity
//@SequenceGenerator(
//        name = "member_seq_generator",
//        sequenceName = "member_seq")
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MY_SEQUENCES",
        allocationSize = 3)
public class Member2 {
    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_generator")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
    private Long id;
    @Column(name = "user_name", length = 10)
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Member2() {
    }

    public Member2(Long id, String name) {

        this.id = id;
        this.name = name;
    }
}
