package jpabook.jpashop.service;


import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
//단순히 itemRepository에 위임만 하는 정도
public class ItemService {

    private final ItemRepository itemRepository;

    //상품 저장
    @Transactional //저장을 위해 우선권 부여
    public void saveItem(Item item){
        itemRepository.save(item);
    }



    @Transactional//dirty check -> transaction commit 시점에서 dirty checking이 동작해 DB에 update SQL 실행
    public void updateItem(Long itemId, String name,int price,int stockQuantity){
//        Item findItem = itemRepository.findOne(itemId); //영속성 컨텍스트에서 엔티티를 조회
//        findItem.setPrice(param.getPrice()); //값 수정
//        findItem.setName(param.getName());
//        findItem.setStockQuantity(param.getStockQuantity());

        //최적화
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        Item item = itemRepository.findOne(itemId);
        return item;
    }

}
