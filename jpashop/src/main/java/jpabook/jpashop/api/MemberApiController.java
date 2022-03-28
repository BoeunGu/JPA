package jpabook.jpashop.api;


import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;


    /**
     * 회원 가입
     * V1 : 요청 값으로 Member 엔티티를 직접 받는다.
     * 문제점 : 엔티티에 프레젠테이션 계층을 위한 로직이 추가된다.
     * Entity에 API 검증을 위한 로직이 들어간다.(@NotEmpty 등등)
     * 한 엔티티에 각각의 API를 위한 요구사항을 담기가 어렵다.
     * 엔티티와 API 스펙이 변한다.
     * 결론 : API 요청스펙에 맞추어 별도의 DTO를 파라미터로 받는다.
     */
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody@Valid Member member){
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);

        //문제젬 : 클라이언트로부터 받는 값이 엔티티로 직접 매핑이 되어서 외부에 노출되기 쉽고, 유지 보수가 안좋다.
    }

    /**
     * 회원가입
     * V2 : 요청 값으로 Member 엔티티 대신에 별도의 DTO를 받는다.-> 로직 분리
     * @param request
     * @return
     */
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){

        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);

    }

    /**
     * 회원 수정
     */

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id")Long id,
                                               @RequestBody @Valid UpdateMemberRequest request){

    }

    @Data
    static class UpdateMemberRequest{
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse{
        private Long id;
        private String name;
    }

    @Data
    static class CreateMemberRequest{
        private String name;
    }


    @Data
    static class CreateMemberResponse{
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
