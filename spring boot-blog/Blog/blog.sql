drop database if exists blog;
create database blog;
use blog;

create table account
(
    id           bigint primary key auto_increment  not null comment '账号ID',
    username     varchar(32)                        not null comment '用户名',
    password     char(32)                           not null comment '密码',
    account_type int(1)                             not null default 0 comment '账户类型：0-用户，1-管理员',
    create_time  datetime default CURRENT_TIMESTAMP not null comment '注册时间',
    last_update  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '最后修改时间',
    status       int(1)   default 0                 not null comment '状态：0-正常 1-禁用'

) engine innodb
  auto_increment = 100000
  charset 'utf8mb4' comment '账号信息表';

insert into account (username, password, account_type, create_time, last_update, status)
values ('admin', '123', 1, '2018-01-01 00:00:00', '2018-01-01 00:00:00', 0);
insert into account (username, password, account_type, create_time, last_update, status)
values ('user', '123', 0, '2018-01-01 00:00:00', '2018-01-01 00:00:00', 0);



create table account_info
(
    account_id         bigint primary key not null comment '账户ID',
    avatar             blob default null comment '头像地址',
    nickname           varchar(32) comment '昵称',
    sex                tinyint comment '性别',
    phone_num          char(11) comment '手机号',
    email              varchar(32) comment '邮箱',
    address            varchar(64)  default null comment '地址',
    personal_signature varchar(100) default null comment '个性签名'

) engine innodb
  charset 'utf8mb4' comment '用户信息表';

insert into account_info (account_id, sex, nickname, phone_num, email, address, personal_signature, avatar)
values (100000, 1, '管理员', '13800138000', 'd2e9@6e955.cn', '北京市市辖区大兴区黄村地区', 'GG', null);

drop table if exists article;
CREATE TABLE article
(
    id           bigint(20) primary key auto_increment comment '博客ID',
    account_id   bigint       not null comment '博主ID',
    title        varchar(200) not null comment '博客标题',
    summary      varchar(500) not null comment '摘要',
    content      mediumtext   not null comment '博客内容',
    category_id int(20)      not null comment '博客类型ID',
    status      int(1)      NOT NULL DEFAULT 3 COMMENT '博客状态：0-已发布；1-草稿；2-回收站；3-待审核；4-审核不通过 5-已删除',
    comment_open int(1)                default 0 comment '评论开启：0开启，1关闭',
    appreciation int(1)                default 0 comment '赞赏开启：0开启，1关闭',
    recommend    int(1)                default 0 comment '是否推荐：0推荐，1不推荐',
    reprint      int(1)                default 0 comment '是否转载：0 原创，1转载',
    reprint_url  varchar(256)          default null comment '转载地址',
    blog_img     varchar(256)          default null comment '博客封面图片路径',
    views        bigint                default 0 comment '点击数',
    comment_num  bigint                default 0 comment '评论数',
    likes        bigint                default 0 comment '点赞数',
    create_time  datetime     not null default CURRENT_TIMESTAMP comment '发表时间',
    last_update  datetime     not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '最后修改时间'

) engine innodb
  auto_increment = 100000
  charset 'utf8mb4' comment '博客文章信息表';




drop table if exists category;
CREATE TABLE category
(
    id        bigint(20) primary key auto_increment comment '博客类型ID',
    category_name varchar(50)  not null comment '博客类型名称',
    category_desc varchar(200) not null comment '博客类型描述'

) engine innodb
  charset 'utf8mb4' comment '博客类型信息表';


insert into category (category_name, category_desc)
values ('原创', '原创');
insert into category (category_name, category_desc)
values ('转载', '转载');
insert into category (category_name, category_desc)
values ('翻译', '翻译');
insert into category (category_name, category_desc)
values ('记录', '记录');
insert into category (category_name, category_desc)
values ('其他', '其他');



