package jpabook;

import jakarta.persistence.Embeddable;

// 임배다드 터압과 같은 값 타입은 여러 엔티티에서 공유하면 위험하다
// 값 타입 객체의 인스턴스를 공유하는 것은 예상치 못한 사이드 이펙트가 발생할 수 있다.
// 그렇지만 래퍼런스를 공유하는 행위를 막을 순 없으므로 값 변경을 막아 해결할 수 있다.
// 불변 객체로 만들어야함 -> 생성 시점 이후 변경되지 않는 객체

@Embeddable
public class Address {
    private String city;
    private String street;
    private String zipcode;

    public String getCity() {
        return city;
    }


    public String getStreet() {
        return street;
    }


    public String getZipcode() {
        return zipcode;
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public Address() {
    }
}
