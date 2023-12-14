# AlumniManagementSystem
### 校友管理系统-后端 ams-backend

>- Java version: 1.8.0_201 / 1.8.0
>- Maven version: 3.6.1
>- MySQL 5.7
>- redis 6.2.6 / 7.2.3
>- nacos 1.4.2

#### 四个主要模块
ams-common模块
>- 负责管理所有微服务公共的依赖, bean, 工具类

ams-gateway模块（需要启动）
>- 负责网关转发

ams-basic模块（需要启动）
>- 负责校友用户的操作

renren-fast模块（需要启动）
>- 负责管理员用户的操作

renren-generator模块
>- 开发时使用的逆向工程, 可忽略

#### 开发/生产环境配置切换
- 更改ams-gateway, ams-basic, renren-fast中bootstrap.properties文件的nacos配置
- 更改ams-basic, renren-fast中application.yml文件中spring.profiles.active的配置