package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import java.util.List;

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


}