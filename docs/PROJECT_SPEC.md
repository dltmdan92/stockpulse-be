# Project Specification

## StockPulse
투자 포트폴리오 관리 + 매매 일지 웹앱.
"나의 투자를 기록하고, 추적하고, 복기한다."

## Differentiation
모든 거래에 **"왜?"** 를 기록하는 매매 일지 기능.
태그 기반 전략 분석으로 투자 판단력 향상.

## Backend Responsibilities
- REST API 제공 (JSON)
- JWT 기반 인증/인가
- PostgreSQL 데이터 저장
- 포트폴리오/분석 데이터 실시간 계산
- 시장 데이터 mock 제공

## Core Features

| Feature | Route | Description |
|---------|-------|-------------|
| Auth | `/api/auth/*` | 회원가입, 로그인, 토큰 갱신 |
| Stock | `/api/stocks` | 보유 종목 CRUD |
| Trade | `/api/trades` | 매매 기록 CRUD + 태그/필터 |
| Journal | `/api/journals` | 매매 일지 CRUD |
| Portfolio | `/api/portfolio/*` | 포트폴리오 요약/분석 |
| Analysis | `/api/analysis/*` | 매매 통계/태그 성과 |
| Market | `/api/market/*` | 시장 지수/뉴스 (mock) |
| Watchlist | `/api/watchlist` | 관심 종목 CRUD |
| User | `/api/user/*` | 프로필/설정 |

## Data Models (Main)
- `User` — 사용자 (email, password, name)
- `Stock` — 보유 종목 (symbol, quantity, avgPrice, currentPrice)
- `Trade` — 거래 기록 (type, price, memo, tags)
- `JournalEntry` — 매매 일지 (content, sentiment)
- `WatchlistItem` — 관심 종목 (symbol, targetPrice)
- `UserPreference` — 사용자 설정 (theme, notifications)

## Current Phase
- **Phase 1** (Complete): Frontend prototype (`stockpulse-fe`)
- **Phase 2** (Complete): Backend API server (`stockpulse-be`)
- **Phase 3** (Planned): FE-BE integration, real-time data

## References
- Architecture → `docs/ARCHITECTURE.md`
- API Endpoints → `docs/FEATURE_SPEC.md`
- API Design → `docs/API_DESIGN.md`
- Domain Guide → `docs/DOMAIN.md`
- Tech Stack → `docs/TECH_STACK.md`
