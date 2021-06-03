## 码匠社区

## 资料

[Spring Boot](http://projects.spring.io/spring-boot/#quick-start)
[MyBatis](https://mybatis.org/mybatis-3/zh/index.html)
[MyBatis Generator](http://mybatis.org/generator/)
[H2](http://www.h2database.com/html/main.html)
[Flyway](https://flywaydb.org/getstarted/firststeps/maven)
[Lombok](https://www.projectlombok.org)
[Bootstrap](https://v3.bootcss.com/getting-started/)
[Github OAuth](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)
[UFile](https://github.com/ucloud/ufile-sdk-java)
[Bootstrap](https://v3.bootcss.com/getting-started/)

## 工具

[Git](https://git-scm.com/download)
[Visual Paradig](https://www.visual-paradig.com)

## 有新文件时的操作

# 1、git status 查看新增文件

# 2、git add . 将新增文件添加到暂存

# 3、git status 再次查看

# 4、git commit -m "add readme" 提交(m的意思是添加备注)引号里写备注

# 若不修改备注，追加即可，但都要写在add命令后

# git commit --amend --no-edit 其中amend:追加上一个相同的文件,--no-edit:不更改备注信息

# 5、git push 上传

##相关命令
mvn flyway:migrate
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate