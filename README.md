# 자바 ORM 표준 JPA 프로그래밍
김영한님   [자바 ORM 표준 JPA 프로그래밍 - 기본편](https://www.inflearn.com/course/ORM-JPA-Basic/dashboard)

## JPA (Java Persist Api)
- 자바 ORM 기술 표준 (Object Relational Mapping)
- 객체 중심 개발
- 생산성, 유지보수성 ↑
- 패러다임의 불일치 해결
-> 별도의 sql 작성 없이 java 엔티티와 DB 테이블을 매핑해주는 기술

## 영속성 컨텍스트 
#### < 최적화 기능 >
- **1차 캐시** 
  -  Map 형태로 데이터를 캐싱함 
  -  Key : @Id , Value: 엔티티 인스턴스
- **동일성 보장**
  - == 비교
  -  같은 트랜잭션 안에서 같은 식별자에 대한 조회를 하는 경우 1차 캐시에있는 인스턴스에 접근하므로, 같은 엔티티를 반환한다 
- **트랜잭션을 지원하는 쓰기지연**
- **Dirty Checking**
- **지연로딩, 즉시딩**
