package jpabook.jpashop.repository;


import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    // 상품 저장
    public void save(Item item){
        if(item.getId()==null){
            em.persist(item);
        }else{
            em.merge(item);
            //merge는 준영속 상태의 엔티티를 영속상태로 변경할 때 사용하는 기능, 하지만 모든 필드를 업데이트해서 값이 없으면 null로 갈아치워질수도 있어서 위험
        }
    }

    //상품 하나 조회
    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }



}
