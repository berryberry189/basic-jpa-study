# 자바 ORM 표준 JPA 프로그래밍
김영한님   [자바 ORM 표준 JPA 프로그래밍 - 기본편](https://www.inflearn.com/course/ORM-JPA-Basic/dashboard)

## JPA (Java Persist Api)
- 자바 ORM 기술 표준 (Object Relational Mapping)
- 객체 중심 개발
- 생산성, 유지보수성 ↑
- 패러다임의 불일치 해결
-> 별도의 sql 작성 없이 java 엔티티와 DB 테이블을 매핑해주는 기술


## 영속성 컨텍스트 
- 엔티티를 영구 저장하는 환경
- 애플리케이션과 데이터베이스 사이에서 객체를 보관하는 가상의 데이터베이스 같은 역할을 한다
- 엔티티 매니저를 통해 엔티티를 저장하거나 조회하면 엔티티 메니저는 영속성 컨텍스트에 엔티티를 보관하고 관리한다.

#### < 최적화 기능 >
- **1차 캐시** 
  -  Map 형태로 데이터를 캐싱함 
  -  Key : @Id , Value: 엔티티 인스턴스
- **동일성 보장**
  - == 비교
  -  같은 트랜잭션 안에서 같은 식별자에 대한 조회를 하는 경우 1차 캐시에있는 인스턴스에 접근하므로, 같은 엔티티를 반환한다 
- **트랜잭션을 지원하는 쓰기지연**
  -  엔티티 매니저는 트랜잭션을 커밋하기 직전까지 내부 쿼리 저장소에 insert 쿼리를 모아두고, 트랜잭션이 커밋할 때 모아둔 insert 쿼리를 DB에 보낸다.
- **Dirty Checking 엔티티 수정 변경감지**
  - 영속 상태의 엔티티만 적용됨
  - 1. 트랜잭션 커밋 시 엔티티 매니저 내부에서 flush()가 호출된다
  - 2. 커밋되는 시점의 엔티티와 1차 캐시에 있는 스냅샷을 비교하여 변경된 부분이 있는지 찾는다
  - 3. 변경된 엔티티가 있는 경우에는 쓰기지연 저장소에 저장한다
  - 4. 쓰기지연 저장소의 sql을 DB로 보낸다.
- **지연로딩, 즉시로딩**
  - 

### 엔티티의 생명주기
- 비영속 (new, transient) :  영속성 컨텍스트와 관계가 없는 상태
- 영속   (managed)        :  영속성 컨텍스트에 저장된 상태 
  - EntityManager.persist(entity);
  - EntityManager.find(Member.class. "memberId");
- 준영속 (detachec)       :  영속성 컨텍스트에 저장되었다가 분리된 상태 
  - 특정 엔티티만 -> EntityManager.detatched(entity);
  - 통으로 초기화 -> EntityManager.clear();
  - 영속성 컨텍스트 종료 -> EntityManager.close();
- 삭제   (removed)        :  삭제된 상태 
  - EntityManager.remove(entity);






