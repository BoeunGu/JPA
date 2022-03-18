package jpabook.jpashop.domain;


import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable //값 변경이 불가능하게 설계해야함
@Getter //@Setter 막음
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() { // 프록시 없지만 public은 최대한 방지(protected까지 허용), 함부러 생성하지말자
    }


    //@Setter 대용
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
