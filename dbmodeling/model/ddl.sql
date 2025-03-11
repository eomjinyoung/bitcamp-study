-- 수강신청
DROP TABLE IF EXISTS ed_application RESTRICT;

-- 학생
DROP TABLE IF EXISTS ed_student RESTRICT;

-- 과목
DROP TABLE IF EXISTS ed_lecture RESTRICT;

-- 강의실
DROP TABLE IF EXISTS ed_classroom RESTRICT;

-- 강사
DROP TABLE IF EXISTS ed_teacher RESTRICT;

-- 매니저
DROP TABLE IF EXISTS ed_manager RESTRICT;

-- 은행
DROP TABLE IF EXISTS ed_bank RESTRICT;

-- 센터
DROP TABLE IF EXISTS ed_center RESTRICT;

-- 강의실사진
DROP TABLE IF EXISTS ed_classroom_photo RESTRICT;

-- 주소
DROP TABLE IF EXISTS ed_address RESTRICT;

-- 강사과목
DROP TABLE IF EXISTS ed_teacher_lecture RESTRICT;

-- 회원
DROP TABLE IF EXISTS ed_member RESTRICT;

-- 고용유형
DROP TABLE IF EXISTS ed_employee_type RESTRICT;

-- 부서
DROP TABLE IF EXISTS ed_department RESTRICT;

-- 직위
DROP TABLE IF EXISTS ed_position RESTRICT;

-- 수강신청상태
DROP TABLE IF EXISTS ed_application_status RESTRICT;

-- 게시판
DROP TABLE IF EXISTS ed_board RESTRICT;

-- 첨부파일
DROP TABLE IF EXISTS ed_attach_file RESTRICT;

-- 수강신청
CREATE TABLE ed_application (
	application_id INTEGER  NOT NULL COMMENT '수강신청번호', -- 수강신청번호
	student_id     INTEGER  NOT NULL COMMENT '학생번호', -- 학생번호
	lecture_id     INTEGER  NOT NULL COMMENT '과목번호', -- 과목번호
	create_date    DATETIME NOT NULL DEFAULT current_timestamp() COMMENT '수강신청일', -- 수강신청일
	as_id          INTEGER  NULL     COMMENT '상태번호' -- 상태번호
)
COMMENT '수강신청';

-- 수강신청
ALTER TABLE ed_application
	ADD CONSTRAINT PK_ed_application -- 수강신청 기본키
	PRIMARY KEY (
	application_id -- 수강신청번호
	);

ALTER TABLE ed_application
	MODIFY COLUMN application_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '수강신청번호';

-- 학생
CREATE TABLE ed_student (
	student_id     INTEGER      NOT NULL COMMENT '학생번호', -- 학생번호
	jumin          VARCHAR(13)  NOT NULL COMMENT '주민번호', -- 주민번호
	address_id     INTEGER      NULL     COMMENT '주소번호', -- 주소번호
	detail_address VARCHAR(255) NULL     COMMENT '상세주소', -- 상세주소
	bank_id        INTEGER      NULL     COMMENT '은행번호', -- 은행번호
	account        VARCHAR(20)  NULL     COMMENT '계좌', -- 계좌
	final_level    VARCHAR(50)  NOT NULL COMMENT '최종학력', -- 최종학력
	major          VARCHAR(50)  NOT NULL COMMENT '전공', -- 전공
	school         VARCHAR(50)  NOT NULL COMMENT '최종학교' -- 최종학교
)
COMMENT '학생';

-- 학생
ALTER TABLE ed_student
	ADD CONSTRAINT PK_ed_student -- 학생 기본키
	PRIMARY KEY (
	student_id -- 학생번호
	);

-- 학생 유니크 인덱스
CREATE UNIQUE INDEX UIX_ed_student
	ON ed_student ( -- 학생
		jumin ASC -- 주민번호
	);

-- 학생 유니크 인덱스2
CREATE UNIQUE INDEX UIX_ed_student2
	ON ed_student ( -- 학생
		account ASC -- 계좌
	);

-- 과목
CREATE TABLE ed_lecture (
	lecture_id   INTEGER      NOT NULL COMMENT '과목번호', -- 과목번호
	title        VARCHAR(255) NOT NULL COMMENT '과목명', -- 과목명
	description  MEDIUMTEXT   NOT NULL COMMENT '설명', -- 설명
	capacity     INTEGER      NOT NULL COMMENT '수용인원', -- 수용인원
	start_date   DATE         NOT NULL COMMENT '시작일', -- 시작일
	end_date     DATE         NOT NULL COMMENT '종료일', -- 종료일
	total_hours  INTEGER      NOT NULL COMMENT '총강의시간', -- 총강의시간
	classroom_id INTEGER      NULL     COMMENT '강의실번호', -- 강의실번호
	member_id    INTEGER      NULL     COMMENT '매니저번호' -- 매니저번호
)
COMMENT '과목';

