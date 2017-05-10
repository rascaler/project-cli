# x-project-tool

工程辅助工具，数据库逆向生成代码
使用：
所有配置都在模块to-code中
generatorConfig.xml：只需要修改table标签中的配置
注意：属性domainObjectName后面不要加Do
generatedKey中配置oracle序列

config.properties：修改数据源配置
pom.xml:文件路径、包名、生成路径都在中配置

使用命令mvn clean install -Dmaven.test.skip=true编译整个工程
在to-code模块中使用 mvn mybatis-generator:generate 

生成的项目中，Domain缺少了Do后缀，
Mapper.xml中的resultMap中domain缺少Do后缀，自己加上。