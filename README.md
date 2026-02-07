# StockPulse

> 나의 투자를 기록하고, 추적하고, 복기한다.

StockPulse는 개인 투자자를 위한 포트폴리오 관리 + 매매 일지 서비스입니다.

## 왜 StockPulse인가?

투자에서 가장 중요한 건 **"왜 이 거래를 했는가"**를 기억하는 것입니다.
대부분의 투자 앱은 수익률만 보여주지만, StockPulse는 모든 거래에 **이유와 감정**을 기록하고
태그 기반 전략 분석으로 자신의 투자 패턴을 객관적으로 되돌아볼 수 있게 해줍니다.

## 주요 기능

### 포트폴리오 관리
보유 종목의 현재 가치, 수익률, 섹터별 배분을 한눈에 파악합니다.

### 매매 기록 & 태그
매수/매도 기록에 `#장기투자`, `#AI`, `#저점매수` 같은 태그를 달아 전략별로 관리합니다.

### 매매 일지
거래마다 판단 근거와 시장 심리(bullish/bearish/neutral)를 기록합니다.
시간이 지난 후 복기하면 반복되는 실수를 발견할 수 있습니다.

### 전략 분석
태그별 승률, 평균 수익률, 보유 기간 등을 분석해 어떤 전략이 잘 먹히는지 데이터로 확인합니다.

### 관심 종목
아직 매수하지 않은 종목을 목표가와 함께 추적합니다.

## 기술 스택

- **Backend**: Kotlin + Spring Boot 3.4
- **Database**: PostgreSQL 16
- **Auth**: JWT
- **JDK**: Java 21 (Virtual Threads)

## 시작하기

```bash
# PostgreSQL 기동
docker compose up -d

# 개발 서버 실행
./gradlew bootRun --args='--spring.profiles.active=dev'
```

서버: http://localhost:8080 | API 문서: http://localhost:8080/swagger-ui.html

dev 프로필에서는 데모 계정(`demo@stockpulse.com` / `demo1234`)과 샘플 데이터가 자동 생성됩니다.

## 프로젝트 구조

| 저장소 | 설명 |
|--------|------|
| `stockpulse-be` | 백엔드 API 서버 (이 저장소) |
| `stockpulse-fe` | 프론트엔드 웹앱 |
