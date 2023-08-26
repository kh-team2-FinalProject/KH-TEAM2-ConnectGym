
create table users(
  no int primary key auto_increment,
  user_id varchar(20) not null,
  user_pw varchar(20) not null,
  user_name varchar(20) not null,
  user_tel varchar(20) not null,
  user_address varchar(100) not null,
  reg_date datetime default now(),
  user_email varchar(40) not null
);
insert into users(user_id, user_pw, user_name, user_tel,user_address, user_email) values('aaaa', '1234', '추성훈', '010-1234-1234','경기도 의정부시 전좌로', '111@naver.com');
insert into users(user_id, user_pw, user_name, user_tel,user_address, user_email)  values('bbbb', '2345', '김연아', '010-2345-2345','서울시 강서구 화곡로', '222@naver.com');
insert into users(user_id, user_pw, user_name, user_tel,user_address, user_email)  values('cccc', '3456', '손흥민', '010-3456-3456','서울시 송파구 오금로', '333@naver.com');

insert into trainers(trainer_id,trainer_pw,trainer_name,trainer_tel,license,profile_img,trainer_info) values
('qwer','1234','심으뜸','010-1234-1234',1,'me.jpg','성실하게 가르치겠습니다');
insert into trainers(trainer_id,trainer_pw,trainer_name,trainer_tel,license,profile_img,trainer_info) values
('asdf','1234','김계란','010-1234-1234',2,'kim_me.jpg','항상 성실하게 가르치겠습니다');


insert into lessons(title, trainer_no, price, category, lesson_info, start_date, end_date) values('헬스헬스', 1, 10000, 1, '헬스입니다', '2023-08-23 00:00:00', '2024-08-23 00:00:00');
insert into lessons(title, trainer_no, price, category, lesson_info, start_date, end_date) values('득근득근', 2, 30000, 2, '득근합시다', '2023-08-25 01:12:19', '2023-08-26 01:12:19');

create table trainers (
  no int primary key auto_increment,
  trainer_id varchar(20) not null,
  trainer_pw varchar(20) not null,
  trainer_name varchar(20) not null,
  trainer_tel varchar(20) not null,
  license varchar(100) references licenses(no),
  profile_img varchar(100),
  trainer_info varchar(1000),
  reg_date datetime default now()
);

create table license (
no int primary key auto_increment,
trainer_no int references trainers(no),
content varchar(50)
);

# 수업 요일, 수업 시간, 수업 횟수 컬럼 필요
create table lessons (
	no int primary key auto_increment,
  title varchar(50) not null,
  trainer_no int references trainers(no),
  price int not null,
  category int not null,
  lesson_info varchar(1000),
  start_date datetime not null,
  end_date datetime not null
);

drop table enroll_detail;


create table enroll_detail(
	no int primary key auto_increment,
    user_no int references users(no),
    lesson_no int references lesson(no),

    enroll_key varchar(20) not null
);
select *from enroll_detail;
insert into enroll_detail(user_no,lesson_no,enroll_key) values (1,1,'test1234');
insert into enroll_detail(user_no,lesson_no,enroll_key) values (1,2,'test4567');
insert into enroll_detail(user_no,lesson_no,enroll_key) values (3,2,'test4567');
create table lesson_date(
	no int primary key auto_increment,
    lesson_no int references lessons(no)
);

create table lesson_time(
	no int primary key auto_increment,
    lesson_date_no int references lesson_date(no)
);


drop table rooms;

create table rooms (
no int primary key auto_increment,
enroll_detail_no int references enroll_detail(no),
room_name varchar(50) not null,
room_key varchar(50)
);

select * from trainers;
select * from users;
select * from lessons;
