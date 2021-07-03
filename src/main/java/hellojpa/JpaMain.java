package hellojpa;

import javax.persistence.*;
import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            // 팀 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            // 회원 저장
            Member member = new Member();
            member.setName("member1");
            // member.setTeamId(team.getId());
            member.changeTeam(team); // 단방향 연관관계 설정, 참조 저장
            em.persist(member);

            // team.addMember(member); 연관관계 편의메소드는 둘 중 하나만

            em.flush();
            em.clear();

            // 조회
            Member findMember = em.find(Member.class, member.getId());

            /**
            List<Member> members = findMember.getTeam().getMembers();
            for(Member m : members){
                System.out.println("m = " + m.getName());
            }
             */

            Team findTeam = em.find(Team.class, team.getId());
            List<Member> members = findTeam.getMembers();
            for(Member m : members){
                System.out.println("m = " + m.getName());
            }

            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
