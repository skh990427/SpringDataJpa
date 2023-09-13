package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findTop3HelloBy();

    @Query(name = "Member.findByUsername") //사실 얘는 주석처리해도 돌아감
    //레포지토리에 바로 쿼리를 칠수있는 기능이있는데 그 기능이 너무 강해서 네임드쿼리는 실무에서 잘 쓰지않음
    List<Member> findByUsername(@Param("username") String username);

    //실무에서 자주 사용됨
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUserNameList();

    //Dto 조회를 할때는 생성자 생성하듯이 경로를 다적어줘야함.. 조금 복잡하다
    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    List<Member> findListByUsername(String username); //컬렉션
    Member findMemberByUsername(String username); //단건
    Optional<Member> findOptionalByUsername(String username); //단건 Optional

    //카운트 쿼리는 조인을 할 필요가 없잖아?
    @Query(value = "select m from Member m left join m.team t",
            countQuery = "select count(m.username) from Member m")
    Page<Member> findByAge(int age, Pageable pageable);

    //clearAutomatically = true - 쿼리가 나가고 영속성컨텍스트 자동으로 클리어 해줌
    @Modifying(clearAutomatically = true) //무언가 값을 변경할때는 이 어노테이션을 꼭 넣어야함. 안넣으면 리스트나 singleResult 가져옴
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    //패치조인 = 연관된걸 셀렉트쿼리 한방에 다 긁어온다
    //스프링데이터JPA는 맨날 findBy뭐 해서 쓰는데 이러면 JPQL을 써야하잖아요
    //그래서 우리는 엔티티그래프(객체 그래프) 라는게 있다!
    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    //JPQL을 사용해서 엔티티 그래프를 사용할 수도있음 위에꺼랑 똑같음 그냥
    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    @EntityGraph(attributePaths = {"team"})
    List<Member> findEntityGraphByUsername(@Param("username") String username);
}