DROP DATABASE IF EXISTS planusdb;

CREATE DATABASE planusdb;

use planusdb;

DROP TABLE IF EXISTS user;

CREATE TABLE user (
    user_id VARCHAR(50) PRIMARY KEY COMMENT '사용자 고유 ID',
    username VARCHAR(50) NOT NULL COMMENT '사용자 이름',
    group_id BIGINT COMMENT '소속 그룹 ID',
    email VARCHAR(100) COMMENT '이메일 주소',
    phone VARCHAR(50) COMMENT '전화번호',
    login_id VARCHAR(20) COMMENT '로그인용 ID',
    password VARCHAR(255) COMMENT '로그인 비밀번호',
    role ENUM('ADMIN', 'USER') DEFAULT 'USER' COMMENT '권한 (ADMIN 또는 USER)'
);



DROP TABLE IF EXISTS board;

CREATE TABLE board(
    write_id INTEGER auto_increment primary key COMMENT '게시글 고유 ID',
    board_id VARCHAR(100) COMMENT '게시판 ID',
    group_id BIGINT COMMENT '소속 그룹 ID',
    user_id VARCHAR(50) COMMENT '작성자 ID',
    created_at DATETIME COMMENT '작성일시',
    title VARCHAR(255) COMMENT '게시글 제목',
    content LONGTEXT COMMENT '게시글 본문 (HTML 포함 가능)',
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

DROP TABLE IF EXISTS boardViewLog;

CREATE TABLE boardViewLog(
    view_log VARCHAR(255) primary key COMMENT '뷰 로그 고유 ID',
    user_id VARCHAR(50) COMMENT '조회자 ID',
    group_id BIGINT COMMENT '소속 그룹 ID',
    write_id INTEGER COMMENT '게시글 ID',
    view_time DATETIME COMMENT '조회 시각',
    FOREIGN KEY (write_id) REFERENCES board(write_id)
);

DROP TABLE IF EXISTS comment;

CREATE TABLE comment (
    comment_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '댓글 고유 ID',
    user_id VARCHAR(50) COMMENT '댓글 작성자 ID',
    board_id INTEGER COMMENT '댓글이 달린 게시글 ID',
    group_id BIGINT COMMENT '소속 그룹 ID',
    content TEXT NOT NULL COMMENT '댓글 내용',
    created_at DATETIME COMMENT '작성일시',
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (board_id) REFERENCES board(write_id)
);

DROP TABLE IF EXISTS calendar;

CREATE TABLE calendar (
    calendar_id VARCHAR(255) PRIMARY KEY COMMENT '일정 고유 식별자',
    title VARCHAR(255) COMMENT '일정 제목',
    content LONGTEXT COMMENT '일정 상세 내용 (HTML 포함 가능)',
    group_id BIGINT COMMENT '소속 그룹 ID',
    user_id VARCHAR(50) COMMENT '작성자 ID',
    start_date DATETIME COMMENT '일정 시작일',
    end_date DATETIME COMMENT '일정 종료일',
    status INTEGER DEFAULT 0 COMMENT '0: 예정, 1: 완료',
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

ALTER TABLE board ADD COLUMN status TINYINT DEFAULT 0;
-- 0: 정상, 1: 삭제

ALTER TABLE comment ADD COLUMN status TINYINT DEFAULT 0;
-- 0: 정상, 1: 삭제
ALTER TABLE board ADD COLUMN delete_time DATETIME null;
ALTER TABLE comment ADD COLUMN delete_time DATETIME null;

ALTER TABLE calendar ADD COLUMN view_status TINYINT DEFAULT 0;

ALTER TABLE boardViewLog ADD UNIQUE (user_id, write_id);


CREATE TABLE group_list (
                            group_id BIGINT,
                            # 이 메뉴가 속한 그룹의 ID. 그룹별로 메뉴를 구분하기 위해 필요.
                            group_name VARCHAR(50),
                            # 그룹의 이름
                            group_email VARCHAR(50)
);


CREATE TABLE board_menu (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        # 게시판 고유 ID (PK). AUTO_INCREMENT.
                        group_id BIGINT,
                        # 이 게시판이 속한 그룹 ID. 어떤 그룹 소속인지 구분용.
                        board_name VARCHAR(50),
                        # 게시판 이름. 예: “공지사항”, “Q&A”, “팀 회의록” 등.
                        board_path VARCHAR(100),
                        # 해당 메뉴를 눌렀을 때 이동할 URL 경로. 예: /group/101/board
                        sort_order INT,
                        # 사이드바에서 메뉴가 뜨는 순서. 낮을수록 위에 배치됨.
                        description TEXT
                        # 게시판에 대한 설명. 예: “팀 전체 공지사항을 올리는 공간입니다.”
);

ALTER TABLE board_menu ADD column status tinyint default 0;

ALTER TABLE group_list ADD column gitHubOwnerName VARCHAR(255);
ALTER TABLE group_list ADD column gitHubRepoName VARCHAR(255);
ALTER TABLE group_list ADD column gitHubToken VARCHAR(255);
ALTER TABLE group_list ADD column gitHubTokenDate VARCHAR(255);


CREATE TABLE gitHubCommit(
    commit_id VARCHAR(255) PRIMARY KEY ,
    commit_msg LONGTEXT,
    group_id BIGINT,
    user_name VARCHAR(255),
    user_email VARCHAR(255),
    commit_date DATETIME,
    commitURL VARCHAR(255),
    FOREIGN KEY (group_id) REFERENCES group_list(group_id));

CREATE TABLE gitHubPr(
    pr_id VARCHAR(255) PRIMARY KEY ,
    pr_title VARCHAR(255),
    group_id BIGINT,
    user_name VARCHAR(255),
    user_email VARCHAR(255),
    pr_date DATETIME,
    status ENUM('open', 'closed', 'merged') DEFAULT 'open' COMMENT 'PR 상태',
    prURL VARCHAR(255),
    FOREIGN KEY (group_id) REFERENCES group_list(group_id));