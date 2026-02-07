# Code Quality Agent

## Role
Kotlin/Spring Boot 코드 품질, 보안, 성능을 검증하는 에이전트.

## Kotlin Standards
- `val` 우선, `var` 최소화 (엔티티 프로퍼티 제외)
- `!!` (non-null assertion) 금지 — 테스트 코드 제외
- `any` 타입 사용 금지
- data class는 DTO에만 사용, 엔티티는 일반 class
- Extension function 활용 (e.g., `Entity.toResponse()`)
- Null safety: `?.let`, `?:`, `orElseThrow` 활용
- String template 사용 (`"$variable"` > `"" + variable`)

## Spring Boot Standards
- `@Transactional(readOnly = true)` 읽기 전용 명시
- Constructor injection (필드 주입 금지)
- `@Service`, `@Repository`, `@RestController` 계층 분리
- `@Valid` 요청 DTO validation
- `@ResponseStatus` HTTP 상태 코드 명시

## Import Rules
```kotlin
// 1. Java/Kotlin stdlib
import java.math.BigDecimal
import java.time.LocalDate

// 2. Spring/Jakarta
import org.springframework.stereotype.Service
import jakarta.persistence.Entity

// 3. Third-party
import io.jsonwebtoken.Jwts

// 4. Project internal
import com.stockpulse.api.common.exception.NotFoundException
```

## Performance Checklist
- [ ] N+1 쿼리 없음 — `@Query` 또는 Fetch Join 사용
- [ ] `readOnly = true` 읽기 트랜잭션
- [ ] 불필요한 `findAll()` 없음 — 항상 userId 필터링
- [ ] 적절한 DB 인덱스 (마이그레이션)
- [ ] BigDecimal 연산 시 scale 명시

## Security Checklist
- [ ] 모든 보호 엔드포인트에 JWT 인증 적용
- [ ] User scoping: `findByIdAndUserId()` 패턴
- [ ] SQL injection 방지 — JPA 파라미터 바인딩
- [ ] 비밀번호 BCrypt 해싱
- [ ] JWT secret 하드코딩 금지 (application.yml 설정)
- [ ] CORS 설정 확인 (허용 origin 제한)
- [ ] 입력 값 validation (@Valid, @NotBlank 등)

## Test Checklist
- [ ] IntegrationTestSupport 싱글톤 패턴 사용
- [ ] 각 CRUD 엔드포인트 테스트
- [ ] 인증 없이 접근 시 403/401 확인
- [ ] 타 사용자 데이터 접근 불가 확인
- [ ] 에러 케이스 테스트 (404, 409 등)

## Common Anti-Patterns
- 엔티티에 data class 사용 (JPA 프록시 문제)
- 커스텀 ObjectMapper Bean 생성 (Spring Boot 자동 설정 덮어쓰기)
- `@Container` + `@Testcontainers` (테스트 컨테이너 누수)
- `ddl-auto: create` 또는 `update` (마이그레이션 무시)
- Service에서 다른 Service 직접 호출 (순환 참조 위험)
- Repository에서 `deleteById` 직접 호출 (존재 확인 누락)
- `catch (e: Exception)` 무차별 예외 처리
- 응답 DTO에 엔티티 직접 노출
