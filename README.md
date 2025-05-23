# 📅 Schedule API

Spring Boot + JDBC 기반 일정 관리 API입니다.  
비밀번호 기반 인증을 사용하며, 기본적인 CRUD 기능을 지원합니다.

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