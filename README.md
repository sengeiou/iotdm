# IOTdm

IOTdm: 百讯物联网设备接入平台

## 设计思路与表结构设计

http://wiki.baixun.com/x/P4AWAQ

## 功能
- 设备连接
- 数据解析
- 规则转发
- 在线调试

## 包结构
```
iotdm
├── iotdm-application    应用程序启动，web api,设备数据处理与反馈
│   
├── iotdm-common         公共程序类
│    
├── iotdm-rule           转发规则,消费消息队列 触发规则并转发出去
│    
└── iotdm-transport      设备接入与数据传输
```  

## 数据发布
```json
{
  "key": "value",
  "modelId": "模型名称" // 可以有 可以没
}
```
二进制报文


## 说明 
- 未使用百讯模版中db与redis，需要一些定制化开发
- redis 开启订阅通知
1. 修改redis.conf 配置文件
```
# 原来是""空
notify-keyspace-events "Ex"
```
2. decode 需要js 插件解析
```js
var jsonObj = {};
var uint8Array = new Uint8Array(payload.length);
for (var i = 0; i < payload.length; i++) {
	uint8Array[i] = payload[i] & 0xff;
}
var dataView = new DataView(uint8Array.buffer, 0);
var tem = dataView.getInt8(0);
jsonObj = {
	"tem": tem
};
return JSON.stringify(jsonObj);
```
3. encode js
```js
{
	"toDeviceType": "COMMAND", // 枚举类 分别是 COMMAND，CONFIG，OTA
	"commandLabel": "", //命令名称
	"modelId": "" //模型id  允许为空
	"params": {
		"key1": "value1",
		"key2": 10
	}
}
```

## 分支说明 
v1.0.0.1 分支是基础版本开发分支，该分支主要迭代的功能是设备mqtt 连接，基础消息转发</br>
待完成的功能点如下：
- [ ] TCP 连接服务
- [ ] 设备联动
- [ ] 数据持久化配置