-- 과목
ALTER TABLE ed_lecture
	ADD CONSTRAINT PK_ed_lecture -- 과목 기본키
	PRIMARY KEY (
	lecture_id -- 과목번호
	);

ALTER TABLE ed_lecture
	MODIFY COLUMN lecture_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '과목번호';

-- 강의실
CREATE TABLE ed_classroom (
	classroom_id INTEGER     NOT NULL COMMENT '강의실번호', -- 강의실번호
	center_id    INTEGER     NOT NULL COMMENT '센터번호', -- 센터번호
	name         VARCHAR(50) NOT NULL COMMENT '교실명' -- 교실명
)
COMMENT '강의실';

-- 강의실
ALTER TABLE ed_classroom
	ADD CONSTRAINT PK_ed_classroom -- 강의실 기본키
	PRIMARY KEY (
	classroom_id -- 강의실번호
	);

ALTER TABLE ed_classroom
	MODIFY COLUMN classroom_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '강의실번호';

-- 강사
CREATE TABLE ed_teacher (
	teacher_id INTEGER      NOT NULL COMMENT '강사번호', -- 강사번호
	photo      VARCHAR(255) NOT NULL COMMENT '사진', -- 사진
	et_id      INTEGER      NOT NULL COMMENT '고용유형번호' -- 고용유형번호
)
COMMENT '강사';

-- 강사
ALTER TABLE ed_teacher
	ADD CONSTRAINT PK_ed_teacher -- 강사 기본키
	PRIMARY KEY (
	teacher_id -- 강사번호
	);

-- 매니저
CREATE TABLE ed_manager (
	member_id     INTEGER     NOT NULL COMMENT '매니저번호', -- 매니저번호
	department_id INTEGER     NOT NULL COMMENT '부서번호', -- 부서번호
	position_id   INTEGER     NOT NULL COMMENT '직위번호', -- 직위번호
	fax           VARCHAR(30) NULL     COMMENT '팩스' -- 팩스
)
COMMENT '매니저';

-- 매니저
ALTER TABLE ed_manager
	ADD CONSTRAINT PK_ed_manager -- 매니저 기본키
	PRIMARY KEY (
	member_id -- 매니저번호
	);

-- 은행
CREATE TABLE ed_bank (
	bank_id INTEGER     NOT NULL COMMENT '은행번호', -- 은행번호
	name    VARCHAR(50) NOT NULL COMMENT '은행명' -- 은행명
)
COMMENT '은행';

-- 은행
ALTER TABLE ed_bank
	ADD CONSTRAINT PK_ed_bank -- 은행 기본키
	PRIMARY KEY (
	bank_id -- 은행번호
	);

-- 은행 유니크 인덱스
CREATE UNIQUE INDEX UIX_ed_bank
	ON ed_bank ( -- 은행
		name ASC -- 은행명
	);

ALTER TABLE ed_bank
	MODIFY COLUMN bank_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '은행번호';

-- 센터
CREATE TABLE ed_center (
	center_id INTEGER     NOT NULL COMMENT '센터번호', -- 센터번호
	name      VARCHAR(50) NOT NULL COMMENT '강의센터명' -- 강의센터명
)
COMMENT '센터';

-- 센터
ALTER TABLE ed_center
	ADD CONSTRAINT PK_ed_center -- 센터 기본키
	PRIMARY KEY (
	center_id -- 센터번호
	);

-- 센터 유니크 인덱스
CREATE UNIQUE INDEX UIX_ed_center
	ON ed_center ( -- 센터
		name ASC -- 강의센터명
	);

ALTER TABLE ed_center
	MODIFY COLUMN center_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '센터번호';

-- 강의실사진
CREATE TABLE ed_classroom_photo (
	cp_id        INTEGER      NOT NULL COMMENT '강의실사진번호', -- 강의실사진번호
	classroom_id INTEGER      NOT NULL COMMENT '강의실번호', -- 강의실번호
	photo        VARCHAR(255) NOT NULL COMMENT '사진파일경로' -- 사진파일경로
)
COMMENT '강의실사진';

