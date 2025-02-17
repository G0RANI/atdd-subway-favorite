package nextstep.subway.member.ui;

import nextstep.subway.auth.ui.AuthenticationPrincipal;
import nextstep.subway.member.application.MemberService;
import nextstep.subway.member.application.dto.MemberRequest;
import nextstep.subway.member.application.dto.MemberResponse;
import nextstep.subway.auth.domain.LoginMember;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members")
    public ResponseEntity<Void> createMember(@RequestBody MemberRequest request) {
        MemberResponse member = memberService.createMember(request);
        return ResponseEntity.created(URI.create("/members/" + member.getId())).build();
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<MemberResponse> findMember(@PathVariable Long id) {
        MemberResponse member = memberService.findMember(id);
        return ResponseEntity.ok().body(member);
    }

    @PutMapping("/members/{id}")
    public ResponseEntity<MemberResponse> updateMember(@AuthenticationPrincipal LoginMember loginMember,
                                                       @PathVariable Long id,
                                                       @RequestBody MemberRequest param) {
        memberService.updateMember(loginMember, id, param);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<MemberResponse> deleteMember(@AuthenticationPrincipal LoginMember loginMember,
                                                       @PathVariable Long id) {
        memberService.deleteMember(loginMember, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/members/me")
    public ResponseEntity<MemberResponse> findMemberOfMine(@AuthenticationPrincipal LoginMember loginMember) {
        MemberResponse memberResponse = memberService.findMe(loginMember);
        return ResponseEntity.ok().body(memberResponse);
    }
}
