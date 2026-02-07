# API Design Guidelines

## Base URL
```
http://localhost:8080/api
```

## Authentication
```
Authorization: Bearer {JWT_ACCESS_TOKEN}
```

## Endpoint Patterns

### CRUD Resource
```
GET    /api/{resource}       → List<Response>     (200)
GET    /api/{resource}/{id}  → Response           (200)
POST   /api/{resource}       → Response           (201)
PUT    /api/{resource}/{id}  → Response           (200)
DELETE /api/{resource}/{id}  → void               (204)
```

### Computed Resource (읽기 전용)
```
GET    /api/{resource}/{sub} → Response           (200)
```

## HTTP Status Codes

| Code | 의미 | 사용 |
|------|------|------|
| 200 | OK | 조회, 수정 성공 |
| 201 | Created | 생성 성공 |
| 204 | No Content | 삭제 성공 |
| 400 | Bad Request | 입력 검증 실패 |
| 401 | Unauthorized | 인증 실패 (잘못된 credentials) |
| 403 | Forbidden | JWT 없음 또는 만료 |
| 404 | Not Found | 리소스 없음 |
| 409 | Conflict | 중복 (이메일, 관심종목 등) |
| 500 | Internal Server Error | 서버 오류 |

## Error Response (RFC 9457)
```json
{
  "type": "about:blank",
  "title": "Not Found",
  "status": 404,
  "detail": "Stock not found",
  "instance": "/api/stocks/invalid-id"
}
```

Validation 에러:
```json
{
  "type": "about:blank",
  "title": "Bad Request",
  "status": 400,
  "detail": "Validation failed",
  "errors": {
    "symbol": "must not be blank",
    "quantity": "must be greater than 0"
  }
}
```

## Request/Response Format
- Content-Type: `application/json`
- 프로퍼티명: **camelCase**
- 날짜: ISO 8601 문자열 (`"2024-01-15"`)
- 금액: `number` (Double)
- ID: `string` (UUID)

## Endpoint Reference

### Auth (Public)
| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/auth/register` | 회원가입 |
| POST | `/api/auth/login` | 로그인 |
| POST | `/api/auth/refresh` | 토큰 갱신 |

### Stocks (JWT)
| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/stocks` | 보유 종목 목록 |
| GET | `/api/stocks/{id}` | 종목 상세 |
| POST | `/api/stocks` | 종목 추가 |
| PUT | `/api/stocks/{id}` | 종목 수정 |
| DELETE | `/api/stocks/{id}` | 종목 삭제 |

### Trades (JWT)
| Method | Path | Query Params | Description |
|--------|------|-------------|-------------|
| GET | `/api/trades` | type, symbol, startDate, endDate | 거래 목록 (필터링) |
| GET | `/api/trades/{id}` | | 거래 상세 |
| POST | `/api/trades` | | 거래 기록 |
| PUT | `/api/trades/{id}` | | 거래 수정 |
| DELETE | `/api/trades/{id}` | | 거래 삭제 |

### Journals (JWT)
| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/journals` | 일지 목록 |
| POST | `/api/journals` | 일지 작성 |
| PUT | `/api/journals/{id}` | 일지 수정 |
| DELETE | `/api/journals/{id}` | 일지 삭제 |

### Portfolio (JWT, Computed)
| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/portfolio/summary` | 포트폴리오 요약 |
| GET | `/api/portfolio/sectors` | 섹터별 배분 |
| GET | `/api/portfolio/history` | 자산 변동 추이 |

### Analysis (JWT, Computed)
| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/analysis/stats` | 매매 통계 |
| GET | `/api/analysis/tags` | 태그별 성과 |

### Market (Public)
| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/market/indices` | 주요 지수 |
| GET | `/api/market/news` | 시장 뉴스 |

### Watchlist (JWT)
| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/watchlist` | 관심 종목 목록 |
| POST | `/api/watchlist` | 관심 종목 추가 |
| DELETE | `/api/watchlist/{id}` | 관심 종목 삭제 |

### User (JWT)
| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/user/profile` | 프로필 조회 |
| PUT | `/api/user/profile` | 프로필 수정 |
| GET | `/api/user/preferences` | 설정 조회 |
| PUT | `/api/user/preferences` | 설정 수정 |

## Swagger UI
```
http://localhost:8080/swagger-ui.html
```