-- 강의실사진
ALTER TABLE ed_classroom_photo
	ADD CONSTRAINT PK_ed_classroom_photo -- 강의실사진 기본키
	PRIMARY KEY (
	cp_id -- 강의실사진번호
	);

ALTER TABLE ed_classroom_photo
	MODIFY COLUMN cp_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '강의실사진번호';

-- 주소
CREATE TABLE ed_address (
	address_id    INTEGER      NOT NULL COMMENT '주소번호', -- 주소번호
	postno        VARCHAR(10)  NOT NULL COMMENT '우편번호', -- 우편번호
	basic_address VARCHAR(255) NOT NULL COMMENT '기본주소' -- 기본주소
)
COMMENT '주소';

-- 주소
ALTER TABLE ed_address
	ADD CONSTRAINT PK_ed_address -- 주소 기본키
	PRIMARY KEY (
	address_id -- 주소번호
	);

ALTER TABLE ed_address
	MODIFY COLUMN address_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '주소번호';

-- 강사과목
CREATE TABLE ed_teacher_lecture (
	lecture_id INTEGER NOT NULL COMMENT '과목번호', -- 과목번호
	teacher_id INTEGER NOT NULL COMMENT '강사번호' -- 강사번호
)
COMMENT '강사과목';

-- 강사과목
ALTER TABLE ed_teacher_lecture
	ADD CONSTRAINT PK_ed_teacher_lecture -- 강사과목 기본키
	PRIMARY KEY (
	lecture_id, -- 과목번호
	teacher_id  -- 강사번호
	);

-- 회원
CREATE TABLE ed_member (
	member_id   INTEGER      NOT NULL COMMENT '회원번호', -- 회원번호
	name        VARCHAR(50)  NOT NULL COMMENT '이름', -- 이름
	email       VARCHAR(40)  NOT NULL COMMENT '이메일', -- 이메일
	tel         VARCHAR(30)  NOT NULL COMMENT '전화', -- 전화
	create_date DATETIME     NOT NULL DEFAULT current_timestamp() COMMENT '등록일', -- 등록일
	pwd         VARCHAR(100) NOT NULL COMMENT '암호', -- 암호
	photo       VARCHAR(255) NULL     COMMENT '사진' -- 사진
)
COMMENT '회원';

-- 회원
ALTER TABLE ed_member
	ADD CONSTRAINT PK_ed_member -- 회원 기본키
	PRIMARY KEY (
	member_id -- 회원번호
	);

-- 회원 유니크 인덱스
CREATE UNIQUE INDEX UIX_ed_member
	ON ed_member ( -- 회원
		email ASC -- 이메일
	);

-- 회원 유니크 인덱스2
CREATE UNIQUE INDEX UIX_ed_member2
	ON ed_member ( -- 회원
		tel ASC -- 전화
	);

-- 회원 인덱스
CREATE INDEX IX_ed_member
	ON ed_member( -- 회원
		name ASC -- 이름
	);

ALTER TABLE ed_member
	MODIFY COLUMN member_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '회원번호';

-- 고용유형
CREATE TABLE ed_employee_type (
	et_id INTEGER     NOT NULL COMMENT '고용유형번호', -- 고용유형번호
	type  VARCHAR(50) NOT NULL COMMENT '고용명' -- 고용명
)
COMMENT '고용유형';

-- 고용유형
ALTER TABLE ed_employee_type
	ADD CONSTRAINT PK_ed_employee_type -- 고용유형 기본키
	PRIMARY KEY (
	et_id -- 고용유형번호
	);

-- 고용유형 유니크 인덱스
CREATE UNIQUE INDEX UIX_ed_employee_type
	ON ed_employee_type ( -- 고용유형
		type ASC -- 고용명
	);

ALTER TABLE ed_employee_type
	MODIFY COLUMN et_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '고용유형번호';

-- 부서
CREATE TABLE ed_department (
	department_id INTEGER     NOT NULL COMMENT '부서번호', -- 부서번호
	name          VARCHAR(50) NOT NULL COMMENT '부서명' -- 부서명
)
COMMENT '부서';

-- 부서
ALTER TABLE ed_department
	ADD CONSTRAINT PK_ed_department -- 부서 기본키
	PRIMARY KEY (
	department_id -- 부서번호
	);

-- 부서 유니크 인덱스
CREATE UNIQUE INDEX UIX_ed_department
	ON ed_department ( -- 부서
		name ASC -- 부서명
	);

ALTER TABLE ed_department
	MODIFY COLUMN department_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '부서번호';

