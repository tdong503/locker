### 需求描述
储物柜()可以存包、取包

### 需求澄清总结：
1. 储物柜容量不限制是指容量不固定，但是一定是会存满的
2. 储物柜没有尺寸限制，默认多大的包都能存
3. 硬件系统功能不需要考虑（开门/关门/按钮/停电/没票纸）
4. 存包失败，需要提示用户是因为储物柜满了
5. 取包失败，需要提示用户是因为票据无效
6. 存包的位置是随机，没有顺序
7. 不要脑补需求，及时和PO确认
8. 不考虑并发

### Tasking
```
1. Given Locker 有可用容量 When 存包 Then 存包成功，返回票据
2. Given Locker 无可用容量 When 存包 Then 存包失败，提示储物柜已满  
3. Given 一张有效票据 When 取包 Then 取包成功，并且是票据对应的包
4. Given 一张伪造票据 When 取包 Then 取包失败，提示无此票据
5. Given 一张重复使用的票据 When 取包 Then 取包失败，提示票据已被使用过  
6. Given 一张识别失败的票据 When 取包 Then 取包失败，提示无法识别
```

----------------------------------------------
### 需求：作为一个初级储物柜机器人，我能够按储物柜的顺序来存包，也能取包
需求澄清：
1. PrimaryLockerRobot存包是按照locker顺序存包
2. PrimaryLockerRobot在某个Locker内存包的位置是随机的
3. 报错信息和Locker是一致的
4. PrimaryLockerRobot至少管理一个Locker
5. PrimaryLockerRobot会回收取过包的票据
Note：
在Locker的基础上继续完善，不需要重新创建仓库

### Tasking
```
1. Given PrimaryLockerRobot管理多个Locker，每个Locker都有可用容量 When PrimaryLockerRobot存包 Then 成功存入第一个Locker，并且返回票据
2. Given PrimaryLockerRobot管理多个Locker，第一个Locker存满，第二个Locker有可用容量 When PrimaryLockerRobot存包 Then 成功存入第二个Locer，并且返回票据
3. Given PrimaryLockerRobot管理多个Locker，都没有可用容量 Then PrimaryLockerRobot存包 Then 存包失败，提示所有储物柜已满
4. Given PrimaryLockerRobot管理多个Locker，并且拿到一张有效的票 When PrimaryLockerRobot取包 Then 取包成功，并且是票据所对应的包
5. Given PrimaryLockerRobot管理多个Locker，并且拿到一个伪造的票 When PrimaryLockerRobot取包 Then 取包失败，提示非法票据
6. Given PrimaryLockerRobot管理多个Locker，并且拿到一个无法识别的票 When PrimaryLockerRobot取包 Then取包失败，提示无法识别
```
