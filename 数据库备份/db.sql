prompt PL/SQL Developer import file
prompt Created on 2015年3月31日 by Administrator
set feedback off
set define off
prompt Creating TAB_CERTTYPE...
create table TAB_CERTTYPE
(
  id      NUMBER(11) not null,
  content VARCHAR2(20)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column TAB_CERTTYPE.id
  is 'ID';
comment on column TAB_CERTTYPE.content
  is '证件类型';
alter table TAB_CERTTYPE
  add constraint TAB_CERTTYPE_PK primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt Creating TAB_CITY...
create table TAB_CITY
(
  id     NUMBER(11) not null,
  cityid VARCHAR2(6),
  city   VARCHAR2(50),
  father VARCHAR2(6)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column TAB_CITY.id
  is 'ID';
comment on column TAB_CITY.cityid
  is '市标志';
comment on column TAB_CITY.city
  is '市名称';
comment on column TAB_CITY.father
  is '省份标识';
alter table TAB_CITY
  add constraint TAB_CITY_PK primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt Creating TAB_PROVINCE...
create table TAB_PROVINCE
(
  id         NUMBER(11) not null,
  provinceid VARCHAR2(6),
  province   VARCHAR2(40)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column TAB_PROVINCE.id
  is 'ID';
comment on column TAB_PROVINCE.provinceid
  is '省份标识';
comment on column TAB_PROVINCE.province
  is '省份名称';
alter table TAB_PROVINCE
  add constraint TAB_PROVINCE_PK primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt Creating TAB_USERTYPE...
create table TAB_USERTYPE
(
  id      NUMBER(11) not null,
  content VARCHAR2(40) not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column TAB_USERTYPE.id
  is 'ID';
comment on column TAB_USERTYPE.content
  is '旅客类型';
alter table TAB_USERTYPE
  add constraint TAB_USERTYPE_PK primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt Creating TAB_USER...
create table TAB_USER
(
  id         NUMBER(11) not null,
  username   VARCHAR2(30) not null,
  password   VARCHAR2(50) not null,
  rule       VARCHAR2(2) default '2' not null,
  realname   VARCHAR2(50) not null,
  sex        CHAR(1) default '1' not null,
  city       NUMBER(11) not null,
  cert_type  NUMBER(11) not null,
  cert       VARCHAR2(50) not null,
  birthday   DATE not null,
  user_type  NUMBER(11),
  content    VARCHAR2(3000),
  status     CHAR(1) default '1' not null,
  login_ip   VARCHAR2(50),
  image_path VARCHAR2(200)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column TAB_USER.id
  is 'ID';
comment on column TAB_USER.username
  is '用户名';
comment on column TAB_USER.password
  is '密码';
comment on column TAB_USER.rule
  is '权限(1、管理员 2、普通用户)';
comment on column TAB_USER.realname
  is '真实姓名';
comment on column TAB_USER.sex
  is '性别(1、男 2、女)';
comment on column TAB_USER.city
  is '市信息字典ID值';
comment on column TAB_USER.cert_type
  is '证件类型字典ID值';
comment on column TAB_USER.cert
  is '证件号码';
comment on column TAB_USER.birthday
  is '生日';
comment on column TAB_USER.user_type
  is '旅客类型字典ID值';
comment on column TAB_USER.content
  is '备注信息';
comment on column TAB_USER.status
  is '用户状态（0、无效  1、有效 ）';
comment on column TAB_USER.login_ip
  is '登陆IP';
comment on column TAB_USER.image_path
  is '用户头像路径';
alter table TAB_USER
  add constraint TAB_USER_PK primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table TAB_USER
  add constraint TAB_USER_UK unique (USERNAME)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table TAB_USER
  add constraint TAB_USER_CERTTYPE_FK foreign key (CERT_TYPE)
  references TAB_CERTTYPE (ID);
alter table TAB_USER
  add constraint TAB_USER_CITY_FK foreign key (CITY)
  references TAB_CITY (ID);
alter table TAB_USER
  add constraint TAB_USER_USERTYPE_FK foreign key (USER_TYPE)
  references TAB_USERTYPE (ID);

prompt Disabling triggers for TAB_CERTTYPE...
alter table TAB_CERTTYPE disable all triggers;
prompt Disabling triggers for TAB_CITY...
alter table TAB_CITY disable all triggers;
prompt Disabling triggers for TAB_PROVINCE...
alter table TAB_PROVINCE disable all triggers;
prompt Disabling triggers for TAB_USERTYPE...
alter table TAB_USERTYPE disable all triggers;
prompt Disabling triggers for TAB_USER...
alter table TAB_USER disable all triggers;
prompt Disabling foreign key constraints for TAB_USER...
alter table TAB_USER disable constraint TAB_USER_CERTTYPE_FK;
alter table TAB_USER disable constraint TAB_USER_CITY_FK;
alter table TAB_USER disable constraint TAB_USER_USERTYPE_FK;
prompt Loading TAB_CERTTYPE...
insert into TAB_CERTTYPE (id, content)
values (1, '二代身份证');
insert into TAB_CERTTYPE (id, content)
values (2, '港澳通行证');
insert into TAB_CERTTYPE (id, content)
values (3, '台湾通行证');
insert into TAB_CERTTYPE (id, content)
values (4, '护照');
commit;
prompt 4 records loaded
prompt Loading TAB_CITY...
insert into TAB_CITY (id, cityid, city, father)
values (1, '110100', '北京市', '110000');
insert into TAB_CITY (id, cityid, city, father)
values (3, '120100', '天津市', '120000');
insert into TAB_CITY (id, cityid, city, father)
values (5, '130100', '石家庄市', '130000');
insert into TAB_CITY (id, cityid, city, father)
values (6, '130200', '唐山市', '130000');
insert into TAB_CITY (id, cityid, city, father)
values (7, '130300', '秦皇岛市', '130000');
insert into TAB_CITY (id, cityid, city, father)
values (8, '130400', '邯郸市', '130000');
insert into TAB_CITY (id, cityid, city, father)
values (9, '130500', '邢台市', '130000');
insert into TAB_CITY (id, cityid, city, father)
values (10, '130600', '保定市', '130000');
insert into TAB_CITY (id, cityid, city, father)
values (11, '130700', '张家口市', '130000');
insert into TAB_CITY (id, cityid, city, father)
values (12, '130800', '承德市', '130000');
insert into TAB_CITY (id, cityid, city, father)
values (13, '130900', '沧州市', '130000');
insert into TAB_CITY (id, cityid, city, father)
values (14, '131000', '廊坊市', '130000');
insert into TAB_CITY (id, cityid, city, father)
values (15, '131100', '衡水市', '130000');
insert into TAB_CITY (id, cityid, city, father)
values (16, '140100', '太原市', '140000');
insert into TAB_CITY (id, cityid, city, father)
values (17, '140200', '大同市', '140000');
insert into TAB_CITY (id, cityid, city, father)
values (18, '140300', '阳泉市', '140000');
insert into TAB_CITY (id, cityid, city, father)
values (19, '140400', '长治市', '140000');
insert into TAB_CITY (id, cityid, city, father)
values (20, '140500', '晋城市', '140000');
insert into TAB_CITY (id, cityid, city, father)
values (21, '140600', '朔州市', '140000');
insert into TAB_CITY (id, cityid, city, father)
values (22, '140700', '晋中市', '140000');
insert into TAB_CITY (id, cityid, city, father)
values (23, '140800', '运城市', '140000');
insert into TAB_CITY (id, cityid, city, father)
values (24, '140900', '忻州市', '140000');
insert into TAB_CITY (id, cityid, city, father)
values (25, '141000', '临汾市', '140000');
insert into TAB_CITY (id, cityid, city, father)
values (26, '141100', '吕梁市', '140000');
insert into TAB_CITY (id, cityid, city, father)
values (27, '150100', '呼和浩特市', '150000');
insert into TAB_CITY (id, cityid, city, father)
values (28, '150200', '包头市', '150000');
insert into TAB_CITY (id, cityid, city, father)
values (29, '150300', '乌海市', '150000');
insert into TAB_CITY (id, cityid, city, father)
values (30, '150400', '赤峰市', '150000');
insert into TAB_CITY (id, cityid, city, father)
values (31, '150500', '通辽市', '150000');
insert into TAB_CITY (id, cityid, city, father)
values (32, '150600', '鄂尔多斯市', '150000');
insert into TAB_CITY (id, cityid, city, father)
values (33, '150700', '呼伦贝尔市', '150000');
insert into TAB_CITY (id, cityid, city, father)
values (34, '150800', '巴彦淖尔市', '150000');
insert into TAB_CITY (id, cityid, city, father)
values (35, '150900', '乌兰察布市', '150000');
insert into TAB_CITY (id, cityid, city, father)
values (36, '152200', '兴安盟', '150000');
insert into TAB_CITY (id, cityid, city, father)
values (37, '152500', '锡林郭勒盟', '150000');
insert into TAB_CITY (id, cityid, city, father)
values (38, '152900', '阿拉善盟', '150000');
insert into TAB_CITY (id, cityid, city, father)
values (39, '210100', '沈阳市', '210000');
insert into TAB_CITY (id, cityid, city, father)
values (40, '210200', '大连市', '210000');
insert into TAB_CITY (id, cityid, city, father)
values (41, '210300', '鞍山市', '210000');
insert into TAB_CITY (id, cityid, city, father)
values (42, '210400', '抚顺市', '210000');
insert into TAB_CITY (id, cityid, city, father)
values (43, '210500', '本溪市', '210000');
insert into TAB_CITY (id, cityid, city, father)
values (44, '210600', '丹东市', '210000');
insert into TAB_CITY (id, cityid, city, father)
values (45, '210700', '锦州市', '210000');
insert into TAB_CITY (id, cityid, city, father)
values (46, '210800', '营口市', '210000');
insert into TAB_CITY (id, cityid, city, father)
values (47, '210900', '阜新市', '210000');
insert into TAB_CITY (id, cityid, city, father)
values (48, '211000', '辽阳市', '210000');
insert into TAB_CITY (id, cityid, city, father)
values (49, '211100', '盘锦市', '210000');
insert into TAB_CITY (id, cityid, city, father)
values (50, '211200', '铁岭市', '210000');
insert into TAB_CITY (id, cityid, city, father)
values (51, '211300', '朝阳市', '210000');
insert into TAB_CITY (id, cityid, city, father)
values (52, '211400', '葫芦岛市', '210000');
insert into TAB_CITY (id, cityid, city, father)
values (53, '220100', '长春市', '220000');
insert into TAB_CITY (id, cityid, city, father)
values (54, '220200', '吉林市', '220000');
insert into TAB_CITY (id, cityid, city, father)
values (55, '220300', '四平市', '220000');
insert into TAB_CITY (id, cityid, city, father)
values (56, '220400', '辽源市', '220000');
insert into TAB_CITY (id, cityid, city, father)
values (57, '220500', '通化市', '220000');
insert into TAB_CITY (id, cityid, city, father)
values (58, '220600', '白山市', '220000');
insert into TAB_CITY (id, cityid, city, father)
values (59, '220700', '松原市', '220000');
insert into TAB_CITY (id, cityid, city, father)
values (60, '220800', '白城市', '220000');
insert into TAB_CITY (id, cityid, city, father)
values (61, '222400', '延边朝鲜族自治州', '220000');
insert into TAB_CITY (id, cityid, city, father)
values (62, '230100', '哈尔滨市', '230000');
insert into TAB_CITY (id, cityid, city, father)
values (63, '230200', '齐齐哈尔市', '230000');
insert into TAB_CITY (id, cityid, city, father)
values (64, '230300', '鸡西市', '230000');
insert into TAB_CITY (id, cityid, city, father)
values (65, '230400', '鹤岗市', '230000');
insert into TAB_CITY (id, cityid, city, father)
values (66, '230500', '双鸭山市', '230000');
insert into TAB_CITY (id, cityid, city, father)
values (67, '230600', '大庆市', '230000');
insert into TAB_CITY (id, cityid, city, father)
values (68, '230700', '伊春市', '230000');
insert into TAB_CITY (id, cityid, city, father)
values (69, '230800', '佳木斯市', '230000');
insert into TAB_CITY (id, cityid, city, father)
values (70, '230900', '七台河市', '230000');
insert into TAB_CITY (id, cityid, city, father)
values (71, '231000', '牡丹江市', '230000');
insert into TAB_CITY (id, cityid, city, father)
values (72, '231100', '黑河市', '230000');
insert into TAB_CITY (id, cityid, city, father)
values (73, '231200', '绥化市', '230000');
insert into TAB_CITY (id, cityid, city, father)
values (74, '232700', '大兴安岭地区', '230000');
insert into TAB_CITY (id, cityid, city, father)
values (75, '310100', '上海市', '310000');
insert into TAB_CITY (id, cityid, city, father)
values (77, '320100', '南京市', '320000');
insert into TAB_CITY (id, cityid, city, father)
values (78, '320200', '无锡市', '320000');
insert into TAB_CITY (id, cityid, city, father)
values (79, '320300', '徐州市', '320000');
insert into TAB_CITY (id, cityid, city, father)
values (80, '320400', '常州市', '320000');
insert into TAB_CITY (id, cityid, city, father)
values (81, '320500', '苏州市', '320000');
insert into TAB_CITY (id, cityid, city, father)
values (82, '320600', '南通市', '320000');
insert into TAB_CITY (id, cityid, city, father)
values (83, '320700', '连云港市', '320000');
insert into TAB_CITY (id, cityid, city, father)
values (84, '320800', '淮安市', '320000');
insert into TAB_CITY (id, cityid, city, father)
values (85, '320900', '盐城市', '320000');
insert into TAB_CITY (id, cityid, city, father)
values (86, '321000', '扬州市', '320000');
insert into TAB_CITY (id, cityid, city, father)
values (87, '321100', '镇江市', '320000');
insert into TAB_CITY (id, cityid, city, father)
values (88, '321200', '泰州市', '320000');
insert into TAB_CITY (id, cityid, city, father)
values (89, '321300', '宿迁市', '320000');
insert into TAB_CITY (id, cityid, city, father)
values (90, '330100', '杭州市', '330000');
insert into TAB_CITY (id, cityid, city, father)
values (91, '330200', '宁波市', '330000');
insert into TAB_CITY (id, cityid, city, father)
values (92, '330300', '温州市', '330000');
insert into TAB_CITY (id, cityid, city, father)
values (93, '330400', '嘉兴市', '330000');
insert into TAB_CITY (id, cityid, city, father)
values (94, '330500', '湖州市', '330000');
insert into TAB_CITY (id, cityid, city, father)
values (95, '330600', '绍兴市', '330000');
insert into TAB_CITY (id, cityid, city, father)
values (96, '330700', '金华市', '330000');
insert into TAB_CITY (id, cityid, city, father)
values (97, '330800', '衢州市', '330000');
insert into TAB_CITY (id, cityid, city, father)
values (98, '330900', '舟山市', '330000');
insert into TAB_CITY (id, cityid, city, father)
values (99, '331000', '台州市', '330000');
insert into TAB_CITY (id, cityid, city, father)
values (100, '331100', '丽水市', '330000');
insert into TAB_CITY (id, cityid, city, father)
values (101, '340100', '合肥市', '340000');
insert into TAB_CITY (id, cityid, city, father)
values (102, '340200', '芜湖市', '340000');
insert into TAB_CITY (id, cityid, city, father)
values (103, '340300', '蚌埠市', '340000');
commit;
prompt 100 records committed...
insert into TAB_CITY (id, cityid, city, father)
values (104, '340400', '淮南市', '340000');
insert into TAB_CITY (id, cityid, city, father)
values (105, '340500', '马鞍山市', '340000');
insert into TAB_CITY (id, cityid, city, father)
values (106, '340600', '淮北市', '340000');
insert into TAB_CITY (id, cityid, city, father)
values (107, '340700', '铜陵市', '340000');
insert into TAB_CITY (id, cityid, city, father)
values (108, '340800', '安庆市', '340000');
insert into TAB_CITY (id, cityid, city, father)
values (109, '341000', '黄山市', '340000');
insert into TAB_CITY (id, cityid, city, father)
values (110, '341100', '滁州市', '340000');
insert into TAB_CITY (id, cityid, city, father)
values (111, '341200', '阜阳市', '340000');
insert into TAB_CITY (id, cityid, city, father)
values (112, '341300', '宿州市', '340000');
insert into TAB_CITY (id, cityid, city, father)
values (113, '341400', '巢湖市', '340000');
insert into TAB_CITY (id, cityid, city, father)
values (114, '341500', '六安市', '340000');
insert into TAB_CITY (id, cityid, city, father)
values (115, '341600', '亳州市', '340000');
insert into TAB_CITY (id, cityid, city, father)
values (116, '341700', '池州市', '340000');
insert into TAB_CITY (id, cityid, city, father)
values (117, '341800', '宣城市', '340000');
insert into TAB_CITY (id, cityid, city, father)
values (118, '350100', '福州市', '350000');
insert into TAB_CITY (id, cityid, city, father)
values (119, '350200', '厦门市', '350000');
insert into TAB_CITY (id, cityid, city, father)
values (120, '350300', '莆田市', '350000');
insert into TAB_CITY (id, cityid, city, father)
values (121, '350400', '三明市', '350000');
insert into TAB_CITY (id, cityid, city, father)
values (122, '350500', '泉州市', '350000');
insert into TAB_CITY (id, cityid, city, father)
values (123, '350600', '漳州市', '350000');
insert into TAB_CITY (id, cityid, city, father)
values (124, '350700', '南平市', '350000');
insert into TAB_CITY (id, cityid, city, father)
values (125, '350800', '龙岩市', '350000');
insert into TAB_CITY (id, cityid, city, father)
values (126, '350900', '宁德市', '350000');
insert into TAB_CITY (id, cityid, city, father)
values (127, '360100', '南昌市', '360000');
insert into TAB_CITY (id, cityid, city, father)
values (128, '360200', '景德镇市', '360000');
insert into TAB_CITY (id, cityid, city, father)
values (129, '360300', '萍乡市', '360000');
insert into TAB_CITY (id, cityid, city, father)
values (130, '360400', '九江市', '360000');
insert into TAB_CITY (id, cityid, city, father)
values (131, '360500', '新余市', '360000');
insert into TAB_CITY (id, cityid, city, father)
values (132, '360600', '鹰潭市', '360000');
insert into TAB_CITY (id, cityid, city, father)
values (133, '360700', '赣州市', '360000');
insert into TAB_CITY (id, cityid, city, father)
values (134, '360800', '吉安市', '360000');
insert into TAB_CITY (id, cityid, city, father)
values (135, '360900', '宜春市', '360000');
insert into TAB_CITY (id, cityid, city, father)
values (136, '361000', '抚州市', '360000');
insert into TAB_CITY (id, cityid, city, father)
values (137, '361100', '上饶市', '360000');
insert into TAB_CITY (id, cityid, city, father)
values (138, '370100', '济南市', '370000');
insert into TAB_CITY (id, cityid, city, father)
values (139, '370200', '青岛市', '370000');
insert into TAB_CITY (id, cityid, city, father)
values (140, '370300', '淄博市', '370000');
insert into TAB_CITY (id, cityid, city, father)
values (141, '370400', '枣庄市', '370000');
insert into TAB_CITY (id, cityid, city, father)
values (142, '370500', '东营市', '370000');
insert into TAB_CITY (id, cityid, city, father)
values (143, '370600', '烟台市', '370000');
insert into TAB_CITY (id, cityid, city, father)
values (144, '370700', '潍坊市', '370000');
insert into TAB_CITY (id, cityid, city, father)
values (145, '370800', '济宁市', '370000');
insert into TAB_CITY (id, cityid, city, father)
values (146, '370900', '泰安市', '370000');
insert into TAB_CITY (id, cityid, city, father)
values (147, '371000', '威海市', '370000');
insert into TAB_CITY (id, cityid, city, father)
values (148, '371100', '日照市', '370000');
insert into TAB_CITY (id, cityid, city, father)
values (149, '371200', '莱芜市', '370000');
insert into TAB_CITY (id, cityid, city, father)
values (150, '371300', '临沂市', '370000');
insert into TAB_CITY (id, cityid, city, father)
values (151, '371400', '德州市', '370000');
insert into TAB_CITY (id, cityid, city, father)
values (152, '371500', '聊城市', '370000');
insert into TAB_CITY (id, cityid, city, father)
values (153, '371600', '滨州市', '370000');
insert into TAB_CITY (id, cityid, city, father)
values (154, '371700', '荷泽市', '370000');
insert into TAB_CITY (id, cityid, city, father)
values (155, '410100', '郑州市', '410000');
insert into TAB_CITY (id, cityid, city, father)
values (156, '410200', '开封市', '410000');
insert into TAB_CITY (id, cityid, city, father)
values (157, '410300', '洛阳市', '410000');
insert into TAB_CITY (id, cityid, city, father)
values (158, '410400', '平顶山市', '410000');
insert into TAB_CITY (id, cityid, city, father)
values (159, '410500', '安阳市', '410000');
insert into TAB_CITY (id, cityid, city, father)
values (160, '410600', '鹤壁市', '410000');
insert into TAB_CITY (id, cityid, city, father)
values (161, '410700', '新乡市', '410000');
insert into TAB_CITY (id, cityid, city, father)
values (162, '410800', '焦作市', '410000');
insert into TAB_CITY (id, cityid, city, father)
values (163, '410900', '濮阳市', '410000');
insert into TAB_CITY (id, cityid, city, father)
values (164, '411000', '许昌市', '410000');
insert into TAB_CITY (id, cityid, city, father)
values (165, '411100', '漯河市', '410000');
insert into TAB_CITY (id, cityid, city, father)
values (166, '411200', '三门峡市', '410000');
insert into TAB_CITY (id, cityid, city, father)
values (167, '411300', '南阳市', '410000');
insert into TAB_CITY (id, cityid, city, father)
values (168, '411400', '商丘市', '410000');
insert into TAB_CITY (id, cityid, city, father)
values (169, '411500', '信阳市', '410000');
insert into TAB_CITY (id, cityid, city, father)
values (170, '411600', '周口市', '410000');
insert into TAB_CITY (id, cityid, city, father)
values (171, '411700', '驻马店市', '410000');
insert into TAB_CITY (id, cityid, city, father)
values (172, '420100', '武汉市', '420000');
insert into TAB_CITY (id, cityid, city, father)
values (173, '420200', '黄石市', '420000');
insert into TAB_CITY (id, cityid, city, father)
values (174, '420300', '十堰市', '420000');
insert into TAB_CITY (id, cityid, city, father)
values (175, '420500', '宜昌市', '420000');
insert into TAB_CITY (id, cityid, city, father)
values (176, '420600', '襄樊市', '420000');
insert into TAB_CITY (id, cityid, city, father)
values (177, '420700', '鄂州市', '420000');
insert into TAB_CITY (id, cityid, city, father)
values (178, '420800', '荆门市', '420000');
insert into TAB_CITY (id, cityid, city, father)
values (179, '420900', '孝感市', '420000');
insert into TAB_CITY (id, cityid, city, father)
values (180, '421000', '荆州市', '420000');
insert into TAB_CITY (id, cityid, city, father)
values (181, '421100', '黄冈市', '420000');
insert into TAB_CITY (id, cityid, city, father)
values (182, '421200', '咸宁市', '420000');
insert into TAB_CITY (id, cityid, city, father)
values (183, '421300', '随州市', '420000');
insert into TAB_CITY (id, cityid, city, father)
values (184, '422800', '恩施土家族苗族自治州', '420000');
insert into TAB_CITY (id, cityid, city, father)
values (185, '429000', '省直辖行政单位', '420000');
insert into TAB_CITY (id, cityid, city, father)
values (186, '430100', '长沙市', '430000');
insert into TAB_CITY (id, cityid, city, father)
values (187, '430200', '株洲市', '430000');
insert into TAB_CITY (id, cityid, city, father)
values (188, '430300', '湘潭市', '430000');
insert into TAB_CITY (id, cityid, city, father)
values (189, '430400', '衡阳市', '430000');
insert into TAB_CITY (id, cityid, city, father)
values (190, '430500', '邵阳市', '430000');
insert into TAB_CITY (id, cityid, city, father)
values (191, '430600', '岳阳市', '430000');
insert into TAB_CITY (id, cityid, city, father)
values (192, '430700', '常德市', '430000');
insert into TAB_CITY (id, cityid, city, father)
values (193, '430800', '张家界市', '430000');
insert into TAB_CITY (id, cityid, city, father)
values (194, '430900', '益阳市', '430000');
insert into TAB_CITY (id, cityid, city, father)
values (195, '431000', '郴州市', '430000');
insert into TAB_CITY (id, cityid, city, father)
values (196, '431100', '永州市', '430000');
insert into TAB_CITY (id, cityid, city, father)
values (197, '431200', '怀化市', '430000');
insert into TAB_CITY (id, cityid, city, father)
values (198, '431300', '娄底市', '430000');
insert into TAB_CITY (id, cityid, city, father)
values (199, '433100', '湘西土家族苗族自治州', '430000');
insert into TAB_CITY (id, cityid, city, father)
values (200, '440100', '广州市', '440000');
insert into TAB_CITY (id, cityid, city, father)
values (201, '440200', '韶关市', '440000');
insert into TAB_CITY (id, cityid, city, father)
values (202, '440300', '深圳市', '440000');
insert into TAB_CITY (id, cityid, city, father)
values (203, '440400', '珠海市', '440000');
commit;
prompt 200 records committed...
insert into TAB_CITY (id, cityid, city, father)
values (204, '440500', '汕头市', '440000');
insert into TAB_CITY (id, cityid, city, father)
values (205, '440600', '佛山市', '440000');
insert into TAB_CITY (id, cityid, city, father)
values (206, '440700', '江门市', '440000');
insert into TAB_CITY (id, cityid, city, father)
values (207, '440800', '湛江市', '440000');
insert into TAB_CITY (id, cityid, city, father)
values (208, '440900', '茂名市', '440000');
insert into TAB_CITY (id, cityid, city, father)
values (209, '441200', '肇庆市', '440000');
insert into TAB_CITY (id, cityid, city, father)
values (210, '441300', '惠州市', '440000');
insert into TAB_CITY (id, cityid, city, father)
values (211, '441400', '梅州市', '440000');
insert into TAB_CITY (id, cityid, city, father)
values (212, '441500', '汕尾市', '440000');
insert into TAB_CITY (id, cityid, city, father)
values (213, '441600', '河源市', '440000');
insert into TAB_CITY (id, cityid, city, father)
values (214, '441700', '阳江市', '440000');
insert into TAB_CITY (id, cityid, city, father)
values (215, '441800', '清远市', '440000');
insert into TAB_CITY (id, cityid, city, father)
values (216, '441900', '东莞市', '440000');
insert into TAB_CITY (id, cityid, city, father)
values (217, '442000', '中山市', '440000');
insert into TAB_CITY (id, cityid, city, father)
values (218, '445100', '潮州市', '440000');
insert into TAB_CITY (id, cityid, city, father)
values (219, '445200', '揭阳市', '440000');
insert into TAB_CITY (id, cityid, city, father)
values (220, '445300', '云浮市', '440000');
insert into TAB_CITY (id, cityid, city, father)
values (221, '450100', '南宁市', '450000');
insert into TAB_CITY (id, cityid, city, father)
values (222, '450200', '柳州市', '450000');
insert into TAB_CITY (id, cityid, city, father)
values (223, '450300', '桂林市', '450000');
insert into TAB_CITY (id, cityid, city, father)
values (224, '450400', '梧州市', '450000');
insert into TAB_CITY (id, cityid, city, father)
values (225, '450500', '北海市', '450000');
insert into TAB_CITY (id, cityid, city, father)
values (226, '450600', '防城港市', '450000');
insert into TAB_CITY (id, cityid, city, father)
values (227, '450700', '钦州市', '450000');
insert into TAB_CITY (id, cityid, city, father)
values (228, '450800', '贵港市', '450000');
insert into TAB_CITY (id, cityid, city, father)
values (229, '450900', '玉林市', '450000');
insert into TAB_CITY (id, cityid, city, father)
values (230, '451000', '百色市', '450000');
insert into TAB_CITY (id, cityid, city, father)
values (231, '451100', '贺州市', '450000');
insert into TAB_CITY (id, cityid, city, father)
values (232, '451200', '河池市', '450000');
insert into TAB_CITY (id, cityid, city, father)
values (233, '451300', '来宾市', '450000');
insert into TAB_CITY (id, cityid, city, father)
values (234, '451400', '崇左市', '450000');
insert into TAB_CITY (id, cityid, city, father)
values (235, '460100', '海口市', '460000');
insert into TAB_CITY (id, cityid, city, father)
values (236, '460200', '三亚市', '460000');
insert into TAB_CITY (id, cityid, city, father)
values (237, '469000', '省直辖县级行政单位', '460000');
insert into TAB_CITY (id, cityid, city, father)
values (238, '500100', '重庆市', '500000');
insert into TAB_CITY (id, cityid, city, father)
values (241, '510100', '成都市', '510000');
insert into TAB_CITY (id, cityid, city, father)
values (242, '510300', '自贡市', '510000');
insert into TAB_CITY (id, cityid, city, father)
values (243, '510400', '攀枝花市', '510000');
insert into TAB_CITY (id, cityid, city, father)
values (244, '510500', '泸州市', '510000');
insert into TAB_CITY (id, cityid, city, father)
values (245, '510600', '德阳市', '510000');
insert into TAB_CITY (id, cityid, city, father)
values (246, '510700', '绵阳市', '510000');
insert into TAB_CITY (id, cityid, city, father)
values (249, '511000', '内江市', '510000');
insert into TAB_CITY (id, cityid, city, father)
values (250, '511100', '乐山市', '510000');
insert into TAB_CITY (id, cityid, city, father)
values (251, '511300', '南充市', '510000');
insert into TAB_CITY (id, cityid, city, father)
values (252, '511400', '眉山市', '510000');
insert into TAB_CITY (id, cityid, city, father)
values (253, '511500', '宜宾市', '510000');
insert into TAB_CITY (id, cityid, city, father)
values (254, '511600', '广安市', '510000');
insert into TAB_CITY (id, cityid, city, father)
values (255, '511700', '达州市', '510000');
insert into TAB_CITY (id, cityid, city, father)
values (256, '511800', '雅安市', '510000');
insert into TAB_CITY (id, cityid, city, father)
values (257, '511900', '巴中市', '510000');
insert into TAB_CITY (id, cityid, city, father)
values (258, '512000', '资阳市', '510000');
insert into TAB_CITY (id, cityid, city, father)
values (259, '513200', '阿坝藏族羌族自治州', '510000');
insert into TAB_CITY (id, cityid, city, father)
values (260, '513300', '甘孜藏族自治州', '510000');
insert into TAB_CITY (id, cityid, city, father)
values (261, '513400', '凉山彝族自治州', '510000');
insert into TAB_CITY (id, cityid, city, father)
values (262, '520100', '贵阳市', '520000');
insert into TAB_CITY (id, cityid, city, father)
values (263, '520200', '六盘水市', '520000');
insert into TAB_CITY (id, cityid, city, father)
values (264, '520300', '遵义市', '520000');
insert into TAB_CITY (id, cityid, city, father)
values (265, '520400', '安顺市', '520000');
insert into TAB_CITY (id, cityid, city, father)
values (266, '522200', '铜仁地区', '520000');
insert into TAB_CITY (id, cityid, city, father)
values (267, '522300', '黔西南布依族苗族自治州', '520000');
insert into TAB_CITY (id, cityid, city, father)
values (268, '522400', '毕节地区', '520000');
insert into TAB_CITY (id, cityid, city, father)
values (269, '522600', '黔东南苗族侗族自治州', '520000');
insert into TAB_CITY (id, cityid, city, father)
values (270, '522700', '黔南布依族苗族自治州', '520000');
insert into TAB_CITY (id, cityid, city, father)
values (271, '530100', '昆明市', '530000');
insert into TAB_CITY (id, cityid, city, father)
values (272, '530300', '曲靖市', '530000');
insert into TAB_CITY (id, cityid, city, father)
values (273, '530400', '玉溪市', '530000');
insert into TAB_CITY (id, cityid, city, father)
values (274, '530500', '保山市', '530000');
insert into TAB_CITY (id, cityid, city, father)
values (275, '530600', '昭通市', '530000');
insert into TAB_CITY (id, cityid, city, father)
values (276, '530700', '丽江市', '530000');
insert into TAB_CITY (id, cityid, city, father)
values (277, '530800', '思茅市', '530000');
insert into TAB_CITY (id, cityid, city, father)
values (278, '530900', '临沧市', '530000');
insert into TAB_CITY (id, cityid, city, father)
values (279, '532300', '楚雄彝族自治州', '530000');
insert into TAB_CITY (id, cityid, city, father)
values (280, '532500', '红河哈尼族彝族自治州', '530000');
insert into TAB_CITY (id, cityid, city, father)
values (281, '532600', '文山壮族苗族自治州', '530000');
insert into TAB_CITY (id, cityid, city, father)
values (282, '532800', '西双版纳傣族自治州', '530000');
insert into TAB_CITY (id, cityid, city, father)
values (283, '532900', '大理白族自治州', '530000');
insert into TAB_CITY (id, cityid, city, father)
values (284, '533100', '德宏傣族景颇族自治州', '530000');
insert into TAB_CITY (id, cityid, city, father)
values (285, '533300', '怒江傈僳族自治州', '530000');
insert into TAB_CITY (id, cityid, city, father)
values (286, '533400', '迪庆藏族自治州', '530000');
insert into TAB_CITY (id, cityid, city, father)
values (287, '540100', '拉萨市', '540000');
insert into TAB_CITY (id, cityid, city, father)
values (288, '542100', '昌都地区', '540000');
insert into TAB_CITY (id, cityid, city, father)
values (289, '542200', '山南地区', '540000');
insert into TAB_CITY (id, cityid, city, father)
values (290, '542300', '日喀则地区', '540000');
insert into TAB_CITY (id, cityid, city, father)
values (291, '542400', '那曲地区', '540000');
insert into TAB_CITY (id, cityid, city, father)
values (292, '542500', '阿里地区', '540000');
insert into TAB_CITY (id, cityid, city, father)
values (293, '542600', '林芝地区', '540000');
insert into TAB_CITY (id, cityid, city, father)
values (294, '610100', '西安市', '610000');
insert into TAB_CITY (id, cityid, city, father)
values (295, '610200', '铜川市', '610000');
insert into TAB_CITY (id, cityid, city, father)
values (296, '610300', '宝鸡市', '610000');
insert into TAB_CITY (id, cityid, city, father)
values (297, '610400', '咸阳市', '610000');
insert into TAB_CITY (id, cityid, city, father)
values (298, '610500', '渭南市', '610000');
insert into TAB_CITY (id, cityid, city, father)
values (299, '610600', '延安市', '610000');
insert into TAB_CITY (id, cityid, city, father)
values (300, '610700', '汉中市', '610000');
insert into TAB_CITY (id, cityid, city, father)
values (301, '610800', '榆林市', '610000');
insert into TAB_CITY (id, cityid, city, father)
values (302, '610900', '安康市', '610000');
insert into TAB_CITY (id, cityid, city, father)
values (303, '611000', '商洛市', '610000');
insert into TAB_CITY (id, cityid, city, father)
values (304, '620100', '兰州市', '620000');
insert into TAB_CITY (id, cityid, city, father)
values (305, '620200', '嘉峪关市', '620000');
insert into TAB_CITY (id, cityid, city, father)
values (306, '620300', '金昌市', '620000');
insert into TAB_CITY (id, cityid, city, father)
values (307, '620400', '白银市', '620000');
commit;
prompt 300 records committed...
insert into TAB_CITY (id, cityid, city, father)
values (308, '620500', '天水市', '620000');
insert into TAB_CITY (id, cityid, city, father)
values (309, '620600', '武威市', '620000');
insert into TAB_CITY (id, cityid, city, father)
values (310, '620700', '张掖市', '620000');
insert into TAB_CITY (id, cityid, city, father)
values (311, '620800', '平凉市', '620000');
insert into TAB_CITY (id, cityid, city, father)
values (312, '620900', '酒泉市', '620000');
insert into TAB_CITY (id, cityid, city, father)
values (313, '621000', '庆阳市', '620000');
insert into TAB_CITY (id, cityid, city, father)
values (314, '621100', '定西市', '620000');
insert into TAB_CITY (id, cityid, city, father)
values (247, '510800', '广元市', '510000');
insert into TAB_CITY (id, cityid, city, father)
values (248, '510900', '遂宁市', '510000');
insert into TAB_CITY (id, cityid, city, father)
values (315, '621200', '陇南市', '620000');
insert into TAB_CITY (id, cityid, city, father)
values (316, '622900', '临夏回族自治州', '620000');
insert into TAB_CITY (id, cityid, city, father)
values (317, '623000', '甘南藏族自治州', '620000');
insert into TAB_CITY (id, cityid, city, father)
values (318, '630100', '西宁市', '630000');
insert into TAB_CITY (id, cityid, city, father)
values (319, '632100', '海东地区', '630000');
insert into TAB_CITY (id, cityid, city, father)
values (320, '632200', '海北藏族自治州', '630000');
insert into TAB_CITY (id, cityid, city, father)
values (321, '632300', '黄南藏族自治州', '630000');
insert into TAB_CITY (id, cityid, city, father)
values (322, '632500', '海南藏族自治州', '630000');
insert into TAB_CITY (id, cityid, city, father)
values (323, '632600', '果洛藏族自治州', '630000');
insert into TAB_CITY (id, cityid, city, father)
values (324, '632700', '玉树藏族自治州', '630000');
insert into TAB_CITY (id, cityid, city, father)
values (325, '632800', '海西蒙古族藏族自治州', '630000');
insert into TAB_CITY (id, cityid, city, father)
values (326, '640100', '银川市', '640000');
insert into TAB_CITY (id, cityid, city, father)
values (327, '640200', '石嘴山市', '640000');
insert into TAB_CITY (id, cityid, city, father)
values (328, '640300', '吴忠市', '640000');
insert into TAB_CITY (id, cityid, city, father)
values (329, '640400', '固原市', '640000');
insert into TAB_CITY (id, cityid, city, father)
values (330, '640500', '中卫市', '640000');
insert into TAB_CITY (id, cityid, city, father)
values (331, '650100', '乌鲁木齐市', '650000');
insert into TAB_CITY (id, cityid, city, father)
values (332, '650200', '克拉玛依市', '650000');
insert into TAB_CITY (id, cityid, city, father)
values (333, '652100', '吐鲁番地区', '650000');
insert into TAB_CITY (id, cityid, city, father)
values (334, '652200', '哈密地区', '650000');
insert into TAB_CITY (id, cityid, city, father)
values (335, '652300', '昌吉回族自治州', '650000');
insert into TAB_CITY (id, cityid, city, father)
values (336, '652700', '博尔塔拉蒙古自治州', '650000');
insert into TAB_CITY (id, cityid, city, father)
values (337, '652800', '巴音郭楞蒙古自治州', '650000');
insert into TAB_CITY (id, cityid, city, father)
values (338, '652900', '阿克苏地区', '650000');
insert into TAB_CITY (id, cityid, city, father)
values (339, '653000', '克孜勒苏柯尔克孜自治州', '650000');
insert into TAB_CITY (id, cityid, city, father)
values (340, '653100', '喀什地区', '650000');
insert into TAB_CITY (id, cityid, city, father)
values (341, '653200', '和田地区', '650000');
insert into TAB_CITY (id, cityid, city, father)
values (342, '654000', '伊犁哈萨克自治州', '650000');
insert into TAB_CITY (id, cityid, city, father)
values (343, '654200', '塔城地区', '650000');
insert into TAB_CITY (id, cityid, city, father)
values (344, '654300', '阿勒泰地区', '650000');
insert into TAB_CITY (id, cityid, city, father)
values (345, '659000', '省直辖行政单位', '650000');
commit;
prompt 340 records loaded
prompt Loading TAB_PROVINCE...
insert into TAB_PROVINCE (id, provinceid, province)
values (1, '110000', '北京');
insert into TAB_PROVINCE (id, provinceid, province)
values (2, '120000', '天津');
insert into TAB_PROVINCE (id, provinceid, province)
values (3, '130000', '河北省');
insert into TAB_PROVINCE (id, provinceid, province)
values (4, '140000', '山西省');
insert into TAB_PROVINCE (id, provinceid, province)
values (5, '150000', '内蒙古自治区');
insert into TAB_PROVINCE (id, provinceid, province)
values (6, '210000', '辽宁省');
insert into TAB_PROVINCE (id, provinceid, province)
values (7, '220000', '吉林省');
insert into TAB_PROVINCE (id, provinceid, province)
values (8, '230000', '黑龙江省');
insert into TAB_PROVINCE (id, provinceid, province)
values (9, '310000', '上海');
insert into TAB_PROVINCE (id, provinceid, province)
values (10, '320000', '江苏省');
insert into TAB_PROVINCE (id, provinceid, province)
values (11, '330000', '浙江省');
insert into TAB_PROVINCE (id, provinceid, province)
values (12, '340000', '安徽省');
insert into TAB_PROVINCE (id, provinceid, province)
values (13, '350000', '福建省');
insert into TAB_PROVINCE (id, provinceid, province)
values (14, '360000', '江西省');
insert into TAB_PROVINCE (id, provinceid, province)
values (15, '370000', '山东省');
insert into TAB_PROVINCE (id, provinceid, province)
values (16, '410000', '河南省');
insert into TAB_PROVINCE (id, provinceid, province)
values (17, '420000', '湖北省');
insert into TAB_PROVINCE (id, provinceid, province)
values (18, '430000', '湖南省');
insert into TAB_PROVINCE (id, provinceid, province)
values (19, '440000', '广东省');
insert into TAB_PROVINCE (id, provinceid, province)
values (20, '450000', '广西壮族自治区');
insert into TAB_PROVINCE (id, provinceid, province)
values (21, '460000', '海南省');
insert into TAB_PROVINCE (id, provinceid, province)
values (22, '500000', '重庆');
insert into TAB_PROVINCE (id, provinceid, province)
values (23, '510000', '四川省');
insert into TAB_PROVINCE (id, provinceid, province)
values (24, '520000', '贵州省');
insert into TAB_PROVINCE (id, provinceid, province)
values (25, '530000', '云南省');
insert into TAB_PROVINCE (id, provinceid, province)
values (26, '540000', '西藏自治区');
insert into TAB_PROVINCE (id, provinceid, province)
values (27, '610000', '陕西省');
insert into TAB_PROVINCE (id, provinceid, province)
values (28, '620000', '甘肃省');
insert into TAB_PROVINCE (id, provinceid, province)
values (29, '630000', '青海省');
insert into TAB_PROVINCE (id, provinceid, province)
values (30, '640000', '宁夏回族自治区');
insert into TAB_PROVINCE (id, provinceid, province)
values (31, '650000', '新疆维吾尔自治区');
insert into TAB_PROVINCE (id, provinceid, province)
values (32, '710000', '台湾省');
insert into TAB_PROVINCE (id, provinceid, province)
values (33, '810000', '香港特别行政区');
insert into TAB_PROVINCE (id, provinceid, province)
values (34, '820000', '澳门特别行政区');
commit;
prompt 34 records loaded
prompt Loading TAB_USERTYPE...
insert into TAB_USERTYPE (id, content)
values (1, '成人');
insert into TAB_USERTYPE (id, content)
values (2, '儿童');
insert into TAB_USERTYPE (id, content)
values (3, '学生');
insert into TAB_USERTYPE (id, content)
values (4, '残疾军人、伤残人民警察');
commit;
prompt 4 records loaded
prompt Enabling foreign key constraints for TAB_USER...
alter table TAB_USER enable constraint TAB_USER_CERTTYPE_FK;
alter table TAB_USER enable constraint TAB_USER_CITY_FK;
alter table TAB_USER enable constraint TAB_USER_USERTYPE_FK;
prompt Enabling triggers for TAB_CERTTYPE...
alter table TAB_CERTTYPE enable all triggers;
prompt Enabling triggers for TAB_CITY...
alter table TAB_CITY enable all triggers;
prompt Enabling triggers for TAB_PROVINCE...
alter table TAB_PROVINCE enable all triggers;
prompt Enabling triggers for TAB_USERTYPE...
alter table TAB_USERTYPE enable all triggers;
prompt Enabling triggers for TAB_USER...
alter table TAB_USER enable all triggers;
set feedback on
set define on
prompt Done.
