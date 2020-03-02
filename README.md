
# 东林社区

## 资料  
[Spring](https://spring.io/guides)  
[Spring Web](https://spring.io/guides/gs/serving-web-content/)  
[Bootstrap](https://v3.bootcss.com/getting-started/)  
[Github OAuth](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)  
[SpringBoot2.2.0](https://docs.spring.io/spring-boot/docs/2.2.0.RC1/reference/htmlsingle/)  
[mybatis-spring-boot](http://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)  
[H2 Database](http://www.h2database.com/html/main.html)  
[Thymeleaf](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#iteration)  
[Spring MVC](https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc)  
[Mybatis Generator](http://mybatis.org/generator/)  
[SpringBoot ErrorHandling](https://docs.spring.io/spring-boot/docs/2.2.0.RC1/reference/htmlsingle/#boot-features-error-handling)  
[UFile SDK](https://github.com/ucloud/ufile-sdk-java)  
[SpringBoot Log](https://docs.spring.io/spring-boot/docs/2.2.2.RELEASE/reference/htmlsingle/#boot-features-logging)  

## 工具  

[Git](https://git-scm.com/download)  
[Visual Paradigm](https://www.visual-paradigm.com)  
[OkHttp](https://square.github.io/okhttp/)  
[Github授权登陆](https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/)  
[Flyway](https://flywaydb.org/getstarted/why)  
[Lombok](https://projectlombok.org/)  
[Live Reload](https://chrome.google.com/webstore/detail/livereload/jnihajbhpnppcggbcgedagnkighmdlei)  
[Postman](https://chrome.google.com/webstore/detail/tabbed-postman-rest-clien/coohjcphdfgbiolnekdpbcijmhambjff)  
[Markdown](http://editor.md.ipandao.com/)  
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
```bash  
mvn flyway:migrate  
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```  
## 部署  
### 依赖  
- Git  
- JDK  
- Maven  
- MySql  
## 步骤
- yum update  
