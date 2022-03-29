package jpabook.jpashop.repository;


import jpabook.jpashop.api.OrderSimpleApiController;
import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long id){
        return em.find(Order.class,id);
    }

    public List<Order> findAllByString(OrderSearch orderSearch) {
//language=JPAQL
        String jpql = "select o From Order o join o.member m";
        boolean isFirstCondition = true;
//주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = :status";
        }
//회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }
        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
                .setMaxResults(1000); //최대 1000건
        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }
        return query.getResultList();
    }


    public List<Order> findAllWithMemberDelivery() {
        // Entity에서 연관관게는 다 Lazy처리 하고 필요한 Entity객체들만 fetch 조인해서 쿼리로 날려준다 -> 성능향상을 위해서 하는 것.
        return em.createQuery("select o from Order o"+
                "join fetch o.member m" +
                "join fetch o.delivery d",Order.class)
                .getResultList();

    }

    public List<Order> findAllWithItem(){
        //결론적으론 join으로 인해서 4개의 Order가 반환됨 (1:N의 경우 N만큼 데이터가 나오게됨)
        return em.createQuery("select distinct o from Order o"+
                        //distinct의 기능 1)DB에 distinct을 포함해서 날려준다.(DB에서는 row의 값들이 완전히 일치해야 중복제거를 해주어서 여기선 의미가 없다.)
                        // 2) 데이터들을 WAS에 들고온 뒤 한번 더 distinct를 처리해주어 root(Order에 해당)이 중복일 때 걸러서 컬렉션에 담아 준다.
                " join fetch o.member m"+
                " join fetch o.delivery d"+
                " join fetch o.orderItems oi"+
                " join fetch oi.item i",Order.class)
                .getResultList();
        )
    }

    public List<Order> findAllWithMemberDelivery(int offset, int limit) {
        return em.createQuery("select o from Order o"+
                        "join fetch o.member m" +
                        "join fetch o.delivery d",Order.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();



    }

//
//    public List<OrderSimpleQueryDto> findOrderDtos() {
//        return em.createQuery("select new jpabook.jpashop.repository.OrderSimpleQueryDto(o.id,m.name,o.orderdate,o.status,d.address)"+
//                " from Order o"+
//                "join o.member m"+
//                "join o.delivery d",OrderSimpleQueryDto.class)
//                .getResultList();
//    }
}
