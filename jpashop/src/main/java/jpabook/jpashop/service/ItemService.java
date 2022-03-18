package jpabook.jpashop.service;


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

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        Item item = itemRepository.findOne(itemId);
        return item;
    }

}
