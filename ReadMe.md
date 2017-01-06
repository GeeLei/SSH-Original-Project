## Huanke Modules 示例模块说明文档

### 一、模块说明
本模块的作用如下：

1. 提供ssh基本运行环境及配置，将开发的模块和ssh解耦
2. 使用方法
  - 1. ssh-shell的profile中更改数据库参数
  - 2. <properties>中<extraModule>的值,<extraModule>必须和ssh-shell平级，且为文件夹的名字
  - 3. denpendencies中添加对开发模块的依赖
  - 4. mvn jetty:run运行

### 二、接口文档

1. 测试接口
  - 接口路径： demo/demo/演示
  - 接口ID： 308
  
  
### 三、配置项列表
