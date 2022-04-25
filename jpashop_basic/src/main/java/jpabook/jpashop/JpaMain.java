package jpabook.jpashop;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); //Persistence 객체가 META-INF설정 정보파일을 읽어서 EntityManagerFactory를 생성 , Application로딩 시 한 번만 만듬

        EntityManager em = emf.createEntityManager(); //EntityManagerFactory에서 EntityManager 생성, DB와 일하는 아이

        EntityTransaction tx = em.getTransaction();
        tx.begin(); //트랜잭션 시작

        try{

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close(); //EntityManager가 DB커넥션을 물고 있어서 사용후엔 꼭 닫아주어야함 , 한 트랙잭션안에서 생성되고 사라짐
        }

        //기능 CODE

        emf.close();

    }
}
