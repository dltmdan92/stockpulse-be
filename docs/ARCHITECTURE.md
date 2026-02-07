# Architecture

## Overview
Spring Boot 3.4.2 + Kotlin 기반 REST API 서버.
Phase 2로서 `stockpulse-fe` 프론트엔드에 실제 데이터를 제공한다.

## System Diagram
```
Browser → Next.js (FE) → HTTP/REST → Spring Boot (BE) → PostgreSQL
                                         ↓
                                   JWT Authentication
```

## Layer Architecture
```
Controller (REST API)
    ↓ DTO (Request/Response)
Service (Business Logic)
    ↓ Entity
Repository (Data Access)
    ↓ JPA/Hibernate
PostgreSQL (Database)
```

## Package Structure
```
com.stockpulse.api/
├── common/          # 공통 (BaseEntity, Exception, DTO)
├── config/          # 설정 (Security, Jackson, OpenAPI, DataInitializer)
├── auth/            # 인증 (User, JWT, Login/Register)
├── stock/           # 보유 종목 CRUD
├── trade/           # 거래 기록 CRUD (tags: TEXT[])
├── journal/         # 매매 일지 CRUD
├── portfolio/       # 포트폴리오 요약/분석 (computed)
├── analysis/        # 매매 통계/태그 성과 (computed)
├── market/          # 시장 데이터 (mock)
├── watchlist/       # 관심 종목 CRUD
└── user/            # 프로필/설정 CRUD
```

## Domain별 Layer 구성
```
{domain}/
├── entity/         # JPA Entity (BaseEntity 상속)
├── repository/     # Spring Data JPA Repository
├── dto/            # Request/Response DTO + toResponse() 확장 함수
├── service/        # Business Logic (@Transactional)
└── controller/     # REST Controller (@RestController)
```

## Authentication Flow
```
1. POST /api/auth/register → AuthService → User 생성 → JWT 발급
2. POST /api/auth/login → AuthService → 비밀번호 검증 → JWT 발급
3. Request + "Authorization: Bearer {token}"
   → JwtAuthenticationFilter → JwtTokenProvider.validateToken()
   → SecurityContext에 UserPrincipal 설정
4. @CurrentUser UserPrincipal → Controller에서 userId 접근
```

## Data Flow
```
Client Request
  → Controller (DTO validation, @CurrentUser)
    → Service (business logic, user scoping)
      → Repository (JPA query)
        → PostgreSQL
      ← Entity
    ← Entity.toResponse() → Response DTO
  ← JSON Response
```

## Security Architecture
- **Stateless**: JWT 기반, 세션 없음 (`SessionCreationPolicy.STATELESS`)
- **Filter Chain**: `JwtAuthenticationFilter` → Spring Security Filter Chain
- **CORS**: `localhost:3000`, `localhost:3001` 허용
- **Public Endpoints**: `/api/auth/**`, `/api/market/**`, Swagger UI
- **Protected Endpoints**: 그 외 모든 엔드포인트 (JWT 필수)

## Database
- **Migration**: Flyway (`V1` ~ `V7`)
- **Validation**: `ddl-auto: validate` (마이그레이션과 엔티티 일치 검증)
- **Connection Pool**: HikariCP (Spring Boot 기본)
- **Virtual Threads**: `spring.threads.virtual.enabled=true`
