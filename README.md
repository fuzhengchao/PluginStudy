# PluginStudy
## Android 插件化学习

### Binder
[参考1](http://blog.csdn.net/universus/article/details/6211589)
[参考2](http://blog.csdn.net/luoshengyang/article/details/6618363)

Bindler 是Android系统进程间通信（IPC）方式之一。

基于Client-Server通信模式，传输过程只需要拷贝一次，为发送添加UID/PID身份，支持实名Binder和匿名Binder。

* 传输性：

各种IPC方式数据拷贝次数：

|IPC|数据拷贝次数|
|:--|:--:|
|共享内存|0|
|Binder|1|
|Socket/管道/消息队列|2|

* 安全性：
	+ 传统IPC没有任何安全措施，完全依赖上层协议来确保，传统IPC的接收方无法获得对方进程可靠的UID/PID（用户ID/进程ID），无法鉴别对方身份。
	+ 传统IPC只能由用户在数据包中填入UID/PID等信息，容易被恶意程序利用。Bindler由系统IPC机制添加。
	+ 传统IPC访问接入点是开放的，无法建立私有通道（如管道名称，system V键值， socket ip地址）。

* 结构：
	+ Server：用户空间
	+ Clinet：用户空间。通过Binder名获取Binder引用。
	+ ServiceManager：用户空间，类似DNS做作用。注册Binder名->Binder引用并存储。ServiceManager本身是一个特殊Server,其它Server注册时用引用号0与其通信，Client获取Binder引用时也是通过引用号0与ServerManager通信。
	+ Binder驱动：内核空间，类似路由作用。负责进程间Binder通信的建立，Binder进程间传递，Binder引用计数器管理，数据包进程间传递等底层支持。

 ![](http://img.my.csdn.net/uploads/201102/27/0_1298798577tfS4.gif)
 
* 匿名Binder（未在ServerManager中注册）

  实名Binder建立client-server连接，server通过已建立连接将新创建的Binder实体直接传给Client，匿名连接。匿名连接为通信双方建立了一条私密通道。