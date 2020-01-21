## 东林社区

## 资料  
[Spring](https://spring.io/guides)  
[Spring Web](https://spring.io/guides/gs/serving-web-content/)  
[Bootstrap](https://v3.bootcss.com/getting-started/)  
[Github OAuth](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)  
[SpringBoot2.2.0](https://docs.spring.io/spring-boot/docs/2.2.0.RC1/reference/htmlsingle/)  
[mybatis-spring-boot](http://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)  
[H2 Database](http://www.h2database.com/html/main.html)  
[Thyleaf](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#iteration)  


## 工具  
[Git](https://git-scm.com/download)  
[Visual Paradigm](https://www.visual-paradigm.com)  
[OkHttp](https://square.github.io/okhttp/)  
[Github授权登陆](https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/)  
[Flyway](https://flywaydb.org/getstarted/why)  
[Lombok](https://projectlombok.org/)  
## 脚本  
```sql
create table USER
(
	ID INTEGER auto_increment
		primary key,
	ACCOUNT_ID VARCHAR(100),
	NAME VARCHAR(50),
	TOKEN CHAR(36),
	GMT_CREATE BIGINT,
	GMT_MODIFIED BIGINT
);
```  

## 遗留问题  
H2数据库自增间隔在1和32之间变  
1.每次通过登录插入用户信息后用数据库工具自增变为32