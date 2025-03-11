-- 고용 유형 데이터
INSERT INTO ed_employee_type(et_id, type) values(11, '정규직');
INSERT INTO ed_employee_type(et_id, type) values(12, '계약직');
INSERT INTO ed_employee_type(et_id, type) values(13, '시간제');

-- 부서 데이터
INSERT INTO ed_department(department_id, name) values(21, '교육팀');
INSERT INTO ed_department(department_id, name) values(22, '인사팀');
INSERT INTO ed_department(department_id, name) values(23, '경영팀');
INSERT INTO ed_department(department_id, name) values(24, '영업팀');

-- 직위 데이터
INSERT INTO ed_position(position_id, name) values(31, '사원');
INSERT INTO ed_position(position_id, name) values(32, '대리');
INSERT INTO ed_position(position_id, name) values(33, '과장');
INSERT INTO ed_position(position_id, name) values(34, '부장');


-- 학생 데이터
INSERT INTO ed_member(member_id, name, email, tel, pwd)
values(101, 'user1', 'user1@test.com', '010-1111-1111', sha2('1111', 256));
INSERT INTO ed_student(student_id, jumin, final_level, major, school)
values(101, '101', '학사', '컴퓨터공학', '한국대학교');

INSERT INTO ed_member(member_id, name, email, tel, pwd)
values(102, 'user2', 'user2@test.com', '010-1111-1112', sha2('1111', 256));
INSERT INTO ed_student(student_id, jumin, final_level, major, school)
values(102, '102', '학사', '국문학과', '대한대학교');

INSERT INTO ed_member(member_id, name, email, tel, pwd)
values(103, 'user3', 'user3@test.com', '010-1111-1113', sha2('1111', 256));
INSERT INTO ed_student(student_id, jumin, final_level, major, school)
values(103, '103', '석사', '기계공학', '독도대학교');



-- 강사 데이터
INSERT INTO ed_member(member_id, name, email, tel, pwd)
values(201, 'teacher1', 'teacher1@test.com', '010-2222-1111', sha2('1111', 256));
INSERT INTO ed_teacher(teacher_id, et_id, photo)
values(201, 11, 'a.jpg');

INSERT INTO ed_member(member_id, name, email, tel, pwd)
values(202, 'teacher2', 'teacher2@test.com', '010-2222-1112', sha2('1111', 256));
INSERT INTO ed_teacher(teacher_id, et_id, photo)
values(202, 12, 'b.jpg');

INSERT INTO ed_member(member_id, name, email, tel, pwd)
values(203, 'teacher3', 'teacher3@test.com', '010-2222-1113', sha2('1111', 256));
INSERT INTO ed_teacher(teacher_id, et_id, photo)
values(203, 13, 'c.jpg');


-- 매니저 데이터
INSERT INTO ed_member(member_id, name, email, tel, pwd)
values(301, 'manager1', 'manager1@test.com', '010-3333-1111', sha2('1111', 256));
INSERT INTO ed_manager(member_id, department_id, position_id)
values(301, 21, 31);

INSERT INTO ed_member(member_id, name, email, tel, pwd)
values(302, 'manager2', 'manager2@test.com', '010-3333-1112', sha2('1111', 256));
INSERT INTO ed_manager(member_id, department_id, position_id)
values(302, 21, 32);

-- 게시글 데이터
INSERT INTO ed_board(board_id, title, content, member_id)
VALUES(1, '제목입니다1', '내용입니다1', 101);

INSERT INTO ed_board(board_id, title, content, member_id)
VALUES(2, '제목입니다2', '내용입니다2', 201);

INSERT INTO ed_board(board_id, title, content, member_id)
VALUES(3, '제목입니다3', '내용입니다3', 301);

-- 첨부파일 데이터
INSERT INTO ed_attach_file(af_id, board_id, filename)
VALUES(1, 1, 'a.jpg');
INSERT INTO ed_attach_file(af_id, board_id, filename)
VALUES(2, 1, 'b.jpg');
INSERT INTO ed_attach_file(af_id, board_id, filename)
VALUES(3, 3, 'c.jpg');




-- 학생 조회 
select
    m.member_id,
    m.name,
    m.email,
    s.jumin,
    s.final_level,
    s.major
from ed_member m 
    inner join ed_student s on m.member_id = s.student_id;

-- 강사 조회 
select
    m.member_id,
    m.name,
    m.email,
    t.et_id,
    et.type
from ed_member m 
    inner join ed_teacher t on m.member_id = t.teacher_id
    inner join ed_employee_type et on t.et_id = et.et_id;


-- 매니저 조회 
select
    m.member_id,
    m.name,
    m.email,
    mgr.fax,
    dept.department_id,
    dept.name dept_name,
    pos.position_id,
    pos.name pos_name
from ed_member m 
    inner join ed_manager mgr on m.member_id = mgr.member_id
    inner join ed_department dept on mgr.department_id = dept.department_id
    inner join ed_position pos on mgr.position_id = pos.position_id;

-- 게시글 조회
select
    b.board_id,
    b.title,
    b.content,
    b.create_date,
    b.view_count,
    m.member_id,
    m.name,
    af.af_id,
    af.filename
from ed_board b
    inner join ed_member m on b.member_id = m.member_id
    left outer join ed_attach_file af on b.board_id = af.board_id;


