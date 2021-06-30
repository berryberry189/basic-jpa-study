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

            Member member = new Member();
            member.setUsername("A");
            member.setRoleType(RoleType.USER);

            em.persist(member); // 이 시점에 db에 insert 한 이후에야 id를 알 수있다(@GeneratedValue auto_increment 의 경우만 다른 옵션은 commit 후에 insert)

            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
