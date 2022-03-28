package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderSimpleQueryDto{

        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public OrderSimpleQueryDto(Long orderId,String name,LocalDateTime orderDate,OrderStatus orderStatus,Address address) {
            this.orderId=orderId;
            this.name=name; //Lazy 초기화 -> 영속성 컨텍스트가 Member id로 객체를 조회하는데 없어서 DB로 쿼리를 날려 최종적으로 member.name을 들고옴.
            this.orderDate =orderDate ;
            this.orderStatus=orderStatus;
            this.address =address; //Lazy 초기화 (Lazy는 기본적으로 영속성 컨텍스트를 조회함) 1 + N 쿼리 실행의 문제가 발생
        }
    }