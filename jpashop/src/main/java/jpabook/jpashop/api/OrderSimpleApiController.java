package jpabook.jpashop.api;


import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


/**
 * xToOne(ManyToOne, OneToOne)
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    //문제점 1. Order Entity에 양방향 관계로 필드가 묶여있어서 JsonIgnore를 해주지 않으면 Jackson library가 무한루프에 빠진다.
    //문제점 2. Order Entity에 연관관계로 묶여있는 필드 중 fetch가 LAZY로 설정되어 있는 경우 DB에서 객체를 조회하지 않고 spring이 proxy 객체를 임의로 생성하여 할당한다.
    // 때문에 Jackson library가 ByteBuddy를 읽어올수 없어서 Type Error가 뜬다. -> Hibernate5Module를 설치해서 proxy객체일 경우 읽어오지 않도록 설정을 해야한다.
    //
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for(Order order : all){
            order.getMember().getName(); //Lazy 강제 초기화 (.getMember()까지는 proxy객체이지만 그 객체 안의 .getName()의 정보를 조회하는 순간 DB에 쿼리가 날라가면서 Lazy강제 초기화 된다) Hibernate5Module안쓰고 다른 해결방법임.
            order.getDelivery().getAddress(); //Lazy 강제초기화
        }
        return all;
    }


    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> orderV2(){
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        return orders.stream()
                .map(o-> new SimpleOrderDto(o))
                .collect(Collectors.toList());
    }

    @Data
    static class SimpleOrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        SimpleOrderDto(Order order) {
            orderId=order.getId();
            name=order.getMember().getName(); //Lazy 초기화 -> 영속성 컨텍스트가 Member id로 객체를 조회하는데 없어서 DB로 쿼리를 날려 최종적으로 member.name을 들고옴.
            orderDate = order.getOrderDate();
            orderStatus=order.getStatus();
            address = order.getDelivery().getAddress(); //Lazy 초기화 (Lazy는 기본적으로 영속성 컨텍스트를 조회함) 1 + N 쿼리 실행의 문제가 발생
        }
    }
}