drop table if exists comment;
CREATE TABLE comment
(
    id                       bigint(20) primary key auto_increment comment '评论ID',
    article_id               bigint(20)   not null comment '博客ID',
    account_id               bigint(20)   not null comment '评论者ID',
    content                  varchar(200) not null comment '评论内容',
    root_comment_id          bigint                default -1 comment '根评论id',
    reply_comment_account_id bigint                default -1 comment '所回复的目标评论的accountId',
    reply_comment_id         bigint                default -1 comment '回复目标评论id',
    status                   int(1)                default 0 comment '删除（0代表未删除，1代表已删除）',
    comment_level            int(1)                default 0 comment '评论等级[ 1 一级评论 默认 ，2 二级评论]',
    praise_num               int(11)      not null default 0 comment '点赞数',
    top_status               tinyint(4)   not null default 0 comment '置顶状态[ 1 置顶，0 不置顶 默认 ]',
    create_time              datetime     not null default CURRENT_TIMESTAMP() comment '评论时间'
) engine innodb
  charset 'utf8mb4' comment '评论信息表';

insert into comment (article_id, account_id, content, root_comment_id, reply_comment_account_id, reply_comment_id,
                     status, comment_level, praise_num, top_status, create_time)
values (1, 100000, '第一篇博客的第一条评论', -1, -1, -1, 0, 1, 0, 0, '2018-01-01 00:00:00');
insert into comment (article_id, account_id, content, root_comment_id, reply_comment_account_id, reply_comment_id,
                     status, comment_level, praise_num, top_status, create_time)
values (2, 100000, '第一篇博客的第二条评论', -1, -1, -1, 0, 1, 0, 0, '2018-01-01 00:00:00');
insert into comment (article_id, account_id, content, root_comment_id, reply_comment_account_id, reply_comment_id,
                     status, comment_level, praise_num, top_status, create_time)
values (3, 100000, '第一篇博客的第三条评论', -1, -1, -1, 0, 1, 0, 0, '2018-01-01 00:00:00');

drop table if exists message;
CREATE TABLE message
(
    id          bigint(20) primary key auto_increment comment '留言ID',
    account_id  bigint(20) not null comment '留言接收者ID',
    content     mediumtext not null comment '留言内容',
    status      int(1)     not null default 0 comment '留言状态（0代表未读，1代表已读）',
    create_time datetime   not null default CURRENT_TIMESTAMP() comment '留言时间'
) engine innodb
  charset 'utf8mb4' comment '留言信息表';

insert into message (account_id, content, status, create_time)
values (100000, '欢迎来到博客系统', 0, '2018-01-01 00:00:00');
insert into message (account_id, content, status, create_time)
values (100001, '欢迎来到博客系统', 0, '2018-01-01 00:00:00');

drop table if exists praise;
CREATE TABLE praise
(
    id          bigint(20) primary key auto_increment comment '点赞ID',
    praise_for_id  bigint(20)  NOT NULL COMMENT '点赞的ID',
    account_id  bigint(20) not null comment '点赞者ID',
    praise_type  int(1)                default 0 not null comment '点赞类型 0-文章 1-评论',
    create_time datetime   not null default CURRENT_TIMESTAMP comment '点赞时间'
) engine innodb
  charset 'utf8mb4' comment '点赞信息表';




drop table if exists top;
CREATE TABLE top
(
    id          bigint(20) primary key auto_increment comment '置顶ID',
    article_id  bigint(20) not null comment '博客ID',
    create_time datetime   not null default CURRENT_TIMESTAMP comment '置顶时间'
) engine innodb
  charset 'utf8mb4' comment '置顶信息表';



drop table if exists tag;
CREATE TABLE tag
(
    id       bigint(20) primary key auto_increment comment '标签ID',
    tag_name varchar(50)  not null comment '标签名称',
    tag_desc varchar(200) not null comment '标签描述'
) engine innodb
  charset 'utf8mb4' comment '标签信息表';

