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
## 说明 
- 未使用百讯模版中db与redis，需要一些定制化开发