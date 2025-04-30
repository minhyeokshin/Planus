DROP DATABASE IF EXISTS planusdb;

CREATE DATABASE planusdb;

use planusdb;

DROP TABLE IF EXISTS user;

CREATE TABLE user (
    user_id VARCHAR(50) PRIMARY KEY COMMENT '사용자 고유 ID',
    username VARCHAR(50) NOT NULL COMMENT '사용자 이름',
    email VARCHAR(100) COMMENT '이메일 주소',
    phone VARCHAR(50) COMMENT '전화번호',
    login_id VARCHAR(20) COMMENT '로그인용 ID',
    password VARCHAR(20) COMMENT '로그인 비밀번호',
    role ENUM('ADMIN', 'USER') DEFAULT 'USER' COMMENT '권한 (ADMIN 또는 USER)'
);

DROP TABLE IF EXISTS pull_request;

CREATE TABLE pull_request (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT 'PR 고유 ID',
    github_pr_id INT UNIQUE COMMENT 'GitHub PR ID',
    title VARCHAR(255) NOT NULL COMMENT 'PR 제목',
    url VARCHAR(255) COMMENT 'PR URL',
    author VARCHAR(100) COMMENT '작성자',
    status ENUM('open', 'closed', 'merged') DEFAULT 'open' COMMENT 'PR 상태',
    created_at DATETIME COMMENT '생성일시'
);

DROP TABLE IF EXISTS board;

CREATE TABLE board(
    write_id VARCHAR(100) primary key COMMENT '게시글 고유 ID',
    board_id VARCHAR(100) COMMENT '게시판 ID',
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
    write_id VARCHAR(100) COMMENT '게시글 ID',
    view_time DATETIME COMMENT '조회 시각',
    FOREIGN KEY (write_id) REFERENCES board(write_id)
);

DROP TABLE IF EXISTS comment;

CREATE TABLE comment (
    comment_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '댓글 고유 ID',
    user_id VARCHAR(50) COMMENT '댓글 작성자 ID',
    board_id VARCHAR(100) COMMENT '댓글이 달린 게시글 ID',
    content TEXT NOT NULL COMMENT '댓글 내용',
    created_at DATETIME COMMENT '작성일시',
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (board_id) REFERENCES board(write_id)
);

DROP TABLE IF EXISTS calender;

CREATE TABLE calender (
    calendar_id VARCHAR(255) PRIMARY KEY COMMENT '일정 고유 식별자',
    title VARCHAR(255) COMMENT '일정 제목',
    content LONGTEXT COMMENT '일정 상세 내용 (HTML 포함 가능)',
    user_id VARCHAR(50) COMMENT '작성자 ID',
    start_date DATETIME COMMENT '일정 시작일',
    end_date DATETIME COMMENT '일정 종료일',
    status INTEGER DEFAULT 0 COMMENT '0: 예정, 1: 완료',
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);
