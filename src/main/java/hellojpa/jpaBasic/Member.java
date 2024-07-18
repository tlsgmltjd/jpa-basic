//package hellojpa.jpaBasic;
//
//import jakarta.persistence.*;
//
//import java.util.Date;
//
//@Entity
//@Table(name = "test")
//public class MemberJpa {
//    @Id
//    private Long id;
//    @Column(name = "name")
//    private String username;
//    private Integer age;
//    @Enumerated(EnumType.STRING)
//    @Column(columnDefinition = "VARCHAR(4)")
//    private RoleType roleType;
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdDate;
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date lastModifiedDate;
//    @Lob // varchar를 넘는 크키가 큰 데이터 Lagr Object
//    private String description;
//
//    @Transient
//    private int temp;
//
//}
