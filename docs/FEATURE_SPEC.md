# Feature Specification (API Endpoints)

## 1. Auth (`/api/auth`)

### POST /api/auth/register
회원가입. 이메일 중복 시 409.
```json
// Request
{ "email": "user@example.com", "password": "pass123", "name": "홍길동" }
// Response (201)
{ "accessToken": "...", "refreshToken": "...", "user": { "id": "uuid", "email": "...", "name": "..." } }
```

### POST /api/auth/login
로그인. 잘못된 credentials 시 401.
```json
// Request
{ "email": "user@example.com", "password": "pass123" }
// Response (200) — register와 동일 shape
```

### POST /api/auth/refresh
토큰 갱신. 만료된 refresh token 시 401.
```json
// Request
{ "refreshToken": "..." }
// Response (200) — register와 동일 shape
```

## 2. Stocks (`/api/stocks`) — JWT 필수

### FE Type: `Stock`
```typescript
{ id, symbol, name, quantity, avgPrice, currentPrice, sector, country, addedAt }
```

- **GET /api/stocks** → `Stock[]` — 사용자의 보유 종목 목록
- **GET /api/stocks/{id}** → `Stock` — 종목 상세 (타 유저 404)
- **POST /api/stocks** → `Stock` (201) — 종목 추가
- **PUT /api/stocks/{id}** → `Stock` — 종목 수정
- **DELETE /api/stocks/{id}** → 204 — 종목 삭제

## 3. Trades (`/api/trades`) — JWT 필수

### FE Type: `Trade`
```typescript
{ id, stockId, symbol, name, type, quantity, price, date, memo, tags, targetPrice?, stopLoss?, holdingPeriod? }
```

- **GET /api/trades** → `Trade[]` — 거래 목록
  - Query: `type` (buy/sell), `symbol`, `startDate`, `endDate`
- **GET /api/trades/{id}** → `Trade`
- **POST /api/trades** → `Trade` (201)
- **PUT /api/trades/{id}** → `Trade`
- **DELETE /api/trades/{id}** → 204

특이사항:
- `tags`: `TEXT[]` PostgreSQL 배열, GIN 인덱스
- `type`: `buy` | `sell` 소문자 enum

## 4. Journals (`/api/journals`) — JWT 필수

### FE Type: `JournalEntry`
```typescript
{ id, tradeId, date, content, sentiment }
```

- **GET /api/journals** → `JournalEntry[]` — 일지 목록 (날짜 역순)
- **POST /api/journals** → `JournalEntry` (201)
- **PUT /api/journals/{id}** → `JournalEntry`
- **DELETE /api/journals/{id}** → 204

특이사항:
- `sentiment`: `bullish` | `bearish` | `neutral` 검증

## 5. Portfolio (`/api/portfolio`) — JWT 필수, Computed

### GET /api/portfolio/summary
FE Type: `PortfolioSummary`
```typescript
{ totalValue, totalCost, totalReturn, totalReturnPercent, dailyChange, dailyChangePercent }
```
보유 종목 기반 실시간 계산.

### GET /api/portfolio/sectors
FE Type: `SectorAllocation[]`
```typescript
{ sector, value, percentage, color }
```
섹터별 비중 + 고정 색상 매핑.

### GET /api/portfolio/history
FE Type: `AssetHistory[]`
```typescript
{ date, value }
```
최근 30일 자산 변동 추이 (시뮬레이션).

## 6. Analysis (`/api/analysis`) — JWT 필수, Computed

### GET /api/analysis/stats
FE Type: `TradeStats`
```typescript
{ totalTrades, winRate, avgReturn, avgHoldingDays, bestTrade, worstTrade }
```
매수→매도 매칭으로 수익률 계산.

### GET /api/analysis/tags
FE Type: `TagPerformance[]`
```typescript
{ tag, trades, winRate, avgReturn }
```
태그별 그룹핑 성과 분석.

## 7. Market (`/api/market`) — Public (JWT 불필요)

### GET /api/market/indices
FE Type: `MarketIndex[]`
```typescript
{ symbol, name, value, change, changePercent }
```
SPX, NDX, KOSPI, DJI 하드코딩 mock 데이터.

### GET /api/market/news
FE Type: `NewsItem[]`
```typescript
{ id, title, source, date, url, symbol? }
```
하드코딩 mock 뉴스.

## 8. Watchlist (`/api/watchlist`) — JWT 필수

### FE Type: `WatchlistItem`
```typescript
{ id, symbol, name, currentPrice, changePercent, targetPrice, currency }
```

- **GET /api/watchlist** → `WatchlistItem[]`
- **POST /api/watchlist** → `WatchlistItem` (201) — symbol 중복 시 409
- **DELETE /api/watchlist/{id}** → 204

특이사항:
- `user_id + symbol` UNIQUE 제약

## 9. User (`/api/user`) — JWT 필수

### GET/PUT /api/user/profile
```typescript
// Response
{ id, email, name }
// Update Request
{ name: "새이름" }
```

### GET/PUT /api/user/preferences
```typescript
// Response
{ theme, priceAlert, tradeConfirm, portfolioReport, marketNews }
// Update Request — 부분 업데이트 가능
{ theme: "light", priceAlert: false }
```

특이사항:
- GET 시 preferences가 없으면 기본값으로 자동 생성
