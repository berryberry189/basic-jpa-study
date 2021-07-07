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

### < 최적화 기능 >
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
  - 즉시 조인해서 가져오는것 (즉시로딩) / 사용할 때 가져오는 것 (지연로딩)
  - Member 엔티티와 Team 엔티티가 연관관계에 있을 때 Member를 조회하는 경우,
  - FetchType.EAGER 즉시로딩은 한방쿼리로 member와 team을 조인해서 조회한다
  - FetchType.LAZY 지연로딩은 Member만 조회하며 member.getTeam().getName()과 같이 Team 엔티티에 있는 무엇인가를 실제로 사용할 때 초기화가 실행되며, Team을 프록시 객체로 가져온다 (조회한다)
  - @ManyToOne, @OneToOne은 기본이 즉시로딩
  - @ManyToMany, @OneToMany는 기본이 지연로딩
  - **가급적 지연로딩만 사용하는 것이 좋다**

## 엔티티의 생명주기
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

## 단방향 / 양방향 연관관계
- 객체(참조)와 테이블(외래키)는 패러다임의 차이가 있음
- 가급적 단방향 연관관계를 갖는것이 좋다
- 1:N 일 경우 **N이 연관관계의 주인**이 되며, 주인만이 **외래키**를 관리한다 ( @JoinColumn )
- **주인이 아닌쪽은 읽기만 가능하다**
- **mappedBy 속성은 주인이 아닌쪽에서 사용**하며, 해당 속성으로 주인을 지정한다.


## 프록시
- em.getReference() ;
    - DB 조회를 미루는 가짜(프록시) 엔티티 객체 조회
    - 프록시 객체는 실제 객체의 참조(target)를 보관하며, 프록시 객체를 호출하면 프록시 객체는 실제 객체의 메소드를 호출한다

**< 프록시 객체 >**

- 처음 사용할때 한번만 초기화 한다
- 프록시 객체가 실제 객체로 바뀌는 것이 아니며, 실제 객체에 접근할 수 있게 되는 것이다.
- 프록시 객체는 원본 객체를 상속 받는다
    - 타입 체크 ⇒ == (X) , **instanceof (O)**
- 영속성 컨텍스트에 찾는 엔티티가 이미 있으면, em.getReference();를 호출해도 실제 엔티티를 반환한다
- ⭐ 영속성 컨텍스트의 도움을 받을 수 없는 **준영속 상태일때, 프록시를 초기화 하면 문제가 발생한다**
- 프록시 초기화 여부 확인 : emf.getPersistenceUnitUtil().isLoaded(referenceMember);
- 프록시 클래스 확인 : entity.getClass().getName();
- 프록시 강제 초기화 : org.hibernate.Hibernate.initialize(entity);  -> JPA 표준은 강제 초기화 없음


## CASCADE 영속성 전이

- 특정 엔티티를 영속상태로 만들때, 특정 엔티티도 함께 영속 상태로 만들고 싶은 경우
- 라이프 사이클이 동일할 때 / 단일 엔티티에 종속적일 때 사용

```java
@OneToMany(mappedBy="parent", **cascade=CascadeType.ALL**)
```

- 영속성 전이는 연관관계를 매핑하는 것과 아무 관련이 없다.
- CASCADE의 종류 ( ALL: 모두 적용, PERSIST: 영속, REMOVE: 삭제 )

## 고아 객체

- **orphanRemoval = true ( or false )**
- 고아 객체 제거: 부모 엔티티와 연관관계가 끊어진 자식 엔티티
를 자동으로 삭제
- 참조할 곳이 하나일 때 사용해야함
- 특정 엔티티가 개인 소유할 때 사용
- @OneToOne, @OneToMany만 가능
- 부모 객체를 제거하면 자식은 고아가 됨 → 고아 객체 기능을 활성화 하면, 부모를 제거할 때 자식도 함께 제거됨 ( like : CascadeType.REMOVE )

## 영속성 전이 + 고아 객체 , 생명주의

- **CascadeType.ALL  +  orphanRemoval = true**
- 부모 엔티티를 통해 자식의 생명주기를 관리할 수 있다.
- DDD의 Aggregate Root개념을 구현할 때 유용
