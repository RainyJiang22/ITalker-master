## 一款实用的基于MVP模式的IM即时聊天APP(轻聊)


### 目录
* [app结构图](#app结构图)
* [框架与技术](#框架与技术)
* [服务端实现](#服务端实现)
* [客户端实现](#客户端实现)
* [所示用的技术与框架](#所使用的技术与框架)



### app结构图

-整体结构图
![](https://github.com/jackytallow/ITalker-master/blob/master/%E9%A1%B9%E7%9B%AE%E8%B5%84%E6%96%99/%E7%BB%93%E6%9E%84%E5%9B%BE/%E6%95%B4%E4%BD%93%E6%9E%B6%E6%9E%84.png)


- app功能结构图
![](https://github.com/jackytallow/ITalker-master/blob/master/%E9%A1%B9%E7%9B%AE%E8%B5%84%E6%96%99/%E7%BB%93%E6%9E%84%E5%9B%BE/app%E5%8A%9F%E8%83%BD%E7%BB%93%E6%9E%84%E5%9B%BE.png)

- 相关技术
![](https://github.com/jackytallow/ITalker-master/blob/master/%E9%A1%B9%E7%9B%AE%E8%B5%84%E6%96%99/%E7%BB%93%E6%9E%84%E5%9B%BE/%E7%9B%B8%E5%85%B3%E6%8A%80%E6%9C%AF.png)


### 框架与技术

主要使用了以下框架及第三方服务：

- Jersey 主要提供Restful支持
- Jetty Server运行的容器(也可以使用Tomcat，我这里就是使用了Tomcat进行本地开发)
- Spring 提供依赖注入
- MyBatis 数据库映射框架
- MySQL 数据库，使用这个数据库的原因是对这个数据库比较熟悉
- RabbitMQ 主要做消息发送队列用
- 七牛云 用户发送的图片，语言存储服务
- JPush 提供最核心的消息推送支持
 - 第三方接口，也就是登录验证
这里都是通过Basic认证
API
Authoirzation
iTalker使用Basic认证， 除了用户登录，注册接口外，其余接口都需要将用户名username，密码password依照以下规制生成authcode，在每次API请求时，附带在headers中的Authorization中。
authcode = "Basic " + Base64(username:md5(password))
(一种更合理的设计应该是，第一次鉴权后，生成一个有一定时限的access_token来作为authcode)

### 服务端实现

#### User 用户接口
POST ~/users/login 用户登录
POST ~/users/register 用户注册
GET ~/users 获取用户列表
GET ~/users/search 用户搜索 GET ~/users/{user_id} 获取指定用户的信息
PUT ~/users/self 更新个人信息

#### Friendship 好友接口
POST ~/friends 添加好友关系
DELETE ~/friends/{friend_id} 删除指定好友关系
GET ~/friends 获取当前用户的好友列表

#### Message 消息发送接口
POST /mesasges/send 发送消息(加好友，文本，图文)
GET /messages/offline 获取离线消息
PUT /messages/status 批量更新消息状态(主要为标记离线/未读消息)
PUT /messages/status/{mid} 更新指定消息状态


#### 用户注册接口设计
localhost:9090/api/account/register
封装json格式为
{
	"account":
	"password":
	"name"
	"sex"
	"portrait"
}

#### 用户登录接口设计
localhost:9090/api/account/login
-封装json格式为
{
	"account”:
	"password"
	“pushId”
}

#### 用户pushId+token
localhost:9090/api/bind/2123231
加个token
mccm

#### 用户更新消息接口设计
localhost:9090/api/user
加入token值
传入的是portrait，desc，sex


#### 用户拉取联系人
localhost:9090/contact
拿到自己的联系人列表，转换为UserCard
直接返回

#### 用户关注人，关注人的操作，双方同时需要关注
localhost：9090/follow/followId
需要判断一下不能关注自己
User self = getSelf()
然后找到关注的人
需要通过UserFactory去获取关注的人，备注默认是没有的
如果关注失败，直接返回服务器异常

#### 获取某人的信息
GET请求
localhost:9090/api/id
@path("{id}")

localhost:9090/api/user/getid
需要带入发送者的token才能进行获取当前的关注的用户
isFollow显示为true
如果获取的是当前用户没有关注的id，则isFollow显示为false

#### 搜索人接口的实现
localhost:9090/api/search/name


### 客户端实现

#### iTalker客户端APP主要完成一下功能：
 -  用户基础功能（注册，登录，登录验证）
 - 好友功能
 - 点对点的消息发送功能
  - 离线消息功能
  - 朋友圈功能
  - 我的界面设置功能


#### 所使用的技术与框架


技术与框架
- ButterKnife
     （ButterKnife是一个专注于Android系统的View注入框架，通俗易懂的说以前总要写很多findViewById来找到view对象，有了Butterknife可以很轻松的省去这些步骤。最重要的是使用ButterKnife对性能基本上没有损失，我们在使用的时候，集成起来比较方便，使用起来也特别简单）
- Picasso
      (一种图片加载框架，处理Adapter中ImageView的回收和取消下载
     使用最小的内存来做复杂的图片变换。比如高斯模糊，圆角、圆形等处理。自动帮我们缓存图片。内存和磁盘缓存。)
- Retrofit
      (Retrofit其实我们可以理解为OkHttp的加强版，它也是一个网络加载框架。底层是使用OKHttp封装的。准确来说,网络请求的工作本质上是OkHttp完成，而 Retrofit 仅负责网络请求接口的封装。它的一个特点是包含了特别多注解，方便简化你的代码量。并且还支持很多的开源库。使用的时候需要注意的是，因为Retrofit是基于OKhttp的，所以需要添加OKhttp的依赖)
- GreenDao
      (GreenDAO是一个开源的Android ORM(“对象/关系映射”)，通过ORM（称为“对象映射”），在我们数据库开发过程中节省了开发时间！)
- RxAndroid
      (RxAndroid起源于RxJava，是一个专门针对Android版本的Rxjava库。RxAndroid-Github 目前最新的版本是v2.0.x)
 - EventBus
       (EventBus能够简化各组件间的通信，让我们的代码书写变得简单能有效的分离事件发送方和接收方(也就是解耦的意思)，能避免复杂和容易出错的依赖性和生命周期问题。)
