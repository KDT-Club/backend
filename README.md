# ClubHub Backend

대학 동아리의 가입과 운영, 커뮤니티 활동을 지원하는 Spring Boot 백엔드입니다. 회원 가입부터 동아리 개설·가입 승인, 게시글과 댓글, 이미지 업로드까지 필요한 API를 제공합니다.

**Java 17 · Spring Boot 3.2.5 · Spring Data JPA · MySQL · AWS S3**

## 주요 기능

| 영역 | 구현 내용 |
| --- | --- |
| 회원 | 회원 가입, 폼 로그인·로그아웃, 로그인 사용자 식별, 회원 정보 조회·수정·탈퇴 |
| 동아리 | 동아리 생성·조회·정보 수정·삭제, 내가 가입한 동아리 조회 |
| 가입 신청 | 지원서 제출·목록·상세 조회, 동아리 회장의 가입 승인·거절 |
| 동아리 회원 | 회원 목록·상세 조회, 등급 변경, 회장 위임, 회원 탈퇴 처리 |
| 커뮤니티 | 전체 자유게시판, 동아리 공지·활동·내부 게시판, 게시글과 댓글 CRUD |
| 파일 | S3 업로드용 Presigned URL 발급, 게시글 첨부파일 URL 저장 |

## 백엔드 구성

| 구분 | 사용 기술 |
| --- | --- |
| 언어·프레임워크 | Java 17, Spring Boot 3.2.5, Spring Web MVC |
| 데이터 접근 | Spring Data JPA, Hibernate, MySQL |
| 인증 | Spring Security 폼 로그인·세션, BCrypt 비밀번호 해싱 |
| 파일 저장 | Amazon S3, Spring Cloud AWS 3.1.1 |
| API 문서 | springdoc-openapi 2.1.0 |
| 빌드·테스트 | Gradle Wrapper 8.8, Spring Boot Test, JUnit 5 |

코드는 기능별 패키지 안에서 Controller, Service, Repository, Entity, DTO를 나누어 구성했습니다. REST API와 일부 서버 렌더링용 컨트롤러·템플릿이 함께 들어 있습니다.

```text
src/main/java/com/ac/su/
├── member/                  # 회원 정보와 사용자 인증
├── clubmember/              # 동아리 회원·등급·회장 위임
├── joinrequest/             # 가입 신청과 승인·거절
├── community/
│   ├── club/                # 동아리 정보
│   ├── board/               # 게시판 종류와 게시글 조회
│   ├── post/                # 게시글 작성·수정·삭제
│   └── attachment/          # 게시글 첨부파일 URL
├── comment/                 # 댓글
├── presignedUrl/            # S3 업로드 URL 발급
├── notification/            # 알림 모델과 기본 클래스
├── LoginController.java     # 회원 가입과 로그인 화면
└── SecurityConfig.java      # 폼 로그인·로그아웃·CORS 설정
```

### 구현 포인트

- **회원과 동아리의 관계**: `ClubMember`로 소속과 회원 등급을 관리합니다. 동아리를 만들면 생성자를 회장으로 등록하고, 가입을 승인하면 신청자를 일반 회원으로 추가합니다.
- **운영 규칙**: 일부 관리 API에서 동아리 회장 여부를 확인합니다. 회장은 위임 전에 동아리를 탈퇴하거나 계정을 삭제할 수 없도록 처리합니다.
- **게시판 분리**: 전체 자유게시판과 동아리별 공지·활동·내부 게시판을 구분하고, 게시글에 첨부파일 URL을 연결합니다.
- **S3 직접 업로드**: 클라이언트가 파일명으로 URL을 요청하면 서버가 `test/` 경로의 객체에 대해 3분 동안 유효한 PUT URL을 발급합니다. 클라이언트는 이 URL로 S3에 파일을 업로드하고, 게시글 작성 시 파일 URL을 전달합니다.

## 대표 API

아래 경로는 현재 컨트롤러에 선언된 매핑입니다. `club`과 `clubs`처럼 단수·복수 경로가 혼재하므로 연동 시 실제 매핑을 확인해야 합니다.

| 메서드 | 경로 | 기능 |
| --- | --- | --- |
| POST | `/signup` | 회원 가입 |
| POST | `/login` | Spring Security 폼 로그인 |
| GET | `/getUserId` | 로그인 사용자 ID 조회 |
| GET | `/clubs` | 전체 동아리 조회, `memberId`로 가입 동아리 조회 |
| POST | `/clubs/create/{memberId}` | 동아리 생성 |
| POST | `/clubs/{clubName}/applications` | 가입 신청 |
| POST | `/clubs/{clubId}/joinRequest/{requestId}/approveRequest` | 가입 승인 |
| POST | `/clubs/{clubId}/clubMember/{memberId}/changeStatus` | 회원 등급 변경 |
| GET | `/board/{board_id}/posts` | 게시판 글 목록 조회 |
| POST | `/board/1/posts` | 전체 자유게시판 글 작성 |
| POST | `/posts/{postId}/comments` | 댓글 작성 |
| GET | `/presigned-url?filename={filename}` | S3 업로드 URL 발급 |

