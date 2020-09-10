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
1. Given Locker 有可用容量 When 存包 Then 存包成功，返回票据
2. Given Locker 无可用容量 When 存包 Then 存包失败，提示储物柜已满  
3. Given 一张有效票据 When 取包 Then 取包成功，并且是票据对应的包
4. Given 一张伪造票据 When 取包 Then 取包失败，提示无此票据
5. Given 一张重复使用的票据 When 取包 Then 取包失败，提示票据已被使用过  
6. Given 一张识别失败的票据 When 取包 Then 取包失败，提示无法识别

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
1. Given PrimaryLockerRobot管理多个Locker，每个Locker都有可用容量 When PrimaryLockerRobot存包 Then 成功存入第一个Locker，并且返回票据
2. Given PrimaryLockerRobot管理多个Locker，第一个Locker存满，第二个Locker有可用容量 When PrimaryLockerRobot存包 Then 成功存入第二个Locker，并且返回票据
3. Given PrimaryLockerRobot管理多个Locker，都没有可用容量 Then PrimaryLockerRobot存包 Then 存包失败，提示所有储物柜已满
4. Given PrimaryLockerRobot管理多个Locker，并且拿到一张有效的票据 When PrimaryLockerRobot取包 Then 取包成功，并且是票据所对应的包
5. Given PrimaryLockerRobot管理多个Locker，并且拿到一个伪造的票据 When PrimaryLockerRobot取包 Then 取包失败，提示非法票据
6. Given PrimaryLockerRobot管理多个Locker，并且拿到一个无法识别的票据 When PrimaryLockerRobot取包 Then取包失败，提示无法识别

### 需求：作为一个聪明的储物柜机器人，我能够将包存在可用容量最多的储物柜，并可以取出

### Tasking
1. Given SmartLockerRobot管理多个Locker，每个Locker都有可用容量，第一个空格多，第二个空格少 When SmartLockerRobot存包 Then 成功存入第一个Locker，并且返回票据
2. Given SmartLockerRobot管理多个Locker，每个Locker都有可用容量，第一个空格少，第二个空格多 When SmartLockerRobot存包 Then 成功存入第二个Locker，并且返回票据
3. Given SmartLockerRobot管理多个Locker，第一个Locker和第二个Locker空格数量相同 When SmartLockerRobot存包 Then 成功存入第一个Locker，并且返回票据
4. Given SmartLockerRobot管理多个Locker，第一个Locker存满，第二个Locker有可用容量 When SmartLockerRobot存包 Then 成功存入第二个Locker，并且返回票据
5. Given SmartLockerRobot管理多个Locker，都没有可用容量 Then SmartLockerRobot存包 Then 存包失败，提示所有储物柜已满
6. Given SmartLockerRobot管理多个Locker，并且拿到一张有效的票据 When SmartLockerRobot取包 Then 取包成功，并且是票据所对应的包
7. Given SmartLockerRobot管理多个Locker，并且拿到一个伪造的票据 When SmartLockerRobot取包 Then 取包失败，提示非法票据
8. Given SmartLockerRobot管理多个Locker，并且拿到一个无法识别的票据 When SmartLockerRobot取包 Then取包失败，提示无法识别

### 需求：作为储物柜机器人的经理，我要管理多个机器人，我可以让机器人存包，也可以自己存包
需求澄清：
1. LockerRobotManager至少管理一个Robot或者Locker
2. LockerRobotManager会让Robot先存包，然后再自己存包
3. LockerRobotManager管理的Robot/Locker按顺序存包

### Tasking
1.
Given LockerRobotManager管理两个locker且未管理robot，且两个locker均有可用空间
When LockerRobotManager 存包
Then 成功存入第一个locker，并返回票据

2.
Given LockerRobotManager管理两个locker且未管理robot，且第一个locker已满，第二个locker有可用空间
When LockerRobotManager 存包
Then 成功存入第二个locker，并返回票据

3.
Given LockerRobotManager管理两个locker且未管理robot，且两个locker均已满
When LockerRobotManager 存包
Then 存包失败，提示储物柜已满

4.
Given LockerRobotManager管理两个robot且未管理locker，且两个robot均有可用空间
When LockerRobotManager 存包
Then 成功由第一个robot存入，并返回票据

5.
Given LockerRobotManager管理两个robot且未管理locker，且第一个robot已满，第二个robot有可用空间
When LockerRobotManager 存包
Then 成功由第二个robot存入，并返回票据

