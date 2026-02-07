# Setup & Development Guide

## Prerequisites
- **JDK**: 21+ (recommend: Eclipse Temurin, Amazon Corretto)
- **Docker**: Docker Desktop 또는 OrbStack
- **IDE**: IntelliJ IDEA (recommend) 또는 VS Code + Kotlin plugin

## Quick Start

### 1. PostgreSQL 기동
```bash
cd stockpulse-be
docker compose up -d
```

### 2. 앱 기동 (dev 프로필)
```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

### 3. 확인
- Swagger UI: http://localhost:8080/swagger-ui.html
- 데모 계정: `demo@stockpulse.com` / `demo1234`

## Available Commands

| Command | Description |
|---------|-------------|
| `./gradlew bootRun` | 앱 기동 (기본 프로필) |
| `./gradlew bootRun --args='--spring.profiles.active=dev'` | 앱 기동 (dev 프로필, 시드 데이터) |
| `./gradlew compileKotlin` | 컴파일 검증 |
| `./gradlew test` | 테스트 실행 (Testcontainers) |
| `./gradlew clean build` | 클린 빌드 |
| `docker compose up -d` | PostgreSQL 기동 |
| `docker compose down` | PostgreSQL 중지 |
| `docker compose down -v` | PostgreSQL 중지 + 데이터 삭제 |

## Project Structure
```
stockpulse-be/
├── build.gradle.kts          # Gradle 빌드 설정
├── settings.gradle.kts       # 프로젝트 설정
├── docker-compose.yml        # PostgreSQL 16
├── gradlew / gradlew.bat     # Gradle Wrapper
├── .claude/                  # Claude Code 설정
│   ├── CLAUDE.md             # 메인 가이드
│   └── agents/               # 에이전트 정의
├── docs/                     # 프로젝트 문서
├── src/main/
│   ├── kotlin/com/stockpulse/api/
│   │   ├── StockPulseApplication.kt
│   │   ├── common/           # BaseEntity, Exceptions, DTO
│   │   ├── config/           # Security, Jackson, OpenAPI, DataInitializer
│   │   ├── auth/             # 인증 (User, JWT)
│   │   ├── stock/            # 보유 종목
│   │   ├── trade/            # 거래 기록
│   │   ├── journal/          # 매매 일지
│   │   ├── portfolio/        # 포트폴리오 (computed)
│   │   ├── analysis/         # 분석 (computed)
│   │   ├── market/           # 시장 데이터 (mock)
│   │   ├── watchlist/        # 관심 종목
│   │   └── user/             # 프로필/설정
│   └── resources/
│       ├── application.yml
│       ├── application-dev.yml
│       └── db/migration/     # Flyway SQL (V1~V7)
└── src/test/
    ├── kotlin/               # Integration tests
    └── resources/
        └── application-test.yml
```

## Configuration

### application.yml
- `spring.threads.virtual.enabled: true` — Java 21 Virtual Threads
- `spring.jpa.hibernate.ddl-auto: validate` — Flyway 마이그레이션만 사용
- `app.jwt.secret` — JWT 서명 키
- `app.jwt.access-token-expiration: 3600000` — 1시간
- `app.jwt.refresh-token-expiration: 604800000` — 7일

### application-dev.yml
- SQL 로그 출력 (DEBUG)
- Hibernate SQL 포맷팅

### application-test.yml
- `spring.docker.compose.enabled: false`
- Testcontainers PostgreSQL 사용

## Database

### 마이그레이션 파일
| File | Description |
|------|-------------|
| V1 | users 테이블 |
| V2 | stocks 테이블 |
| V3 | trades 테이블 (TEXT[] + GIN) |
| V4 | journal_entries 테이블 |
| V5 | asset_snapshots 테이블 |
| V6 | watchlist_items 테이블 (UNIQUE) |
| V7 | user_preferences 테이블 |

### 새 마이그레이션 추가
```sql
-- src/main/resources/db/migration/V8__description.sql
CREATE TABLE ...;
```

## Troubleshooting

### Port 5432 already in use
```bash
docker compose down
docker compose up -d
```

### Flyway migration error
- 기존 마이그레이션 파일은 **절대 수정하지 않음**
- 새 마이그레이션 파일 추가로 해결

### Testcontainers timeout
- Docker가 실행 중인지 확인
- 싱글톤 컨테이너 패턴 사용 확인 (`IntegrationTestSupport.kt`)
