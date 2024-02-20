package nextstep.subway.member.application;

import nextstep.auth.AuthenticationException;
import nextstep.auth.application.AuthManager;
import nextstep.auth.application.TokenType;
import nextstep.subway.member.application.dto.TokenResponse;
import nextstep.subway.member.domain.Member;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    private final MemberService memberService;
    private final AuthManager authManager;

    public TokenService(MemberService memberService,
                        AuthManager authManager) {
        this.memberService = memberService;
        this.authManager = authManager;
    }

    public TokenResponse createGithubToken(String email,
                                           String password) {
        Member member = memberService.findMemberByEmail(email);
        if (!member.getPassword().equals(password)) {
            throw new AuthenticationException();
        }

        String token = authManager.createToken(member.getEmail(), TokenType.JWT);

        return new TokenResponse(token);
    }

    public TokenResponse createGithubToken(String code) {
        String accessToken = authManager.createToken(code, TokenType.GITHUB);

        String email = authManager.getPrincipal(accessToken, TokenType.GITHUB);
        memberService.findMemberByEmailNotExistSave(email);

        return new TokenResponse(accessToken);
    }
}
