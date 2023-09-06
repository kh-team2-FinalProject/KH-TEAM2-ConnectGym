/*
============================================================
                        테이블
============================================================
*/

create table chat_message (
    no bigint not null auto_increment,
    content varchar(255),
    send_at datetime(6),
    sender varchar(255),
    chatroom_no bigint,
    primary key (no)
);


create table chatroom (
    no bigint not null auto_increment,
    member_no bigint not null,
    trainer_no bigint not null,
    primary key (no)
);


create table cs_category (
    id integer not null auto_increment,
    ctgy varchar(255) not null,
    primary key (id)
);


create table customer_service (
    no bigint not null auto_increment,
    category_id bigint not null,
    content varchar(255) not null,
    faq_datetime datetime(6),
    title varchar(255) not null,
    user_id varchar(255) not null,
    primary key (no)
);


create table follows (
    no bigint not null auto_increment,
    from_user_no bigint,
    to_trainer_no bigint,
    primary key (no)
);


create table foods (
    food_cd bigint not null auto_increment,
    animal_plant varchar(255),
    choc double precision,
    fat double precision,
    food_nm varchar(255),
    food_size double precision,
    kcal double precision,
    nat double precision,
    prot double precision,
    sat_fat double precision,
    sugar double precision,
    trans_fat double precision,
    primary key (food_cd)
);


create table gym_rooms (
    no bigint not null auto_increment,
    room_name varchar(255),
    room_status varchar(255),
    order_detail_no bigint,
    primary key (no)
);


create table hibernate_sequence (
    next_val bigint
);


insert into hibernate_sequence values ( 1 );


create table lessons (
    no bigint not null auto_increment,
    category integer not null,
    end_date date,
    lesson_img varchar(255),
    lesson_info varchar(255),
    price integer not null,
    start_date date,
    title varchar(255),
    title_code varchar(255),
    trainer_no bigint,
    primary key (no)
);


create table licenses (
    no bigint not null,
    license_img varchar(255),
    trainer_no bigint,
    primary key (no)
);


create table likes (
    no bigint not null auto_increment,
    lesson_no bigint,
    user_no bigint,
    primary key (no)
);


create table notice (
    no bigint not null auto_increment,
    content varchar(255) not null,
    notice_datetime datetime(6),
    title varchar(255) not null,
    top_con integer not null,
    primary key (no)
);


create table order_progress_details (
    no bigint not null auto_increment,
    lesson_no bigint not null comment '레슨 번호',
    order_progress_no bigint not null comment '결제 예정 테이블 번호',
    primary key (no)
) comment='주문할 때 사용하는 정보 중 상세 정보를 담는 테이블';


create table order_detail (
    no bigint not null auto_increment,
    enroll_key bigint not null,
    lesson_no bigint not null,
    order_no varchar(255) not null,
    primary key (no)
) comment='주문 상세 내역';


create table order_progress (
    no bigint not null auto_increment,
    order_no varchar(255) not null comment '주문 번호',
    price bigint not null comment '결제 예정 금액',
    primary key (no)
) comment='주문할 때 사용되는 테이블';


create table orders (
    no varchar(255) not null,
    day_of_payment datetime(6) not null comment '주문한 날짜 및 시간',
    order_pay bigint not null comment '총 결제 금액',
    type varchar(50) not null,
    member_no bigint not null,
    primary key (no)
) comment='주문 내역';


create table review (
    review_id bigint not null,
    reviewer_id bigint,
    primary key (review_id)
);


create table trainers (
    no bigint not null auto_increment,
    info_content varchar(255),
    info_title varchar(255),
    profile_img varchar(255),
    reg_date date,
    trainer_email varchar(255),
    trainer_id varchar(255),
    trainer_name varchar(255),
    trainer_pw varchar(255),
    trainer_tel varchar(255),
    primary key (no)
);


create table users (
    no bigint not null auto_increment,
    reg_date datetime(6),
    user_address varchar(255),
    user_email varchar(255) not null,
    user_id varchar(255) not null,
    user_name varchar(255) not null,
    user_pw varchar(255) not null,
    user_tel varchar(255),
    primary key (no)
);

/*
============================================================
                        외래 키
============================================================
*/

-- alter table follows
--     drop index uk_follow_from_to;


-- alter table follows
--     add constraint uk_follow_from_to unique (from_user_no, to_trainer_no);


-- alter table likes
--     drop index uk_like_from_to;


-- alter table likes
--     add constraint uk_like_from_to unique (user_no, lesson_no);


-- alter table order_progress
--     drop index UK_gd6prp02vdbsxeeeht32j9pwi;


-- alter table order_progress
--     add constraint UK_gd6prp02vdbsxeeeht32j9pwi unique (order_no);


-- alter table users
--     drop index UK_33uo7vet9c79ydfuwg1w848f;


-- alter table users
--     add constraint UK_33uo7vet9c79ydfuwg1w848f unique (user_email);


-- alter table users
--     drop index UK_6efs5vmce86ymf5q7lmvn2uuf;


-- alter table users
--     add constraint UK_6efs5vmce86ymf5q7lmvn2uuf unique (user_id);


-- alter table chat_message
--     add constraint FKlpchqac9jmnwg2jw48078afrr
--     foreign key (chatroom_no)
--     references chatroom (no);


-- alter table chatroom
--     add constraint FK2q5fous1weajklha9lmsdb5mq
--     foreign key (member_no)
--     references users (no);


-- alter table chatroom
--     add constraint FKaxy1616bqnig7y11ljypbpt2h
--     foreign key (trainer_no)
--     references trainers (no);


-- alter table follows
--     add constraint FK4x4ipcxlp9iui3mrpoan26nb0
--     foreign key (from_user_no)
--     references users (no);


-- alter table follows
--     add constraint FK9dypyxc6xpv67cg6d6u34q2rv
--     foreign key (to_trainer_no)
--     references trainers (no);


-- alter table gym_rooms
--     add constraint FKkc87tsv3yhk5s9i4420ipmcp9
--     foreign key (order_detail_no)
--     references order_detail (no);


-- alter table lessons
--     add constraint FKgydnv0q1pjhrs1dmqpwqte7qs
--     foreign key (trainer_no)
--     references trainers (no);


-- alter table licenses
--     add constraint FK73v20t5fiweuf5a6ell7l6436
--     foreign key (trainer_no)
--     references trainers (no);


-- alter table likes
--     add constraint FKl5xt92lnb2erhvcrwx9uorcrb
--     foreign key (lesson_no)
--     references lessons (no);


-- alter table likes
--     add constraint FK3m1x7y3mktd2stpwlwj988f74
--     foreign key (user_no)
--     references users (no);


-- alter table order_progress_details
--     add constraint FKci1asg9v90hi6reaao6xc58tq
--     foreign key (lesson_no)
--     references lessons (no);


-- alter table order_progress_details
--     add constraint FK5ubd56oyi84dx1erybubwy72r
--     foreign key (order_progress_no)
--     references order_progress (no);


-- alter table order_detail
--     add constraint FK2d24id998293k8on0nu6jh8gx
--     foreign key (lesson_no)
--     references lessons (no);


-- alter table order_detail
--     add constraint FKjtu6fp4ci5yr3asstcds9w86c
--     foreign key (order_no)
--     references orders (no);


-- alter table orders
--     add constraint FKl4cimm01c3d98r3ii352qrd48
--     foreign key (member_no)
--     references users (no);
