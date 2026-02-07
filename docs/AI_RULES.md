# AI Assistant Rules

## 1. Safety
- 파일 삭제/덮어쓰기 전 반드시 확인
- 경로 확인 후 작업 (특히 마이그레이션 파일)
- 승인 없이 새 라이브러리 추가 금지
- Flyway 마이그레이션은 **수정 금지**, 새 버전만 추가
- `application.yml`의 JWT secret 등 민감 설정 변경 주의

## 2. Accuracy
- 코드 작성 전 관련 파일 내용 확인
- 근본 원인 해결 (임시 방편 금지)
- import 확인 (존재하지 않는 클래스 참조 금지)
- FE `types/index.ts` 대조하여 응답 shape 정확성 보장

## 3. User Priority
- 요구사항이 불명확하면 질문
- 사용자 선호 존중
- 문서 참조하여 컨텍스트 확인

## 4. Work Loop
1. **Plan** — 변경 범위 파악, 영향 분석
2. **Execute** — 코드 작성
3. **Verify** — `./gradlew compileKotlin` 또는 `./gradlew test`
4. **Improve** — 리뷰 후 개선
5. **Docs Update** — 변경 사항 문서 반영
6. **Finalize** — 최종 검증

## 5. Documentation Policy
코드 변경 시 관련 문서 업데이트:
- 새 엔드포인트 → `docs/FEATURE_SPEC.md`
- 아키텍처 변경 → `docs/ARCHITECTURE.md`
- 새 기술/라이브러리 → `docs/TECH_STACK.md`
- 컨벤션 변경 → `docs/CONVENTIONS.md`
- 도메인 규칙 변경 → `docs/DOMAIN.md`

## 6. Reference Document Index

| 문서 | 경로 | 용도 |
|------|------|------|
| Claude Instructions | `.claude/CLAUDE.md` | 프로젝트 메인 가이드 |
| Backend Dev Agent | `.claude/agents/backend-dev.md` | 백엔드 개발 에이전트 |
| API Reviewer Agent | `.claude/agents/api-reviewer.md` | API 리뷰 에이전트 |
| Domain Agent | `.claude/agents/stock-domain.md` | 도메인 전문 에이전트 |
| Code Quality Agent | `.claude/agents/code-quality.md` | 코드 품질 에이전트 |
| AI Rules | `docs/AI_RULES.md` | AI 공통 규칙 (이 문서) |
| Architecture | `docs/ARCHITECTURE.md` | 시스템 아키텍처 |
| Conventions | `docs/CONVENTIONS.md` | 코딩 컨벤션 |
| API Design | `docs/API_DESIGN.md` | API 설계 가이드 |
| Domain | `docs/DOMAIN.md` | 투자 도메인 가이드 |
| Feature Spec | `docs/FEATURE_SPEC.md` | API 엔드포인트 스펙 |
| Project Spec | `docs/PROJECT_SPEC.md` | 프로젝트 개요 |
| Roadmap | `docs/ROADMAP.md` | 개발 로드맵 |
| Setup | `docs/SETUP.md` | 환경 설정 가이드 |
| Tech Stack | `docs/TECH_STACK.md` | 기술 스택 상세 |
