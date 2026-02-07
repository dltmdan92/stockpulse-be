# Investment Domain Guide

## Service Concept
투자 포트폴리오 관리 + **매매 일지** 플랫폼.
모든 거래에 "왜 매수/매도했는가"를 기록하여 투자 판단력을 향상시킨다.

## Target Personas
- **Active Trader** — 빈번한 매매, 전략 태그 활용, 성과 분석
- **Learning Investor** — 매매 이유 기록, 복기를 통한 학습
- **Visual Investor** — 포트폴리오 시각화, 차트 분석

## Core Domain Concepts

### Portfolio (보유 종목)
| Field | Type | Description |
|-------|------|-------------|
| symbol | String | 종목 코드 (AAPL, 005930) |
| name | String | 종목명 |
| quantity | Int | 보유 수량 |
| avgPrice | BigDecimal | 평균 매수가 |
| currentPrice | BigDecimal | 현재가 |
| sector | String | 섹터 (Technology, Financials...) |
| country | String | 국가 (US, KR) |

### Trade (매매 기록 - 핵심 기능)
| Field | Type | Description |
|-------|------|-------------|
| type | Enum | `buy` / `sell` (소문자) |
| quantity | Int | 거래 수량 |
| price | BigDecimal | 거래 가격 |
| date | LocalDate | 거래일 |
| memo | String | **매매 이유 (필수)** |
| tags | TEXT[] | 전략 태그 (`#AI`, `#저점매수`) |
| targetPrice | BigDecimal? | 목표가 (선택) |
| stopLoss | BigDecimal? | 손절가 (선택) |

### Strategy Tags (전략 태그)
태그별 성과를 분석하여 어떤 전략이 효과적인지 파악:
- `#AI` — AI 관련 투자
- `#저점매수` — 저점 매수 전략
- `#모멘텀` — 모멘텀 투자
- `#장기투자` — 장기 보유
- `#실적시즌` — 실적 발표 기반
- `#반도체` — 반도체 섹터
- `#익절` — 이익 실현
- `#전기차` — 전기차 섹터

### Performance Metrics (성과 지표)
| Metric | Formula |
|--------|---------|
| Win Rate | 수익 거래 수 / 전체 매도 거래 수 × 100 |
| Avg Return | 거래별 수익률 평균 |
| Avg Holding Days | 매수→매도 기간 평균 |
| Best Trade | 최고 수익률 |
| Worst Trade | 최저 수익률 |
| Tag Performance | 태그별 거래 수, 승률, 평균 수익률 |

### Portfolio Summary (포트폴리오 요약)
| Metric | Formula |
|--------|---------|
| Total Value | Σ(currentPrice × quantity) |
| Total Cost | Σ(avgPrice × quantity) |
| Total Return | totalValue - totalCost |
| Return % | (totalReturn / totalCost) × 100 |

### Sector Allocation (섹터 배분)
| Metric | Formula |
|--------|---------|
| Value | Σ(currentPrice × quantity) per sector |
| Percentage | sectorValue / totalValue × 100 |
| Color | 섹터별 고정 색상 (SectorColorMapper) |

## Currency Rules
| 통화 | 형식 | 예시 |
|------|------|------|
| KRW | 소수점 없음 | ₩75,400 |
| USD | 소수점 2자리 | $189.84 |
| Percent | +/- 기호 | +19.2%, -0.47% |

## Data Validation

### Required Fields
- Stock: symbol, name, quantity
- Trade: symbol, name, type, quantity, price, date
- Journal: date, content

### Allowed Values
- Trade type: `buy`, `sell`
- Journal sentiment: `bullish`, `bearish`, `neutral`
- User preference theme: `dark`, `light`
- Country: `US`, `KR`

## Korean-English Terminology
| 한국어 | English | DB Table / Column |
|--------|---------|-------------------|
| 보유 종목 | Stock Holdings | `stocks` |
| 거래 기록 | Trade History | `trades` |
| 매매 일지 | Trade Journal | `journal_entries` |
| 관심 종목 | Watchlist | `watchlist_items` |
| 포트폴리오 | Portfolio | computed |
| 매수 | Buy | `buy` |
| 매도 | Sell | `sell` |
| 수익률 | Return Rate | computed |
| 승률 | Win Rate | computed |
| 섹터 | Sector | `sector` |
| 평균 매수가 | Avg Price | `avg_price` |
| 현재가 | Current Price | `current_price` |
| 목표가 | Target Price | `target_price` |
| 손절가 | Stop Loss | `stop_loss` |
