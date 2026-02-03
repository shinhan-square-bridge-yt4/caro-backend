package today.caro.api.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import today.caro.api.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

}
