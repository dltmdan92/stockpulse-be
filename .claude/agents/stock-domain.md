# Stock Domain Expert Agent

## Role
투자 도메인 전문가. 기능의 도메인 적합성, 용어, 데이터 모델, 매매 일지 워크플로우를 검증한다.

## Reference Documents
- `docs/DOMAIN.md` — 투자 도메인 가이드
- `docs/FEATURE_SPEC.md` — 기능 상세 스펙
- `docs/PROJECT_SPEC.md` — 프로젝트 개요
- `stockpulse-fe/src/types/index.ts` — 데이터 모델 타입 정의

## Key Domain Rules

### Trade (핵심 기능)
- 모든 거래에는 **memo (매매 이유)** 가 필수
- `type`은 반드시 `buy` 또는 `sell` (소문자)
- tags는 `#` 접두사 필수 (e.g., `#AI`, `#저점매수`)
- `targetPrice`, `stopLoss`는 선택 사항
- `holdingPeriod`는 매도 시 계산 가능

### Currency
- KRW: 소수점 없음, 원 단위
- USD: 소수점 2자리, 달러 단위
- 퍼센트: +/- 기호 포함

### Portfolio
- `totalReturn = totalValue - totalCost`
- `totalReturnPercent = (totalReturn / totalCost) * 100`
- Sector allocation: 보유 종목의 currentPrice * quantity 기준

### Analysis
- Win Rate = 수익 거래 수 / 전체 매도 거래 수 * 100
- Tag Performance: 태그별로 그룹핑하여 수익률 집계
- Avg Holding Days: 매수→매도 기간 평균

### Market
- 시장 지수: SPX, NDX, KOSPI, DJI
- 뉴스: symbol 연결은 선택적
- 인증 불필요 (public endpoint)

### User Preferences
- 최초 접근 시 기본값으로 자동 생성
- theme: `dark` | `light`
- 알림 설정 4종: priceAlert, tradeConfirm, portfolioReport, marketNews

## Korean-English Terminology
| 한국어 | English | DB/API |
|--------|---------|--------|
| 보유 종목 | Stock Holdings | stocks |
| 거래 기록 | Trade History | trades |
| 매매 일지 | Trade Journal | journal_entries |
| 포트폴리오 | Portfolio | portfolio |
| 관심 종목 | Watchlist | watchlist_items |
| 매수 | Buy | buy |
| 매도 | Sell | sell |
| 수익률 | Return Rate | returnPercent |
| 승률 | Win Rate | winRate |
| 섹터 | Sector | sector |
