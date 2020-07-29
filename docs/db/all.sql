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

drop table if exists `category`;
create table `category` (
                            `id` char(8) not null default '' comment 'id',
                            `parent` char(8) not null default '' comment 'parent id',
                            `name` varchar(50) not null comment 'name',
                            `sort` int comment 'sort',
                            primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='category';

insert into `category` (id, parent, name, sort) values ('00000100', '00000000', 'front-end', 100);
insert into `category` (id, parent, name, sort) values ('00000101', '00000100', 'html/css', 101);
insert into `category` (id, parent, name, sort) values ('00000102', '00000100', 'javascript', 102);
insert into `category` (id, parent, name, sort) values ('00000103', '00000100', 'vue.js', 103);
insert into `category` (id, parent, name, sort) values ('00000104', '00000100', 'react.js', 104);
insert into `category` (id, parent, name, sort) values ('00000105', '00000100', 'angular', 105);
insert into `category` (id, parent, name, sort) values ('00000106', '00000100', 'node.js', 106);
insert into `category` (id, parent, name, sort) values ('00000107', '00000100', 'jquery', 107);
insert into `category` (id, parent, name, sort) values ('00000108', '00000100', 'applet', 108);
insert into `category` (id, parent, name, sort) values ('00000200', '00000000', 'back-end', 200);
insert into `category` (id, parent, name, sort) values ('00000201', '00000200', 'java', 201);
insert into `category` (id, parent, name, sort) values ('00000202', '00000200', 'springboot', 202);
insert into `category` (id, parent, name, sort) values ('00000203', '00000200', 'spring cloud', 203);
insert into `category` (id, parent, name, sort) values ('00000204', '00000200', 'ssm', 204);
insert into `category` (id, parent, name, sort) values ('00000205', '00000200', 'python', 205);
insert into `category` (id, parent, name, sort) values ('00000206', '00000200', 'spider', 206);
insert into `category` (id, parent, name, sort) values ('00000300', '00000000', 'mobile development', 300);
insert into `category` (id, parent, name, sort) values ('00000301', '00000300', 'android', 301);
insert into `category` (id, parent, name, sort) values ('00000302', '00000300', 'ios', 302);
insert into `category` (id, parent, name, sort) values ('00000303', '00000300', 'react native', 303);
insert into `category` (id, parent, name, sort) values ('00000304', '00000300', 'ionic', 304);
insert into `category` (id, parent, name, sort) values ('00000400', '00000000', 'state of the art', 400);
insert into `category` (id, parent, name, sort) values ('00000401', '00000400', 'microservices', 401);
insert into `category` (id, parent, name, sort) values ('00000402', '00000400', 'block chain', 402);
insert into `category` (id, parent, name, sort) values ('00000403', '00000400', 'machine learning', 403);
insert into `category` (id, parent, name, sort) values ('00000404', '00000400', 'deep learning', 404);
insert into `category` (id, parent, name, sort) values ('00000405', '00000400', 'data analysis & digging', 405);
insert into `category` (id, parent, name, sort) values ('00000500', '00000000', 'cloud computing & big data', 500);
insert into `category` (id, parent, name, sort) values ('00000501', '00000500', 'big data', 501);
insert into `category` (id, parent, name, sort) values ('00000502', '00000500', 'hadoop', 502);
insert into `category` (id, parent, name, sort) values ('00000503', '00000500', 'spark', 503);
insert into `category` (id, parent, name, sort) values ('00000504', '00000500', 'hbase', 504);
insert into `category` (id, parent, name, sort) values ('00000505', '00000500', 'aliyun', 505);
insert into `category` (id, parent, name, sort) values ('00000506', '00000500', 'docker', 506);
insert into `category` (id, parent, name, sort) values ('00000507', '00000500', 'kubernetes', 507);
insert into `category` (id, parent, name, sort) values ('00000600', '00000000', 'ops & testing', 600);
insert into `category` (id, parent, name, sort) values ('00000601', '00000600', 'ops', 601);
insert into `category` (id, parent, name, sort) values ('00000602', '00000600', 'auto ops', 602);
insert into `category` (id, parent, name, sort) values ('00000603', '00000600', 'middle components', 603);
insert into `category` (id, parent, name, sort) values ('00000604', '00000600', 'linux', 604);
insert into `category` (id, parent, name, sort) values ('00000605', '00000600', 'testing', 605);
insert into `category` (id, parent, name, sort) values ('00000606', '00000600', 'automatic testing', 606);
insert into `category` (id, parent, name, sort) values ('00000700', '00000000', 'databases', 700);
insert into `category` (id, parent, name, sort) values ('00000701', '00000700', 'mysql', 701);
insert into `category` (id, parent, name, sort) values ('00000702', '00000700', 'redis', 702);
insert into `category` (id, parent, name, sort) values ('00000703', '00000700', 'mongodb', 703);