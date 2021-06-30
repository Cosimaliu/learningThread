### ReentrantLock  vs synchronized

reentrantlock用于替代synchronized

由于m1锁定this，只有m1执行完毕的时候，m2才能执行



需要注意的是，reentrantlock必须要必须要必须要手动释放锁（重要的事情说三遍）

使用syn锁定的话如果遇到异常，jvm会自动释放锁，但是lock必须手动释放锁，因此经常



使用reentrantlock可以进行“尝试锁定”**tryLock**，这样无法锁定，或者在指定时间内无法锁定，线程可以决定是否继续等待	



使用reentrantlock的lockInterruptibly方法，可以对线程interrupt方法做出相应。即中断等待（控制台报：无法获得锁的异常）



ReentrantLock还可以指定为公平锁（先到先得，先进先出[等待队列]）



- cas vs sync
- trylock
- lockinterupptibly
- 公平和非公平