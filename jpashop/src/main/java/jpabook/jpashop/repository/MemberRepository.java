package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository //Component Scan -> 자동으로 Bean 등록
@RequiredArgsConstructor// final필드 생성자 만들어 줌
public class MemberRepository {


    private final EntityManager em; //@RequiredArgsConstructor로 생성자 인젝션

    //멤버 저장
    public void save(Member member){
        em.persist(member);
    }

    //아이디로 멤버 한명 조회
    public Member findOne(Long id){
        return em.find(Member.class,id);
    }
     //멤버 모두 조회, 리스트로 리턴
    public List<Member> findAll(){
        return em.createQuery("select m from Member m",Member.class) //JPQL
               .getResultList();
    }

    //이름으로 멤버 찾기 , 리스트로 리턴
    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name=:name",Member.class)
                .setParameter("name",name)
                .getResultList();
    }


}
