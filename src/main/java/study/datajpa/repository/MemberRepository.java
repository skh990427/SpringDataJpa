package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findTop3HelloBy();

    @Query(name = "Member.findByUsername") //사실 얘는 주석처리해도 돌아감
    //레포지토리에 바로 쿼리를 칠수있는 기능이있는데 그 기능이 너무 강해서 네임드쿼리는 실무에서 잘 쓰지않음
    List<Member> findByUsername(@Param("username") String username);
}