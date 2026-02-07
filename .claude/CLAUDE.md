# StockPulse Backend - Claude Code Instructions

## Project Summary
투자 포트폴리오 관리 + 매매 일지 웹앱의 **백엔드 API 서버**.
Spring Boot (Kotlin) + PostgreSQL 기반으로 프론트엔드(`stockpulse-fe`)에 REST API를 제공한다.

## Quick Commands
```bash
docker compose up -d                                      # PostgreSQL 기동
./gradlew bootRun --args='--spring.profiles.active=dev'   # 개발 서버 기동
./gradlew test                                            # 테스트 실행
./gradlew compileKotlin                                   # 컴파일 검증
```

## Documentation Map
| 문서 | 경로 | 용도 |
|------|------|------|
| AI Rules | `docs/AI_RULES.md` | AI 어시스턴트 공통 규칙 |
| Architecture | `docs/ARCHITECTURE.md` | 시스템 아키텍처 |
| Conventions | `docs/CONVENTIONS.md` | 코딩 컨벤션 |
| API Design | `docs/API_DESIGN.md` | API 설계 가이드라인 |
| Domain | `docs/DOMAIN.md` | 투자 도메인 가이드 |
| Feature Spec | `docs/FEATURE_SPEC.md` | API 엔드포인트 상세 스펙 |
| Project Spec | `docs/PROJECT_SPEC.md` | 프로젝트 개요 |
| Roadmap | `docs/ROADMAP.md` | 개발 로드맵 |
| Setup | `docs/SETUP.md` | 환경 설정 가이드 |
| Tech Stack | `docs/TECH_STACK.md` | 기술 스택 상세 |

## Tech Stack
- **Language**: Kotlin 2.1.10 (K2 compiler)
- **Framework**: Spring Boot 3.4.2
- **Build**: Gradle 8.12 (Kotlin DSL)
- **JDK**: Java 21 (Virtual Threads)
- **DB**: PostgreSQL 16 + Flyway migration
- **Auth**: Spring Security 6 + JJWT 0.12.6
- **ORM**: Spring Data JPA + Hibernate 6
- **API Docs**: SpringDoc OpenAPI 2.8.x
- **Test**: JUnit 5 + Testcontainers

## Key Files to Check Before Editing
- `src/main/kotlin/.../common/entity/BaseEntity.kt` — 모든 엔티티의 베이스
- `src/main/kotlin/.../common/exception/Exceptions.kt` — 예외 계층 구조
- `src/main/kotlin/.../config/SecurityConfig.kt` — JWT 보안 설정
- `src/main/kotlin/.../auth/security/JwtTokenProvider.kt` — 토큰 생성/검증
- `src/main/resources/application.yml` — 앱 설정
- `src/main/resources/db/migration/` — Flyway 마이그레이션 SQL
- `stockpulse-fe/src/types/index.ts` — FE 타입 정의 (API 응답 shape 원본)

## Essential Rules

### Kotlin
- Kotlin 코드 스타일: Spring Boot 공식 가이드 준수
- `var` 최소화, `val` 우선 사용
- data class는 DTO에만, 엔티티는 일반 class
- Null safety 적극 활용, `!!` 금지 (테스트 제외)

### Architecture
- 패키지 구조: `{domain}/{layer}` (e.g., `stock/entity`, `stock/service`)
- Entity → Repository → Service → Controller 레이어 분리
- `@Transactional(readOnly = true)` 읽기 전용 트랜잭션 명시
- 모든 엔드포인트는 user scoping (JWT의 userId로 데이터 격리)

### Database
- Flyway 마이그레이션만 사용, `ddl-auto: validate`
- 새 테이블 추가 시 `V{N}__description.sql` 파일 생성
- BigDecimal로 금액 저장, API 응답은 Double 변환

### API
- REST 컨벤션: GET(목록/단건), POST(생성), PUT(수정), DELETE(삭제)
- 응답 shape은 FE `types/index.ts`와 정확히 일치
- camelCase JSON (Spring Boot 기본)
- RFC 9457 ProblemDetail 에러 응답

### Test
- Testcontainers 싱글톤 패턴 사용 (`IntegrationTestSupport.kt`)
- `@Container` + `@Testcontainers` 대신 companion object에서 직접 `start()`
- `spring.docker.compose.enabled: false` (test profile)

## Git Commit Convention
```
feat: 새 기능 추가
fix: 버그 수정
refactor: 리팩토링
docs: 문서 수정
chore: 빌드, 설정 변경
test: 테스트 추가/수정
```

## Custom Agents
- `backend-dev` — 백엔드 개발 전문 에이전트
- `api-reviewer` — API 설계/리뷰 에이전트
- `stock-domain` — 투자 도메인 전문 에이전트
- `code-quality` — 코드 품질 검증 에이전트
