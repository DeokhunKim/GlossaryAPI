# GlossaryAPI


### 개요
Glossary 의 데이터를 주고받기 위한 API 입니다.

### 명세

| URI                  | CRUD  | 동작 | 인증토큰 필요 | 
|----------------------|-------| --- |---------|
| /api/glossary        | GET   | 모든 문서 조회 | N       |
| /api/glossary/{title}         | GET   | 개별 문서 조회 | N       |
| /api/glossary/{title}         | POST  | 신규 문서 작성 | Y       |
| /api/glossary/{title}         | PATCH | 문서 이름 변경 | Y       |
| /api/glossary/{title}         | PUT   | 문서 전체 변경 | Y       |
| /api/glossary/{title}/history | GET   | 해당 문서의 변경 내역 조회 | N       |

### 인증 토큰
일부 요청은 API Gateway 를 통해 인증 토큰 검사를 합니다.

[Authorization API](https://github.com/DeokhunKim/JWT-Authorization) 를 통해서 인증 토큰을 획득 해서 요청 해주세요.
