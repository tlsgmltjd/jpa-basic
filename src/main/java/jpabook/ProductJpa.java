package jpabook;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ProductJpa {
    @Id @GeneratedValue
    private Long id;
    private String name;
//    @ManyToMany(mappedBy = "products")
//    private List<MemberJpa> members = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<MemberProduct> memberProducts = new ArrayList<>();
}
