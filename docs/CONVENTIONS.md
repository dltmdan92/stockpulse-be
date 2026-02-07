# Coding Conventions

## Basic Principles
1. **가독성 우선** — 명확한 코드 > 짧은 코드
2. **일관성** — 기존 코드 스타일 따르기
3. **타입 안전** — Kotlin null safety 적극 활용

## Kotlin Style

### 변수
```kotlin
// val 우선, var 최소화
val userId = user.id!!  // 테스트에서만 !! 허용
var name: String        // 엔티티 프로퍼티는 var 허용
```

### 함수
```kotlin
// 단일 표현식 함수
fun getAll(userId: String): List<StockResponse> =
    stockRepository.findByUserId(userId).map { it.toResponse() }

// 복잡한 로직은 블록 함수
fun create(userId: String, request: StockRequest): StockResponse {
    val stock = Stock(...)
    return stockRepository.save(stock).toResponse()
}
```

### Extension Functions
```kotlin
// Entity → Response DTO 변환
fun Stock.toResponse() = StockResponse(
    id = id!!,
    symbol = symbol,
    ...
)
```

## File Naming
| 종류 | 패턴 | 예시 |
|------|------|------|
| Entity | `{Name}.kt` | `Stock.kt` |
| Repository | `{Name}Repository.kt` | `StockRepository.kt` |
| Service | `{Name}Service.kt` | `StockService.kt` |
| Controller | `{Name}Controller.kt` | `StockController.kt` |
| DTOs | `{Name}Dtos.kt` | `StockDtos.kt` |
| Migration | `V{N}__{description}.sql` | `V2__create_stocks_table.sql` |
| Test | `{Name}IntegrationTest.kt` | `StockIntegrationTest.kt` |

## Package Structure
```
{domain}/
├── entity/       # JPA 엔티티
├── repository/   # Spring Data JPA 인터페이스
├── dto/          # Request/Response + toResponse()
├── service/      # 비즈니스 로직
└── controller/   # REST 엔드포인트
```

## Import Grouping
```kotlin
// 1. Java/Kotlin stdlib
import java.math.BigDecimal

// 2. Jakarta
import jakarta.persistence.Entity

// 3. Spring
import org.springframework.stereotype.Service

// 4. Third-party
import io.jsonwebtoken.Jwts

// 5. Project internal
import com.stockpulse.api.common.exception.NotFoundException
```

## Controller Convention
```kotlin
@RestController
@RequestMapping("/api/{domain}")
class {Name}Controller(
    private val service: {Name}Service,
) {
    @GetMapping             // 목록 조회
    @GetMapping("/{id}")    // 단건 조회
    @PostMapping            // 생성 → @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/{id}")    // 수정
    @DeleteMapping("/{id}") // 삭제 → @ResponseStatus(HttpStatus.NO_CONTENT)
}
```

## Service Convention
```kotlin
@Service
class {Name}Service(
    private val repository: {Name}Repository,
) {
    @Transactional(readOnly = true)  // 읽기 전용
    fun getAll(userId: String): List<Response> = ...

    @Transactional                    // 쓰기
    fun create(userId: String, request: Request): Response = ...
}
```

## DTO Convention
```kotlin
// Request: validation 어노테이션
data class StockRequest(
    @field:NotBlank val symbol: String,
    @field:Positive val quantity: Int,
    val avgPrice: Double,  // FE number → Double
)

// Response: FE 타입과 정확히 일치
data class StockResponse(
    val id: String,        // FE string
    val avgPrice: Double,  // Entity BigDecimal → Double
    val addedAt: String,   // Entity LocalDate → String
)
```

## Git Commit Convention
```
feat: 새 기능 추가
fix: 버그 수정
refactor: 리팩토링
docs: 문서 수정
chore: 빌드, 설정 변경
test: 테스트 추가/수정
```

## Language Rules
- UI 텍스트 (FE): 한국어
- 코드: 영어 (변수명, 클래스명, 함수명)
- 주석/문서: 한국어 또는 영어 (상황에 따라)
- API 경로: 영어 소문자 (`/api/stocks`)
- DB 컬럼: snake_case 영어 (`avg_price`)
