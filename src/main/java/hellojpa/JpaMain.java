package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Arrays;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            // 등록
           /* Member member = new Member();
            member.setId(1L);
            member.setName("HelloA");
            em.persist(member);*/

            // 수정
            /*Member findMember = em.find(Member.class, 1L);
            findMember.setName("HelloJpa"); // em.persist(member); 안해도 됨
            System.out.println("findMember = " + findMember.getName());*/

            // 목록
            List<Member> result = em.createQuery("select m from Member as m", Member.class).getResultList();

            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
