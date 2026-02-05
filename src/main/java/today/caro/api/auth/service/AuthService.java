package today.caro.api.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.caro.api.auth.dto.*;
import today.caro.api.common.exception.BusinessException;
import today.caro.api.common.exception.ErrorCode;
import today.caro.api.config.JwtProperties;
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
    private final JwtProperties jwtProperties;
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

    @Transactional
    public LoginResponse login(LoginRequest request) {
        // 이메일로 회원 조회
        Member member = memberRepository.findByEmail(request.email())
            .orElseThrow(() -> new BusinessException(ErrorCode.LOGIN_FAILED));

        // 비밀번호 검증
        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new BusinessException(ErrorCode.LOGIN_FAILED);
        }

        // 토큰 발급
        String accessToken = jwtTokenProvider.createAccessToken(member.getId());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getId());

        refreshTokenService.save(member.getId(), refreshToken);

        return new LoginResponse(
            member.getId(),
            accessToken,
            refreshToken,
            jwtProperties.accessTokenExpirationSeconds(),
            jwtProperties.refreshTokenExpirationSeconds()
        );
    }

    public void logout(Long memberId) {
        refreshTokenService.delete(memberId);
    }

    public TokenReissueResponse reissue(TokenReissueRequest request) {
        String refreshToken = request.refreshToken();

        // 토큰 유효성 검증
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        Long memberId = jwtTokenProvider.getMemberIdFromToken(refreshToken);

        // 저장된 토큰과 일치하는지 검증
        if (!refreshTokenService.matches(memberId, refreshToken)) {
            throw new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        // 신규 토큰 발급
        String newAccessToken = jwtTokenProvider.createAccessToken(memberId);
        String newRefreshToken = jwtTokenProvider.createRefreshToken(memberId);

        // 신규 리프레시 토큰 저장
        refreshTokenService.save(memberId, newRefreshToken);

        return new TokenReissueResponse(
            newAccessToken,
            newRefreshToken,
            jwtProperties.accessTokenExpirationSeconds(),
            jwtProperties.refreshTokenExpirationSeconds()
        );
    }

    public EmailExistenceCheckResponse checkEmailExists(String email) {
        boolean exists = memberRepository.existsByEmail(email);
        return new EmailExistenceCheckResponse(exists);
    }

}
