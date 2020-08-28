### 需求描述
储物柜(Locker)可以存包、取包

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
1. Given Locker有可用容量 When Locker存包 Then 存包成功，返回票据
2. Given Locker无可用容量 When Locker存包 Then 存包失败，提示储物柜已满  
3. Given 一张有效票据 When Locker取包 Then 取包成功，并且是票据对应的包
4. Given 一张伪造票据 When Locker取包 Then 取包失败，提示无此票据
5. Given 一张重复使用的票据 When Locker取包 Then 取包失败，提示非法票据  
6. Given 一张识别失败的票据 When Locker取包 Then 取包失败，提示非法票据
```