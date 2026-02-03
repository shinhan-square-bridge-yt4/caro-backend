package today.caro.api.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.caro.api.auth.dto.SignUpRequest;
import today.caro.api.auth.dto.SignUpResponse;
import today.caro.api.common.exception.BusinessException;
import today.caro.api.common.exception.ErrorCode;
import today.caro.api.member.entity.Member;
import today.caro.api.member.repository.MemberRepository;
import today.caro.api.security.jwt.JwtTokenProvider;
import today.caro.api.security.token.RefreshTokenService;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {
        // 이메일 중복 여부 검증
        if (memberRepository.existsByEmail(request.email())) {
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
        }

        // 회원 생성
        Member member = Member.builder()
            .email(request.email())
            .password(passwordEncoder.encode(request.password()))
            .name(request.name())
            .build();

        memberRepository.save(member);

        // 토큰 발급
        String accessToken = jwtTokenProvider.createAccessToken(member.getId());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getId());

        refreshTokenService.save(member.getId(), refreshToken);

        return new SignUpResponse(member.getId(), accessToken, refreshToken);
    }

}
