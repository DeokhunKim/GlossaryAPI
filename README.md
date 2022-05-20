# GlossaryAPI


### 개요
Glossary 의 데이터를 주고받기 위한 API 입니다.

### 명세

| URI                  | CRUD  | 동작              | 인증토큰 필요 | 
|----------------------|-------|-----------------|---------|
| /api/glossary        | GET   | 모든 문서 조회        | N       |
| /api/glossary/{title}         | GET   | 개별 문서 조회        | N       |
| /api/glossary/{title}         | POST  | 신규 문서 작성        | Y       |
| /api/glossary/{title}         | PATCH | 문서 이름 변경        | Y       |
| /api/glossary/{title}         | PUT   | 문서 내용 전체 업데이트    | Y       |
| /api/glossary/{title}         | DELETE | 해당 문서 삭제       | Y       |
| /api/glossary/{title}/history | GET   | 해당 문서의 변경 내역 조회 | N       |

<br/>


<details>
<summary><strong>[GET] /api/glossary 상세</strong></summary>

### Params
NOT USE

### Request-Header
NOT USE
### Request-Body
NOT USE

### Response-Body

| Name          | Type            | Description |
|---------------|-----------------|-------------|
| `title`       | `String`        | 문서 제목       |
| `synoym`      | `Array<String>` | 문서 제목 동의어들  |
| `content`     | `Content`       | 문서 내용 집합    |
| `contentHtml` | `String`        | 문서 내용의 Html |
| `date`        | `Long`          | 작성 시간       |
| `user`        | `String`        | 최종 작성자      |

#### 성공

> HTTP status codes 200(OK)

Example
```
{
    "title":"ACID",
    "synonym":  ["ACID", "Transaction ACID"],
    "content":{
        "contentHtml":"<p>..생략..</p>",
        "date":1651628620448,
        "user":"user"
        }
}
```

#### 실패 

> 서버 내부 오류:
> HTTP status codes 500(Internal Server Error)


</details>

<br/>

<details>
<summary><strong>[GET] /api/glossary/{title} 상세</strong></summary>

### Params

| Name    | Description |
|---------|-------------|
| `title` | 조회 할 문서 제목  |

### Request-Header

NOT USE

### Request-Body

NOT USE

### Response-Body

| Name | Type | Description |
| ---- | ---- | ----------- |
| `data` | `Array` | 생성된 일정 일자의 모든 일정 정보들 |
| `_id` | `ObjectId` | 일정 id |
| `startTime` | `String` | 일정 시작 시각 |
| `formatTime` | `String` | 날짜-시간 형식 포맷 변환 |
| `title` | `String` | 일정 제목 |
| `memo` | `String` | 메모 |

#### 성공

> HTTP status codes 200(OK)

Example
```
{
    "title":"ACID",
    "synonym":  ["ACID", "Transaction ACID"],
    "content":{
        "contentHtml":"<p>..생략..</p>",
        "date":1651628620448,
        "user":"user"
        }
}
```

#### 실패

> Parameter 오류:
> HTTP status codes 400(Bad Request)

> 문서 찾지 못함:
> HTTP status codes 404(Not Found)

> 서버 내부 오류:
> HTTP status codes 500(Internal Server Error)

</details>

<br/>

<details>
<summary><strong>[POST] /api/glossary/{title} 상세</strong></summary>

### Params

| Name    | Description |
|---------|-------------|
| `title` | 조회 할 문서 제목  |


### Request-Header

[JWT-Authorization API](https://github.com/DeokhunKim/JWT-Authorization) 참고

### Request-Body

| Name          | Type            | Description |
|---------------|-----------------|-------------|
| `title`       | `String`        | 문서 제목       |
| `synoym`      | `Array<String>` | 문서 제목 동의어들  |
| `content`     | `Content`       | 문서 내용 집합    |
| `contentHtml` | `String`        | 문서 내용의 Html |
| `date`        | `Long`          | 작성 시간       |
| `user`        | `String`        | 최종 작성자      |

```
{
    "title":"ACID",
    "synonym":  ["ACID", "Transaction ACID"],
    "content":{
        "contentHtml":"<p>..생략..</p>",
        "date":1651628620448,
        "user":"user"
        }
}
```

### Response-Body

#### 성공

> HTTP status codes 200(OK)


#### 실패


> Parameter 오류:
> HTTP status codes 400(Bad Request)

> 권한 인증 오류:
> HTTP status codes 401(Unauthorized)

> 문서 찾지 못함:
> HTTP status codes 404(Not Found)

> 서버 내부 오류:
> HTTP status codes 500(Internal Server Error)

</details>

<br/>

<details>
<summary><strong>[PATCH] /api/glossary/{title} 상세</strong></summary>

### Params

| Name    | Description |
|---------|-------------|
| `title` | 변경 전 문서 제목  |


### Request-Header

[JWT-Authorization API](https://github.com/DeokhunKim/JWT-Authorization) 참고

### Request-Body

| Name         | Type | Description |
|--------------| ---- |-------------|
| `afterTitle` | `String` | 변경 후 문서 제목  |
    |


### Response-Body


#### 성공

> HTTP status codes 200(OK)


#### 실패


> Parameter 오류:
> HTTP status codes 400(Bad Request)

> 권한 인증 오류:
> HTTP status codes 401(Unauthorized)

> 문서 찾지 못함:
> HTTP status codes 404(Not Found)

> 서버 내부 오류:
> HTTP status codes 500(Internal Server Error)

</details>

<br/>

<details>
<summary><strong>[PUT] /api/glossary/{title} 상세</strong></summary>

### Params

| Name    | Description |
|---------|-------------|
| `title` | 조회 할 문서 제목  |


### Request-Header

[JWT-Authorization API](https://github.com/DeokhunKim/JWT-Authorization) 참고

### Request-Body

| Name          | Type            | Description |
|---------------|-----------------|-------------|
| `title`       | `String`        | 문서 제목       |
| `synoym`      | `Array<String>` | 문서 제목 동의어들  |
| `content`     | `Content`       | 문서 내용 집합    |
| `contentHtml` | `String`        | 문서 내용의 Html |
| `date`        | `Long`          | 작성 시간       |
| `user`        | `String`        | 최종 작성자      |

```
{
    "title":"ACID",
    "synonym":  ["ACID", "Transaction ACID"],
    "content":{
        "contentHtml":"<p>..생략..</p>",
        "date":1651628620448,
        "user":"user"
        }
}
```

### Response-Body


#### 성공

> HTTP status codes 200(OK)


#### 실패


> Parameter 오류:
> HTTP status codes 400(Bad Request)

> 권한 인증 오류:
> HTTP status codes 401(Unauthorized)

> 문서 찾지 못함:
> HTTP status codes 404(Not Found)

> 서버 내부 오류:
> HTTP status codes 500(Internal Server Error)

</details>

<br/>

<details>
<summary><strong>[GET] /api/glossary/{title}/history 상세</strong></summary>
</details>

<br/>

### 인증 토큰
일부 요청은 API Gateway 를 통해 인증 토큰 검사를 합니다.

[Authorization API](https://github.com/DeokhunKim/JWT-Authorization) 를 통해서 인증 토큰을 획득 해서 요청 해주세요.
