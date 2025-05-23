# 🗓️ Planus - 협업 일정 관리 플랫폼


**Planus**는 소규모 개발팀을 위한 일정 관리 및 GitHub 활동 추적 중심의 협업 보조 플랫폼입니다.  
단순한 CRUD를 넘어, 실사용 흐름을 고려한 **보안, 성능, 배포, API 연동**까지 직접 설계/구현했습니다.

---

## 🙋 왜 이 프로젝트를 만들었는가

> “실무에서 개발자는 혼자 일하지 않는다.”  
Planus는 실제 협업에서 필요한 기능들을 고민하고,  
단순 기능 구현을 넘어서 **현업에 가까운 구조로 설계하고자 시작한 프로젝트**입니다.

- 백엔드 개발자로서 **인증, 세션, 데이터 흐름**을 통제할 수 있어야 한다
- 협업 도구는 **신뢰성과 추적 가능성**이 생명이다
- 포트폴리오는 **기능이 아니라 실전 설계 경험을 보여줘야 한다**

---


## 🔗 배포 주소

👉 https://planus.site

(HTTPS 적용 완료, 실서버 환경 배포)

---

## ✅ 주요 기능

### 🔐 사용자 인증
- Spring Security 기반 로그인/회원가입
- `BCrypt` 해싱(Cost factor: 12) + 커스텀 인증 객체로 세션 구성
- 세션 기반 보안 구조 설계 (JWT 확장 고려 구조)
- ✏️ [구현 예정] 로그아웃 처리 / 아이디·비밀번호 찾기

### 👥 그룹 & 일정 관리
- 그룹 생성, 참여 기능
- 그룹 단위 일정 등록/수정/삭제
- 일정 등록 시 관리자 **메일 자동 발송**

### 📝 게시판
- 게시글 작성, 수정, 삭제
- **페이지네이션 지원**
- **검색 기능**: 제목 / 작성자 / 글번호 기준 (캐시 기반 필터링)

### 🔁 GitHub 연동 (진행 중)
- 그룹 생성 시 GitHub 레포 URL 등록
- GitHub REST API 활용: 커밋 / PR 정보 자동 수집
- 향후 **유저별 활동 통계 시각화** 예정

---

## ⚙️ 기술 스택

| 구분       | 내용 |
|------------|------|
| Backend    | Java 17, Spring Boot, Spring Security, MyBatis |
| Frontend   | Thymeleaf (Spring MVC 기반 서버 사이드 렌더링) |
| DB         | MySQL |
| Infra      | AWS EC2 (Ubuntu 22.04), Nginx, Let’s Encrypt (SSL) |
| DevOps     | Certbot, systemd, crontab (자동 실행 및 인증서 갱신) |
| API        | GitHub REST API (커밋/PR 수집) |
| 기타       | Gmail SMTP (메일 전송), SingleTon Cache (성능 개선)

---

## 🛠️ 주요 구조

- `SecurityConfig`: 세션 기반 커스텀 인증 설정
- `CustomUserDetails`: 세션에서 활용되는 유저 정보 객체
- `BoardService`: 캐시 기반 게시글 페이징/검색 처리
- `GitHubService`: 외부 연동 API 호출 및 DTO 처리

---

## 💬 사용 시나리오

1. 그룹 생성 → 회원가입 → 로그인  
2. 그룹 일정 등록 → 관리자 이메일 발송  
3. 게시판에서 팀원과 커뮤니케이션  
4. GitHub 연동 → 팀 커밋/PR 자동 기록  
5. 그룹별 활동 리포트 제공 (기획 중)

---

## 🔐 보안 요소

- HTTPS 전체 적용 (Let's Encrypt + Nginx)
- BCrypt 암호화 (Cost 12 적용)
- Spring Security 기반 세션 관리
- 토큰 방식(JWT) 구조도 확장 고려 설계

---

## 🧑‍💻 개발자

| 이름 | GitHub | 역할 |
|------|--------|------|
| 신민혁 | [github.com/shinminhyeok](https://github.com/shinminhyeok) | 백엔드, 인프라, 전반 |

**1인 개발**, 전체 시스템 구조, 배포, 테스트까지 직접 구현  
> “단순히 돌아가는 게 아니라, **신뢰받는 시스템을 만들고 싶었습니다.”

---

## 🚀 향후 개발 예정

- GitHub 활동 통계 시각화
- 마이페이지 → 유저 활동 통계, 가입 그룹 목록
- JWT + 모바일 대응 구조 확장
- 공지사항/댓글 시스템 추가
- CI/CD 환경 구성

---

## 📌 시연 영상 / GIF

> 영상 첨부 예정

---

## 📎 기타

- 개발 기간: 2025.05 ~  (진행중)
