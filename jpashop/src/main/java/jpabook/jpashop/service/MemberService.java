package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service //스프링부트가 Component Scan해서 Bean으로 등록
@Transactional(readOnly = true)
@RequiredArgsConstructor //final이 있는 필드들만 가지고 생성자를 만들어줌 @Autowired와 같은 것
public class MemberService {


    private final MemberRepository memberRepository;

    //회원 가입
    @Transactional
    public Long join(Member member){

        validateDuplicateMember(member);//  중복 회원 검증
        memberRepository.save(member);
        return member.getId();

    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");

        }
    }

    //회원 전체 조회


    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    //회원 한명 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
