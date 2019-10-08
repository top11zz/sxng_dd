-- 项目
DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `number` varchar(15) NOT NULL COMMENT '项目编号（TGEM-YW-190001）',
  `name` varchar(30) NOT NULL COMMENT '项目主体',
  `describe` varchar(300) COMMENT '项目简介',
  `note` varchar(300) COMMENT '项目说明',
  `create_user_id` varchar(30) NOT NULL COMMENT '创建人id（钉钉用户userid）',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `updated_at` datetime NOT NULL COMMENT '更新时间',
  `deleted_at` datetime COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目';

-- 负责方联系人
DROP TABLE IF EXISTS `first_linkman`;

CREATE TABLE `first_linkman` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `project_id` int(10) unsigned NOT NULL COMMENT '项目id',
  `name` varchar(10) NOT NULL COMMENT '姓名',
  `duty` varchar(30) COMMENT '职务',
  `phone` text COMMENT '电话（json数组）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='负责方联系人';

-- 项目负责人
DROP TABLE IF EXISTS `principal`;

CREATE TABLE `principal` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `project_id` int(10) unsigned NOT NULL COMMENT '项目id',
  `user_id` varchar(30) NOT NULL COMMENT '员工id（钉钉用户userid）',
  `name` varchar(10) NOT NULL COMMENT '姓名（钉钉用户name）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目负责人';

-- 项目进展
DROP TABLE IF EXISTS `progress`;

CREATE TABLE `progress` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `project_id` int(10) unsigned NOT NULL COMMENT '项目id',
  `content` varchar(300) NOT NULL COMMENT '项目进展',
  `status_text` varchar(100) NOT NULL COMMENT '跟踪状态',
  `tail_after_at` datetime NOT NULL COMMENT '跟踪时间',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `updated_at` datetime NOT NULL COMMENT '更新时间',
  `deleted_at` datetime COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目进展';

-- 项目跟踪人
DROP TABLE IF EXISTS `tail_after`;

CREATE TABLE `tail_after` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `progress_id` int(10) unsigned NOT NULL COMMENT '项目进展id',
  `user_id` varchar(30) NOT NULL COMMENT '员工id（钉钉用户userid）',
  `name` varchar(10) NOT NULL COMMENT '姓名（钉钉用户name）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目跟踪人';