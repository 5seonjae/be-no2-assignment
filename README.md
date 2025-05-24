# 📅 Schedule API

Spring Boot + JDBC 기반 일정 관리 API입니다.  
비밀번호 기반 인증을 사용하며, 기본적인 CRUD 기능을 지원합니다.

## Postman API 명세서
- https://documenter.getpostman.com/view/44606074/2sB2qcBKtT

## ERD
![Schedule](https://github.com/user-attachments/assets/549a30ba-073e-4b3f-b897-214dfca1dbde)

---

## ✅ API 목록

| 기능 | 메서드 | URL | 설명 |
|------|--------|-----|------|
| 일정 생성 | POST | /schedules | 새 일정 등록 |
| 전체 일정 조회 | GET | /schedules | 조건 기반 일정 목록 조회 |
| 단건 일정 조회 | GET | /schedules/{id} | 특정 일정 상세 조회 |
| 일정 수정 | PUT | /schedules/{id} | 비밀번호 검증 후 일정 수정 |
| 일정 삭제 | DELETE | /schedules/{id} | 비밀번호 검증 후 일정 삭제 |

---

## ✏️ 1. 일정 생성 API

- **Method**: POST
- **URL**: `/schedules`

### 요청 예시
```json
{
  "title": "스터디 준비",
  "writer": "홍길동",
  "password": "1234"
}
```

### 응답 예시 ( 201 Created )
```json
{
  "id": 1,
  "message": "일정이 성공적으로 등록되었습니다."
}
```

## 📖 2. 전체 일정 조회 API

- **Method**: GET
- **URL**: `/schedules`
- Query Params:
  - writer=홍길동
  - modifiedAt=2025-05-23

### 응답 예시 ( 200 OK )
```json
[
  {
    "id": 1,
    "title": "스터디 준비",
    "writer": "홍길동",
    "createdAt": "2025-05-23T15:00:00",
    "updatedAt": "2025-05-23T15:00:00"
  }
]
```

## 🔍 3. 단건 일정 조회 API

- **Method**: GET
- **URL**: `/schedules/{id}`
- Path Variable:
  - id = 일정 ID

### 응답 예시 ( 200 OK )
```json
{
  "id": 1,
  "title": "스터디 준비",
  "writer": "홍길동",
  "createdAt": "2025-05-23T15:00:00",
  "updatedAt": "2025-05-23T15:00:00"
}
```

## 🛠️ 4. 일정 수정 API

- **Method**: PUT
- **URL**: `/schedules/{id}`

### 요청 예시
```json
{
  "title": "스터디 장소 변경",
  "writer": "홍길순",
  "password": "1234"
}
```

### 응답 예시
```json
{
  "id": 1,
  "title": "스터디 장소 변경",
  "writer": "홍길순",
  "createdAt": "2025-05-23T15:00:00",
  "updatedAt": "2025-05-23T15:45:00"
}
```

## ❌ 5. 일정 삭제 API

- **Method**: DELETE
- **URL**: `/schedules/{id}`

### 요청 예시
```json
{
  "password": "1234"
}
```

### 응답 예시
```json
{
  "message": "삭제 완료"
}
```

## 📌 공통 사항

- 모든 날짜와 시간은 ISO 8601 포맷 사용 (예: 2025-05-23T15:00:00)
- password 필드는 요청에만 포함되고, 응답에는 포함되지 않음
- 수정 시 createdAt은 그대로 유지되고, updatedAt은 자동 갱신됨

## 자가 점검

### 1. 적절한 관심사 분리를 적용했는가?

이번 프로젝트에서는 Controller – Service – Repository의 3계층 아키텍처를 적용하여 관심사를 명확히 분리했습니다.
- Controller는 HTTP 요청/응답을 처리하는 역할에 집중합니다.
- Service는 비즈니스 로직(예: 비밀번호 검증, 예외 처리 등)을 담당합니다.
- Repository는 순수하게 DB 접근(JDBC 쿼리 실행)에만 집중합니다.
이를 통해 각 계층의 책임이 명확해졌고, 유지보수성과 테스트 용이성이 크게 향상되었습니다.

### 2. RESTful한 API를 설계했는가?

RESTful한 설계 원칙을 최대한 지키도록 노력했습니다.
- HTTP 메서드의 의미 : POST(생성), GET(조회), PUT(수정), DELETE(삭제) 사용
- 리소스 URL : /schedules, /schedules/{id} 등 명사 기반 URI 사용
- 상태 코드 사용 : 200, 201, 401, 404 등 의미에 맞는 응답 코드 반환
- 요청/응답 명확성 : JSON 기반 request/response body 사용

하지만, 한계점도 있었습니다.
- 일부 인증/삭제 요청에서 비밀번호를 Body에 담는 방식은 REST 철학상 완전한 자원 식별로 보기 어려울 수 있습니다.

전체적으로 RESTful 설계를 잘 지키고 있으나, 인증(비밀번호)의 경우 REST보다 RPC적 방식에 가깝다는 점은 한계로 볼 수 있습니다.

### 3. 수정/삭제 API의 Request 방식은 어떤 방식으로 사용했는가?

수정 API (PUT /schedules/{id})
- @PathVariable로 id 전달
- @RequestBody로 title, writer, password를 전달하여 처리
- ⇒ Body 방식 사용

삭제 API (DELETE /schedules/{id})
- @PathVariable로 id 전달
- @RequestBody로 password를 전달하여 비밀번호 일치 여부 확인 후 삭제
- ⇒ Body 방식 사용

비밀번호를 Body에 담은 이유
- 보안상 query param보다 안전하기 때문
- JSON으로 보내는 구조가 통일성과 직관성을 유지하기에 적합하기 때문