6.
Given LockerRobotManager管理两个robot且未管理locker，且两个robot均已满
When LockerRobotManager 存包
Then 存包失败，提示储物柜已满

7.
Given LockerRobotManager管理一个locker和一个robot，且均有可用空间
When LockerRobotManager 存包
Then 应由robot存入，并返回票据

8.
Given LockerRobotManager管理一个locker和一个robot，且locker有可用空间而robot的locker空间已满
When LockerRobotManager 存包
Then 成功存入locker，并返回票据

9.
Given LockerRobotManager管理一个locker和一个robot，且均已满
When LockerRobotManager 存包
Then 存包失败，提示储物柜已满

10.
Given LockerRobotManager管理两个locker且未管理robot，且票据有效
When LockerRobotManager 取包
Then 取包成功，并返回对应的包

11.
Given LockerRobotManager管理两个locker且未管理robot，且票据无效
When LockerRobotManager 取包
Then 取包失败，提示无效票据

12.
Given LockerRobotManager管理两个robot且未管理locker，且票据有效
When LockerRobotManager 取包
Then 取包成功，并返回对应的包

13.
Given LockerRobotManager管理两个robot且未管理locker，且票据无效
When LockerRobotManager 取包
Then 取包失败，提示无效票据

14.
Given LockerRobotManager管理一个Locker和一个robot，且票据有效
When LockerRobotManager 取包
Then 取包成功，并返回对应的包

15.
Given LockerRobotManager管理一个Locker和一个robot，且票据无效
When LockerRobotManager 取包
Then 取包失败，提示无效票据

###需求：作为储物柜机器人主管(Locker Robot Director)，我希望看到一张报表，能够反映出我管理的储物柜的存取包情况
需求澄清：
1. 报表中不区分PrimaryLockerRobot和SmartLockerRobot，两者都是Robot
2. 系统中一定存在LockerRobotManager
3. 可以存在多个LockerRobotManager
4. 不被Manager管理的Robot/Locker不计入报表

###Tasking
1. Given LockerRobotDirector管理一个LockerRobotManager，LockerRobotManager只管理两个Lockers，并且它们的可用容量和容量是0，8和3，5  
   When LockerRobotDirector统计报表  
   Then 报表内容为  
   M 3 13  
   &nbsp;&nbsp;&nbsp;&nbsp;L 0 8  
   &nbsp;&nbsp;&nbsp;&nbsp;L 3 5
2. Given LockerRobotDirector管理一个LockerRobotManager，LockerRobotManager只管理一个Locker且可用容量和容量为2，5，管理一个PrimaryLockerRobot并且可用容量和容量是1，5  
   When LockerRobotDirector统计报表  
   Then 报表内容为  
   M 3 10  
   &nbsp;&nbsp;&nbsp;&nbsp;L 2 5  
   &nbsp;&nbsp;&nbsp;&nbsp;R 1 5
3. Given LockerRobotDirector管理一个LockerRobotManager，LockerRobotManager只管理一个Locker且可用容量和容量为2，5，管理一个PrimaryLockerRobot，且管理两个Lockers，并且可用容量和容量是1，5和2，8  
   When LockerRobotDirector统计报表  
   Then 报表内容为  
   M 5 18  
   &nbsp;&nbsp;&nbsp;&nbsp;L 2 5  
   &nbsp;&nbsp;&nbsp;&nbsp;R 3 13  
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;L 1 5  
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;L 2 8
4. Given LockerRobotDirector管理一个LockerRobotManager，LockerRobotManager只管理两个Robots，并且它们的可用容量和容量是3，9和2，4  
   When LockerRobotDirector统计报表  
   Then 报表内容为  
   M 5 13  
   &nbsp;&nbsp;&nbsp;&nbsp;R 3 9  
   &nbsp;&nbsp;&nbsp;&nbsp;R 2 4
5. Given LockerRobotDirector管理一个LockerRobotManager，LockerRobotManager只管理两个Lockers，并且他们的可用容量和容量分别是0，8和3，5。存在一个没有被LockerRobotManager管理的Locker和PrimaryLockerRobot且它的可用容量是2，6；3，8
   When LockerRobotDirector统计报表  
   Then 报表内容为  
   M 3 13  
   &nbsp;&nbsp;&nbsp;&nbsp;L 0 8  
   &nbsp;&nbsp;&nbsp;&nbsp;L 3 5
