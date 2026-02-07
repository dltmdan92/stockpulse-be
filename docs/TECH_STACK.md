# Technology Stack

## Core Stack

| Category | Technology | Version |
|----------|-----------|---------|
| Language | Kotlin | 2.1.10 (K2 compiler) |
| Framework | Spring Boot | 3.4.2 |
| Build | Gradle (Kotlin DSL) | 8.12 |
| JDK | Java | 21 (Virtual Threads) |
| Database | PostgreSQL | 16 |

## Dependencies

### Spring Boot Starters
| Dependency | Purpose |
|-----------|---------|
| `spring-boot-starter-web` | REST API, Tomcat |
| `spring-boot-starter-data-jpa` | JPA + Hibernate 6 |
| `spring-boot-starter-security` | Spring Security 6 |
| `spring-boot-starter-validation` | Bean Validation |
| `spring-boot-docker-compose` | Docker Compose 연동 |

### Kotlin
| Dependency | Purpose |
|-----------|---------|
| `jackson-module-kotlin` | Kotlin JSON 직렬화 |
| `kotlin-reflect` | Reflection 지원 |
| `kotlin-plugin-spring` | open class 생성 |
| `kotlin-plugin-jpa` | no-arg 생성자 |

### Database
| Dependency | Purpose |
|-----------|---------|
| `postgresql` | PostgreSQL JDBC 드라이버 |
| `flyway-core` | DB 마이그레이션 |
| `flyway-database-postgresql` | Flyway PostgreSQL 지원 |

### Authentication
| Dependency | Version | Purpose |
|-----------|---------|---------|
| `jjwt-api` | 0.12.6 | JWT API |
| `jjwt-impl` | 0.12.6 | JWT 구현 |
| `jjwt-jackson` | 0.12.6 | JWT JSON 처리 |

### API Documentation
| Dependency | Version | Purpose |
|-----------|---------|---------|
| `springdoc-openapi-starter-webmvc-ui` | 2.8.4 | Swagger UI + OpenAPI 3 |

### Test
| Dependency | Purpose |
|-----------|---------|
| `spring-boot-starter-test` | JUnit 5, MockMvc |
| `spring-security-test` | Security 테스트 |
| `spring-boot-testcontainers` | Testcontainers 통합 |
| `testcontainers:junit-jupiter` | JUnit 5 Testcontainers |
| `testcontainers:postgresql` | PostgreSQL 컨테이너 |

## Key Configuration

### build.gradle.kts Plugins
```kotlin
org.springframework.boot     // 3.4.2
io.spring.dependency-management
kotlin("jvm")                // 2.1.10
kotlin("plugin.spring")      // open class
kotlin("plugin.jpa")         // no-arg constructor
```

### allOpen
JPA 엔티티 클래스를 open으로 변환:
```kotlin
allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}
```

### Virtual Threads
```yaml
spring.threads.virtual.enabled: true
```
Java 21의 Virtual Threads로 높은 동시성 처리.

## Library Addition Policy
- 기존 라이브러리로 해결 가능하면 새 라이브러리 추가 금지
- 새 라이브러리 필요 시 반드시 확인 후 추가
- 추가 시 `docs/TECH_STACK.md` 업데이트

## Directory Structure
```
src/main/kotlin/com/stockpulse/api/
├── common/      # 공통 (BaseEntity, Exception, DTO)
├── config/      # 설정 (Security, Jackson, OpenAPI)
├── auth/        # 인증 (User, JWT)
├── stock/       # 보유 종목
├── trade/       # 거래 기록
├── journal/     # 매매 일지
├── portfolio/   # 포트폴리오 (computed)
├── analysis/    # 분석 (computed)
├── market/      # 시장 데이터 (mock)
├── watchlist/   # 관심 종목
└── user/        # 프로필/설정
```
