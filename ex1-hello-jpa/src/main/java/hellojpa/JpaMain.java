package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); //Persistence 객체가 META-INF설정 정보파일을 읽어서 EntityManagerFactory를 생성 , Application로딩시 한 번만 만듬

        EntityManager em = emf.createEntityManager(); //EntityManagerFactory에서 EntityManager 생성, DB와 일하는 아이

        EntityTransaction tx = em.getTransaction();
        tx.begin(); //트랜잭션 시작

        try{

            //비영속
            Member member = new Member();
            member.setId(2L);
            member.setName("HelloB");

            //영속 (쿼리는 아직 안날라감) -> Persistence Context안에 1차 캐시에 저장, 쓰기지연
            em.persist(member);

            Member findMember1 = em.find(Member.class, 2L); //1차 캐시를 먼저 조회한다
            Member findMember2 = em.find(Member.class, 2L);

            System.out.println("findMember.id =" + findMember1.getId());
            System.out.println("findMember.name =" + findMember1.getName());
            System.out.println("result = "+(findMember1 == findMember2)); //영속 엔티티의 동일성 보장

            findMember1.setName("HaHaHa"); //Dirty Checking(변경감지) -> Update 쿼리가 날라감
            //commit하는 시점에 flush()가 호출되는데,  엔티티와 스냅샷(DB에서 값을 읽어온 최초시점의 상태를 저장해둔것)을 비교하여 바뀐게 있으면 JPA가 Update쿼리를 쓰기지연 SQL 저장소에 생성하고 DB에 반영하고 commit이 됨

            //JPA의 모든 데이터 별경은 트랜잭션 안에서 실행
            //쿼리 날라가는 시점
            System.out.println("=====================================");
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
