| 기능         | url                                        | httpmethod | request                                                     | response                                                                                        | HttpStatus |
|--------------|--------------------------------------------|------------|-------------------------------------------------------------|-------------------------------------------------------------------------------------------------|------------|
|유저생성|/members|`POST`|"name" : "String","email" : "String","passworld" : "String", "birthdate" : "String","nickname" : "String","comment" : "String"}|"id" : "Number",name" : "String","email" : "String","birthdate" : "String","nickname" : "String","comment" : "String","createdDate" : "DateTime"}| `201` |
|유저수정 |  /members/{id} |  `PATCH` | {"nickname" : "String","comment" : "String"}| {"name" : "String","email" : "String","nickname" : "String","comment" : "String"updatedDate" : "DateTime"} |`200`|
| 유저 단일 조회 |   /members/{id}   | `GET` | 없음 | {"nickname" : "String","comment" : "String"} | `200`|
| 유저 목록 조회 |  /members      | `GET`      |없음| {"nickname" : "String","comment" : "String"}  | `200` |
| 유저 삭제    |    /members/{id}   | `DELETE` | {"passoword" : "String"} | 없음 | `200` |
| 댓글 작성 | /comment | `post` | {"content":"String"} | {"id":"Long", "content":"String", "createAt":"LocalDateTime","updateAt":"LocalDateTime"} | `201` |
| 댓글 수정 | /comment/{id} | `put` | {"content":"String"} | {"id":"Long", "content":"String", "createAt":"LocalDateTime","updateAt":"LocalDateTime"} | `200` |
| 댓글 조회 | /comment | `get` | 없음 |  {"id":"Long", "content":"String", "createAt":"LocalDateTime","updateAt":"LocalDateTime"} .... | `200` |
| 댓글 삭제 | /comment/{id} | `delete` | 없음 | 없음 | 200
|게시물 생성| /newsfeed | `post` | {"title":"String","content":"String"} | {"id":"Long", "title":"String", "content":"String", "CreateAt":"LocalDateTime", "UpdateAt":"LocalDateTime"} | `201` |
| 게시물 조회 |  /newsfeed/{id} | `get` | 없음 | {"id":"Long", "title":"String", "content":"String", "CreateAt":"LocalDateTime", "UpdateAt":"LocalDateTime"} | `200` |
| 게시물 수정 | /newsfeed/{id} | `put` | {"title":"String", "content":"String"}|{"title":"String", "content":"String", "CreateAt":"LocalDateTime", "UpdateAt":"LocalDateTime"} | `200` |
| 게시물 삭제 | /newsfeed/{id} | `delete` | 없음 | 없음 | `200` |
| 팔로우 |/members/{sendMemberID}/status| `post`|없음|없음|`200`|
|  팔로우 취소 |  /members/{memberID}/status/{statusID} | `Delete`  | 없음 | 없음 |`200`|
| 친구 조회|/members/{memberID}/status| `Get`| 없음 | 없음 |List(Status)| `200` |
| 좋아요 | /like | - | -|-|-|
| 좋아요 삭제 | /like/{id} |-|-|-|-|