회원 가입은 `name`, `username`(학번), `password`, `department`, `phone` 폼 값을 받습니다. 로그인은 `studentId`와 `password`를 사용합니다. 로그인 이후 요청은 세션 쿠키를 유지해야 합니다.

전체 매핑과 요청 DTO는 [컨트롤러와 도메인 코드](src/main/java/com/ac/su)에서 확인할 수 있습니다. 서버가 실행되면 기본 설정 기준으로 `/swagger-ui/index.html`에서 API 문서를 확인할 수 있습니다.

<details>
<summary><strong>로컬 실행 방법</strong></summary>

### 1. 준비

- JDK 17
- MySQL 데이터베이스와 접속 계정
- 사용할 S3 버킷·리전, 해당 버킷에 업로드할 수 있는 AWS 자격 증명

Gradle은 저장소의 Wrapper를 사용하므로 별도 설치가 필요하지 않습니다.

```bash
git clone https://github.com/KDT-Club/backend.git
cd backend
```

### 2. 외부 설정 파일 작성

저장소 폴더 밖에 `clubhub-local.properties`를 만듭니다. 아래 예시에서는 저장소의 바로 상위 폴더인 `../clubhub-local.properties`를 사용합니다. 예시 값을 자신의 개발 환경에 맞게 바꿉니다.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/clubhub?serverTimezone=Asia/Seoul
spring.datasource.username=YOUR_DB_USER
spring.datasource.password=YOUR_DB_PASSWORD

spring.cloud.aws.region.static=ap-northeast-2
spring.cloud.aws.s3.bucket=YOUR_BUCKET_NAME

# 로컬 HTTP 환경에서 세션 쿠키 사용
server.servlet.session.cookie.secure=false
server.servlet.session.cookie.same-site=lax
```

AWS 자격 증명은 실행 환경의 기본 자격 증명 체인으로 공급합니다. 로컬 AWS 프로필이나 `AWS_ACCESS_KEY_ID`, `AWS_SECRET_ACCESS_KEY` 환경변수를 사용할 수 있으며, 임시 자격 증명은 `AWS_SESSION_TOKEN`도 필요합니다. 실제 키와 비밀번호는 저장소에 추가하지 않습니다.

### 3. 서버 실행

**Windows PowerShell**

```powershell
$env:SPRING_CONFIG_ADDITIONAL_LOCATION = "file:../clubhub-local.properties"
.\gradlew.bat bootRun
```

**macOS / Linux**

```bash
export SPRING_CONFIG_ADDITIONAL_LOCATION=file:../clubhub-local.properties
./gradlew bootRun
```

기본 포트는 `8080`입니다. 프런트엔드를 연결할 때는 [SecurityConfig](src/main/java/com/ac/su/SecurityConfig.java)의 허용 Origin과 로그인·로그아웃 성공 URL을 실행 환경에 맞게 조정해야 합니다.

### 4. 게시판 초기 데이터

게시글 작성 로직은 아래 게시판 ID가 데이터베이스에 존재한다고 가정합니다. 현재 저장소에는 초기 데이터를 생성하는 스크립트가 없으므로 새 데이터베이스에서는 게시판 데이터를 먼저 준비해야 합니다.

| ID | `BoardType` | 용도 |
| --- | --- | --- |
| 1 | `GENERAL` | 전체 자유게시판 |
| 2 | `CLUB_NOTICE` | 동아리 공지 |
| 3 | `CLUB_ACTIVITY` | 동아리 활동 |
| 4 | `CLUB_INTERNAL` | 동아리 내부 자유게시판 |

기본 JPA 설정은 `ddl-auto=update`입니다. 테이블 갱신과 게시판 초기 데이터 입력은 별개입니다.

### 5. 테스트·빌드 명령

위 외부 설정 환경변수를 지정한 셸에서 실행합니다.

```powershell
# Windows
.\gradlew.bat test
.\gradlew.bat build
```

```bash
# macOS / Linux
./gradlew test
./gradlew build
```

현재 테스트는 `@SpringBootTest`의 `contextLoads()`이며, 애플리케이션 컨텍스트를 불러옵니다. DB와 AWS 설정이 필요하며 기능별 API 테스트를 갖춘 상태는 아닙니다.

</details>

## 현재 구현 범위

- `notification` 패키지는 모델과 기본 클래스만 있으며, 알림 API는 아직 구현되어 있지 않습니다.
- `SecurityConfig`에는 전체 경로 `permitAll`과 CSRF 비활성화 설정이 들어 있습니다. 일부 관리 API가 컨트롤러에서 회원 등급을 확인하며, 모든 API에 공통 접근 정책을 적용한 구성은 아닙니다.
