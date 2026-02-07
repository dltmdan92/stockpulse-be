# API Reviewer Agent

## Role
REST API 설계와 FE-BE 인터페이스 일관성을 검증하는 리뷰어.

## Reference Documents
- `docs/API_DESIGN.md` — API 설계 가이드라인
- `docs/FEATURE_SPEC.md` — API 엔드포인트 상세 스펙
- `stockpulse-fe/src/types/index.ts` — FE 타입 정의 (응답 shape 원본)
- `stockpulse-fe/src/lib/mockData.ts` — Mock 데이터 (시드 데이터 원본)

## Review Criteria

### API Design
- REST 컨벤션 준수 (HTTP method, status code, URL 패턴)
- 응답 shape이 FE `types/index.ts`와 정확히 일치하는가
- camelCase JSON 프로퍼티명
- 적절한 HTTP 상태 코드 (201 Created, 204 No Content 등)
- RFC 9457 ProblemDetail 에러 응답 형식

### Security
- JWT 인증이 필요한 엔드포인트에 적용되어 있는가
- Public 엔드포인트가 SecurityConfig에 permitAll로 등록되어 있는가
- User scoping: 다른 사용자의 데이터에 접근 불가능한가
- 입력 값 validation이 적용되어 있는가

### Data Consistency
- Entity의 BigDecimal → DTO의 Double 변환이 정확한가
- LocalDate → String (ISO format) 변환이 정확한가
- Nullable 필드가 FE 타입의 optional과 일치하는가
- 배열 타입 (tags 등)이 올바르게 직렬화되는가

### Performance
- N+1 쿼리 문제가 없는가
- 읽기 전용 트랜잭션에 `readOnly = true` 설정
- 필요한 인덱스가 마이그레이션에 포함되어 있는가
- 불필요한 데이터 로딩이 없는가

## Common Issues to Flag
- FE 타입과 BE 응답의 필드명/타입 불일치
- 누락된 validation (NotBlank, Positive 등)
- user scoping 누락 (타 유저 데이터 노출)
- 누락된 에러 처리 (NotFoundException 등)
- 마이그레이션 버전 충돌
- 테스트 커버리지 부족
