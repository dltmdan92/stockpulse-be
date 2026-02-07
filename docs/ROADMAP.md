# Development Roadmap

## Phase 1 — Frontend Prototype (Complete)
프로젝트: `stockpulse-fe`
- [x] Next.js 16 + React 19 + TypeScript
- [x] 대시보드, 포트폴리오, 거래, 분석, 시장, 관심종목, 설정 페이지
- [x] Zustand 상태 관리 + Mock 데이터
- [x] 다크 테마 금융 대시보드 UI

## Phase 2 — Backend API Server (Complete)
프로젝트: `stockpulse-be`
- [x] Spring Boot 3.4.2 + Kotlin 2.1.10 프로젝트 설정
- [x] PostgreSQL 16 + Flyway 마이그레이션 (V1~V7)
- [x] JWT 인증 (register, login, refresh)
- [x] 보유 종목 CRUD (`/api/stocks`)
- [x] 거래 기록 CRUD + TEXT[] 태그 + 필터링 (`/api/trades`)
- [x] 매매 일지 CRUD (`/api/journals`)
- [x] 포트폴리오 요약/분석 (`/api/portfolio/*`)
- [x] 매매 통계/태그 성과 (`/api/analysis/*`)
- [x] 시장 데이터 mock (`/api/market/*`)
- [x] 관심 종목 CRUD (`/api/watchlist`)
- [x] 사용자 프로필/설정 (`/api/user/*`)
- [x] Testcontainers 통합 테스트
- [x] dev 프로필 시드 데이터 + SpringDoc OpenAPI

## Phase 3 — FE-BE Integration (Planned)
- [ ] FE에 API Client 추가 (fetch/axios)
- [ ] Zustand → API 호출로 전환
- [ ] 로그인/회원가입 UI 연동
- [ ] JWT 토큰 관리 (저장, 갱신, 만료 처리)
- [ ] API 에러 처리 UI (toast, error boundary)
- [ ] 로딩 상태 UI 개선

## Phase 4 — Advanced Features (Planned)
- [ ] 실시간 주가 API 연동
- [ ] 포트폴리오 히스토리 실제 스냅샷 저장
- [ ] 알림 시스템 (가격 알림, 거래 확인)
- [ ] 테마 토글 (다크/라이트) 실제 구현
- [ ] 고급 차트 (캔들스틱, 기술적 분석)
- [ ] 소셜 기능 (전략 공유)
- [ ] 모바일 반응형 최적화
- [ ] AI 투자 인사이트

## Development Commands
```bash
docker compose up -d                                      # DB 기동
./gradlew bootRun --args='--spring.profiles.active=dev'   # 서버 기동
./gradlew test                                            # 테스트
./gradlew compileKotlin                                   # 컴파일 검증
```
