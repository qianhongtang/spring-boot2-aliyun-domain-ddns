# 基于阿里云域名开发的动态域名更新系统
 
 使用Java基于spring boot框架所开发的动态域名更新系统
 
# 条件
 先申请阿里云域名的AccessKey
 > https://usercenter.console.aliyun.com/#/manage/ak
 
# 配置
application.properties
## 阿里云的accessKey配置：
> aliyun.accessKeyId=
> aliyun.accessKeySecret=

## 需要配置更新的域名
> aliyun.update.domains=xxx.xxx.com, xxx.xxx.com
> 有多个，请用英文逗号(,)分割

## 启动
请运行 AliyunDomainApplication.java 即可

# 代码
本机公网ip通过IpUtil.java文件获取。
通过：http://2018.ip138.com/ic.asp 获取后解析。
如果该地址有变化，请及时更新。并修改解析方式

 
