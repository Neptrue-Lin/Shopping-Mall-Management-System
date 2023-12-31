# West2 Online - Java 第三轮考核：Order Management System

## 项目介绍
项目简单实现了订单系统的管理，表结构较为简单，只实现了订单与商品的交互，未有权限验证，未考虑与账户系统、物流系统、
支付系统、仓储系统等其他系统的交互。

[//]: # (基本实现增删改查，但复杂的连表查询、子查询无法做到。能进行基本排序、分页和搜索，但搜索无法分词。)

~~（前台就不写了吧~~（ ` **·** ω **·** ´ )

## 项目基本功能与特性
### 数据库
- **符合数据库三大范式**
- **订单表使用主从表设计**
- **逻辑删除**
- **审计记录**
### 后台
- **使用 Mybatis 作为 ORM 框架**
  - 实现增删改查
  - 分页实现
  - 排序与搜索
  - SQL 注入防止
- **采用 Druid 连接池**
- **AOP 实现数据库事务管理**
- **通过 Spring MVC 暴露路由，实现前后端分离**
- [WIP] JUnit 5 + Mockito 单元测试

## 项目结构
### 数据库
- 订单主表 order_manifest
- 订单从表 order_item
- 商品表 product
### 后台
<img src="project-framework.svg" width="500px"></img>

```text
├───order-management-common 通用类库
│   └───src
│       └───main
│           └───java
│               └───org.neptrueworks.ordermanagement.data
│                    ├───composing   组合
│                    └───exceptions  异常处理
│
├───order-management-data   持久层
│   └───src
│       ├───main
│       │   ├───java
│       │   │   └───org.neptrueworks.ordermanagement.data
│       │   │        ├───entitizing  数据实体化
│       │   │        ├───maneuvering 数据操纵
│       │   │        ├───mapping     数据映射
│       │   │        └───reposition  数据查询
│       │   └───resources
│       │
│       └───test-integration
│           └───java
│                ├────org.neptrueworks.ordermanagement.data
│                │    └───mapping
│                └───resources
│
├───order-management-core   服务层
│   └───src
│       ├───main
│       │   ├───java
│       │   │   └───org.neptrueworks.ordermanagement.core
│       │   │        ├───exceptions      异常
│       │   │        └───servicing       核心服务
│       │   └───resources
│       │
│       └───test
│           └───java
│                └───org.neptrueworks.ordermanagement.core
│                     └───servicing
│
└───order-management-web    控制层
    └───src
        └───main
            ├───java
            │   └───org.neptrueworks.ordermanagement.web
            │        ├───controlling 控制器
            │        └───modelling   视图模型
            └───resources
```