insert into tag (tag_name, tag_desc)
values ('java', 'java是一种面向对象的编程语言');
insert into tag (tag_name, tag_desc)
values ('python', 'python是一种面向对象的编程语言');
insert into tag (tag_name, tag_desc)
values ('php', 'php是世界上最好的编程语言');
insert into tag (tag_name, tag_desc)
values ('c++', 'c++是一种面向过程的编程语言');
insert into tag (tag_name, tag_desc)
values ('c', 'c是一种面向过程的编程语言');
insert into tag (tag_name, tag_desc)
values ('go', 'go是一种编程语言');
insert into tag (tag_name, tag_desc)
values ('javascript', 'javascript是一种编程语言');
insert into tag (tag_name, tag_desc)
values ('编程语言', '编程语言是一种编程语言');
insert into tag (tag_name, tag_desc)
values ('数据库', '数据库是一种数据存储结构');
insert into tag (tag_name, tag_desc)
values ('框架', '框架是一种编程语言');
insert into tag (tag_name, tag_desc)
values ('算法', '算法是一种编程语言');
insert into tag (tag_name, tag_desc)
values ('网络', '网络是一种编程语言');
insert into tag (tag_name, tag_desc)
values ('编码', '编码是一种编程语言');
insert into tag (tag_name, tag_desc)
values ('编程', '编程是一种艺术，是一种生活');
insert into tag (tag_name, tag_desc)
values ('设计', '设计是一种艺术，是一种生活');
insert into tag (tag_name, tag_desc)
values ('生活', '生活是一种艺术，是一种生活');
insert into tag (tag_name, tag_desc)
values ('摄影', '摄影是一种艺术，是一种生活');
insert into tag (tag_name, tag_desc)
values ('旅行', '旅行是一种艺术，是一种生活');
insert into tag (tag_name, tag_desc)
values ('游戏', '游戏是一种艺术，是一种生活');
insert into tag (tag_name, tag_desc)
values ('动漫', '动漫是一种艺术，是一种生活');
insert into tag (tag_name, tag_desc)
values ('美食', '美食是一种艺术，是一种生活');
insert into tag (tag_name, tag_desc)
values ('时尚', '时尚是一种艺术，是一种生活');


drop table if exists article_tag;
CREATE TABLE article_tag
(
    id         bigint(20) primary key auto_increment comment '标签博客关系ID',
    tag_id     bigint(20) not null comment '标签ID',
    article_id bigint(20) not null comment '博客ID'
) engine innodb
  charset 'utf8' comment '标签博客关系表';

insert into article_tag (tag_id, article_id)
values (1, 100000);
insert into article_tag (tag_id, article_id)
values (2, 100000);
insert into article_tag (tag_id, article_id)
values (3, 100000);

drop table if exists friendship_chain;
create table friendship_chain
(
    id           int(11) primary key                  NOT NULL AUTO_INCREMENT COMMENT '主键',
    link_name    varchar(50)                          NOT NULL COMMENT '链接名',
    email        varchar(32)           DEFAULT NULL COMMENT '邮箱',
    status       int(1)   default 2                   NOT NULL COMMENT '状态 0-正常 1-删除 2-待审核',
    link_avatar  varchar(255)                         NOT NULL COMMENT '链接头像',
    link_address varchar(100)                         NOT NULL COMMENT '链接地址',
    link_intro   varchar(100)                         NOT NULL COMMENT '链接介绍',
    create_time  datetime default current_timestamp() NOT NULL COMMENT '创建时间'

) engine innodb
  charset 'utf8mb4' comment '友情链表';

insert into friendship_chain (link_name, link_avatar, link_address, link_intro, create_time)
values ('百度', 'https://www.baidu.com/img/bd_logo1.png', 'https://www.baidu.com', '百度是一个搜索引擎', '2020-01-01 00:00:00'),
       ('谷歌', 'https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png',
        'https://www.google.com', '谷歌是一个搜索引擎', '2020-01-01 00:00:00'),
       ('网易', 'https://www.163.com/favicon.ico', 'https://www.163.com', '网易是一个搜索引擎', '2020-01-01 00:00:00'),
       ('搜狗', 'https://www.sogou.com/images/logo/new/sogou.ico', 'https://www.sogou.com', '搜狗是一个搜索引擎',
        '2020-01-01 00:00:00'),
       ('必应', 'https://www.bing.com/images/branding/bing_logo_RGB_rgb.png', 'https://www.bing.com', '必应是一个搜索引擎',
        '2020-01-01 00:00:00'),
       ('网易', 'https://www.163.com/favicon.ico', 'https://www.163.com', '网易是一个搜索引擎', '2020-01-01 00:00:00'),
       ('搜狗', 'https://www.sogou.com/images/logo/new/sogou.ico', 'https://www.sogou.com', '搜狗是一个搜索引擎',
        '2020-01-01 00:00:00');

drop table if exists cron;
create table cron(
    id bigint primary key not null auto_increment comment '主键',
    cron varchar(30) not null comment '控制循环时间'
) engine innodb charset 'utf8mb4' comment '定时任务表'