-- 직위
CREATE TABLE ed_position (
	position_id INTEGER     NOT NULL COMMENT '직위번호', -- 직위번호
	name        VARCHAR(50) NOT NULL COMMENT '직위명' -- 직위명
)
COMMENT '직위';

-- 직위
ALTER TABLE ed_position
	ADD CONSTRAINT PK_ed_position -- 직위 기본키
	PRIMARY KEY (
	position_id -- 직위번호
	);

-- 직위 유니크 인덱스
CREATE UNIQUE INDEX UIX_ed_position
	ON ed_position ( -- 직위
		name ASC -- 직위명
	);

ALTER TABLE ed_position
	MODIFY COLUMN position_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '직위번호';

-- 수강신청상태
CREATE TABLE ed_application_status (
	as_id INTEGER     NOT NULL COMMENT '상태번호', -- 상태번호
	name  VARCHAR(50) NOT NULL COMMENT '상태명', -- 상태명
	etc   TEXT        NULL     COMMENT '비고' -- 비고
)
COMMENT '수강신청상태';

-- 수강신청상태
ALTER TABLE ed_application_status
	ADD CONSTRAINT PK_ed_application_status -- 수강신청상태 기본키
	PRIMARY KEY (
	as_id -- 상태번호
	);

ALTER TABLE ed_application_status
	MODIFY COLUMN as_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '상태번호';

-- 게시판
CREATE TABLE ed_board (
	board_id    INTEGER      NOT NULL COMMENT '게시판번호', -- 게시판번호
	member_id   INTEGER      NOT NULL COMMENT '작성자', -- 작성자
	title       VARCHAR(255) NOT NULL COMMENT '제목', -- 제목
	content     MEDIUMTEXT   NOT NULL COMMENT '내용', -- 내용
	create_date DATETIME     NULL     DEFAULT current_timestamp() COMMENT '작성일', -- 작성일
	view_count  INTEGER      NULL     DEFAULT 0 COMMENT '조회수' -- 조회수
)
COMMENT '게시판';

-- 게시판
ALTER TABLE ed_board
	ADD CONSTRAINT PK_ed_board -- 게시판 기본키
	PRIMARY KEY (
	board_id -- 게시판번호
	);

ALTER TABLE ed_board
	MODIFY COLUMN board_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '게시판번호';

-- 첨부파일
CREATE TABLE ed_attach_file (
	af_id    INTEGER      NOT NULL COMMENT '첨부파일번호', -- 첨부파일번호
	board_id INTEGER      NOT NULL COMMENT '게시판번호', -- 게시판번호
	filename VARCHAR(255) NOT NULL COMMENT '파일명' -- 파일명
)
COMMENT '첨부파일';

-- 첨부파일
ALTER TABLE ed_attach_file
	ADD CONSTRAINT PK_ed_attach_file -- 첨부파일 기본키
	PRIMARY KEY (
	af_id -- 첨부파일번호
	);

ALTER TABLE ed_attach_file
	MODIFY COLUMN af_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '첨부파일번호';

-- 수강신청
ALTER TABLE ed_application
	ADD CONSTRAINT FK_ed_student_TO_ed_application -- 학생 -> 수강신청
	FOREIGN KEY (
	student_id -- 학생번호
	)
	REFERENCES ed_student ( -- 학생
	student_id -- 학생번호
	);

-- 수강신청
ALTER TABLE ed_application
	ADD CONSTRAINT FK_ed_lecture_TO_ed_application -- 과목 -> 수강신청
	FOREIGN KEY (
	lecture_id -- 과목번호
	)
	REFERENCES ed_lecture ( -- 과목
	lecture_id -- 과목번호
	);

-- 수강신청
ALTER TABLE ed_application
	ADD CONSTRAINT FK_ed_application_status_TO_ed_application -- 수강신청상태 -> 수강신청
	FOREIGN KEY (
	as_id -- 상태번호
	)
	REFERENCES ed_application_status ( -- 수강신청상태
	as_id -- 상태번호
	);

-- 학생
ALTER TABLE ed_student
	ADD CONSTRAINT FK_ed_bank_TO_ed_student -- 은행 -> 학생
	FOREIGN KEY (
	bank_id -- 은행번호
	)
	REFERENCES ed_bank ( -- 은행
	bank_id -- 은행번호
	);

-- 학생
ALTER TABLE ed_student
	ADD CONSTRAINT FK_ed_address_TO_ed_student -- 주소 -> 학생
	FOREIGN KEY (
	address_id -- 주소번호
	)
	REFERENCES ed_address ( -- 주소
	address_id -- 주소번호
	);

