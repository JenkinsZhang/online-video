drop table if exists `chapter`;
create table `chapter` (
                           `id` char(8) not null comment 'id',
                           `course_id` char(8) comment 'course id',
                           `name` varchar(50) comment 'course name',
                           primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='chapter';

insert into `chapter` (id, course_id, name) values ('00000001', '00000000', 'Test Chapter 01');
insert into `chapter` (id, course_id, name) values ('00000002', '00000000', 'Test Chapter 02');
insert into `chapter` (id, course_id, name) values ('00000003', '00000000', 'Test Chapter 03');
insert into `chapter` (id, course_id, name) values ('00000004', '00000000', 'Test Chapter 04');
insert into `chapter` (id, course_id, name) values ('00000005', '00000000', 'Test Chapter 05');
insert into `chapter` (id, course_id, name) values ('00000006', '00000000', 'Test Chapter 06');
insert into `chapter` (id, course_id, name) values ('00000007', '00000000', 'Test Chapter 07');
insert into `chapter` (id, course_id, name) values ('00000008', '00000000', 'Test Chapter 08');
insert into `chapter` (id, course_id, name) values ('00000009', '00000000', 'Test Chapter 09');
insert into `chapter` (id, course_id, name) values ('00000010', '00000000', 'Test Chapter 10');
insert into `chapter` (id, course_id, name) values ('00000011', '00000000', 'Test Chapter 11');
insert into `chapter` (id, course_id, name) values ('00000012', '00000000', 'Test Chapter 12');
insert into `chapter` (id, course_id, name) values ('00000013', '00000000', 'Test Chapter 13');
insert into `chapter` (id, course_id, name) values ('00000014', '00000000', 'Test Chapter 14');

drop table if exists `section`;
create table `section` (
                           `id` char(8) not null default '' comment 'id',
                           `title` varchar(50) not null comment 'Title',
                           `course_id` char(8) comment 'Course|course.id',
                           `chapter_id` char(8) comment 'Chapter|chapter.id',
                           `video` varchar(200) comment 'Video',
                           `time` int comment 'Length|second',
                           `charge` char(1) comment 'Charge|C Charge; F Free',
                           `sort` int comment 'Order',
                           `created_at` datetime(3) comment 'Creation Time',
                           `updated_at` datetime(3) comment 'Updated Time',
                           primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='section';

# alter table `section` add column (`vod` char(32) comment 'vod|阿里云vod');

insert into `section` (id, title, course_id, chapter_id, video, time, charge, sort, created_at, updated_at)
values ('00000001', 'Test Section 01', '00000001', '00000000', '', 500, 'F', 1, now(), now());
insert into `section` (id, title, course_id, chapter_id, video, time, charge, sort, created_at, updated_at)
values ('00000002', 'Test Section 02', '00000002', '00000000', '', 500, 'F', 1, now(), now());
insert into `section` (id, title, course_id, chapter_id, video, time, charge, sort, created_at, updated_at)
values ('00000003', 'Test Section 03', '00000003', '00000000', '', 500, 'F', 1, now(), now());

drop table if exists course;
create table course (
                        id char(8) not null default '' comment 'id',
                        name varchar(50) not null comment 'name',
                        summary varchar(2000) comment 'summary',
                        time int default 0 comment 'time length| seconds',
                        price decimal(8,2) default 0.00 comment 'price（yuan）',
                        image varchar(100) comment 'cover',
                        level char(1) comment 'level|enums[CourseLevelEnum]：ONE("1", "Junior"),TWO("2", "Medium"),THREE("3", "Senior")',
                        charge char(1) comment 'charge|enums[CourseChargeEnum]：CHARGE("C", "Charge"),FREE("F", "Free")',
                        status char(1) comment 'status|enums[CourseStatusEnum]：PUBLISH("P", "Publish"),DRAFT("D", "Draft")',
                        enroll integer default 0 comment 'registration number',
                        sort int comment 'sort',
                        created_at datetime(3) comment 'created time',
                        updated_at datetime(3) comment 'updated time',
                        primary key (id)
) engine=innodb default charset=utf8mb4 comment='course';

insert into course (id, name, summary, time, price, image, level, charge, status, enroll, sort, created_at, updated_at)
values ('00000001', 'Test course 01', 'This is a test course', 7200, 19.9, '', 1, 'C', 'P', 100, 0, now(), now());