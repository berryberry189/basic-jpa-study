package hellojpa;

import javax.persistence.*;
import java.util.Date;

@Entity
// @SequenceGenerator(name="member_seq_generator", sequenceName = "member_seq", initialValue = 1, allocationSize = 1) 1부터 시작 1씩 늘어남
// @TableGenerator(name = "MEMBER_SEQ_GENERATOR", table = "MY_SEQUENCES", pkColumnValue = "MEMBER_SEQ", allocationSize = 1)
public class Member {

    // ** 권장 식별자 전략 : Long형 + 대체키(시퀀스 등) + 키 생성전략(auto increment, sequence object 등) **
    @Id
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_generator")
    // @GeneratedValue(strategy = GenerationType.TABLE, generator = "MEMBER_SEQ_GENERATOR") // MY_SEQUENCES라는 시퀀스 테이블이 생성됨(운영에서 사용하는것은 비추)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name="name", insertable = true, updatable = false) // insert에는 반영되지만 update시에는 반영 안되도록 설정
    private String username;

    //@Column(nullable = false) // null 허용 여부
    @Column(columnDefinition = "integer(2) default 20") // DB에 컬럼정보를 직접 줄 때 사용
    private Integer age;
    
    @Enumerated(EnumType.STRING) // ORDINAL 쓰지말자!! (index 숫자로 저장되기 때문..)
    private RoleType roleType;
    
    @Temporal(TemporalType.TIMESTAMP) // 닐짜 타입
    private Date createdDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    
    @Lob // 큰 컨텐츠, 지정 할 수 있는 속성이 없음
    private String description;

    @Transient // 특정 필드를 컬럼에 매핑하지 않음(매핑 무시, DB에는 생성X )
    private int temp;

    /**
     create table Member (
         id bigint not null,
         age integer,
         createdDate timestamp,
         description clob,
         lastModifiedDate timestamp,
         roleType varchar(255),
         name varchar(255),
         primary key (id)
     )
     */


    public Member() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