-- 학생
ALTER TABLE ed_student
	ADD CONSTRAINT FK_ed_member_TO_ed_student -- 회원 -> 학생
	FOREIGN KEY (
	student_id -- 학생번호
	)
	REFERENCES ed_member ( -- 회원
	member_id -- 회원번호
	);

-- 과목
ALTER TABLE ed_lecture
	ADD CONSTRAINT FK_ed_classroom_TO_ed_lecture -- 강의실 -> 과목
	FOREIGN KEY (
	classroom_id -- 강의실번호
	)
	REFERENCES ed_classroom ( -- 강의실
	classroom_id -- 강의실번호
	);

-- 과목
ALTER TABLE ed_lecture
	ADD CONSTRAINT FK_ed_manager_TO_ed_lecture -- 매니저 -> 과목
	FOREIGN KEY (
	member_id -- 매니저번호
	)
	REFERENCES ed_manager ( -- 매니저
	member_id -- 매니저번호
	);

-- 강의실
ALTER TABLE ed_classroom
	ADD CONSTRAINT FK_ed_center_TO_ed_classroom -- 센터 -> 강의실
	FOREIGN KEY (
	center_id -- 센터번호
	)
	REFERENCES ed_center ( -- 센터
	center_id -- 센터번호
	);

-- 강사
ALTER TABLE ed_teacher
	ADD CONSTRAINT FK_ed_member_TO_ed_teacher -- 회원 -> 강사
	FOREIGN KEY (
	teacher_id -- 강사번호
	)
	REFERENCES ed_member ( -- 회원
	member_id -- 회원번호
	);

-- 강사
ALTER TABLE ed_teacher
	ADD CONSTRAINT FK_ed_employee_type_TO_ed_teacher -- 고용유형 -> 강사
	FOREIGN KEY (
	et_id -- 고용유형번호
	)
	REFERENCES ed_employee_type ( -- 고용유형
	et_id -- 고용유형번호
	);

-- 매니저
ALTER TABLE ed_manager
	ADD CONSTRAINT FK_ed_member_TO_ed_manager -- 회원 -> 매니저
	FOREIGN KEY (
	member_id -- 매니저번호
	)
	REFERENCES ed_member ( -- 회원
	member_id -- 회원번호
	);

-- 매니저
ALTER TABLE ed_manager
	ADD CONSTRAINT FK_ed_department_TO_ed_manager -- 부서 -> 매니저
	FOREIGN KEY (
	department_id -- 부서번호
	)
	REFERENCES ed_department ( -- 부서
	department_id -- 부서번호
	);

-- 매니저
ALTER TABLE ed_manager
	ADD CONSTRAINT FK_ed_position_TO_ed_manager -- 직위 -> 매니저
	FOREIGN KEY (
	position_id -- 직위번호
	)
	REFERENCES ed_position ( -- 직위
	position_id -- 직위번호
	);

-- 강의실사진
ALTER TABLE ed_classroom_photo
	ADD CONSTRAINT FK_ed_classroom_TO_ed_classroom_photo -- 강의실 -> 강의실사진
	FOREIGN KEY (
	classroom_id -- 강의실번호
	)
	REFERENCES ed_classroom ( -- 강의실
	classroom_id -- 강의실번호
	);

-- 강사과목
ALTER TABLE ed_teacher_lecture
	ADD CONSTRAINT FK_ed_teacher_TO_ed_teacher_lecture -- 강사 -> 강사과목
	FOREIGN KEY (
	teacher_id -- 강사번호
	)
	REFERENCES ed_teacher ( -- 강사
	teacher_id -- 강사번호
	);

-- 강사과목
ALTER TABLE ed_teacher_lecture
	ADD CONSTRAINT FK_ed_lecture_TO_ed_teacher_lecture -- 과목 -> 강사과목
	FOREIGN KEY (
	lecture_id -- 과목번호
	)
	REFERENCES ed_lecture ( -- 과목
	lecture_id -- 과목번호
	);

-- 게시판
ALTER TABLE ed_board
	ADD CONSTRAINT FK_ed_member_TO_ed_board -- 회원 -> 게시판
	FOREIGN KEY (
	member_id -- 작성자
	)
	REFERENCES ed_member ( -- 회원
	member_id -- 회원번호
	);

-- 첨부파일
ALTER TABLE ed_attach_file
	ADD CONSTRAINT FK_ed_board_TO_ed_attach_file -- 게시판 -> 첨부파일
	FOREIGN KEY (
	board_id -- 게시판번호
	)
	REFERENCES ed_board ( -- 게시판
	board_id -- 게시판번호
	);