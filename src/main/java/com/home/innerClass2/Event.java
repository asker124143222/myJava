package com.home.innerClass2;

import java.time.Duration;
import java.time.Instant;

/**
 * @Author: xu.dm
 * @Date: 2021/2/23 17:15
 * @Version: 1.0
 * @Description: 事件抽象
 * 当希望运行 Event 并随后调用 start() 时，那么构造器就会捕获（从对象创建的时刻开始的）时间，
 * 此时间是这样得来的：start() 获取当前时间，然后加上一个延迟时间，这样生成触发事件的时间。
 * start() 是一个独立的方法，而没有包含在构造器内，因为这样就可以在事件运行以后重新启动计时器，也就是能够重复使用 Event 对象。
 * 例如，如果想要重复一个事件，只需简单地在 action() 中调用 start() 方法。
 *
 * ready() 告诉你何时可以运行 action() 方法了。当然，可以在派生类中重写 ready() 方法，使得 Event 能够基于时间以外的其他因素而触发。
 **/
public abstract class Event {
    private Instant eventTime;
    protected final Duration delayTime;
    public Event(long millisecondDelay) {
        delayTime = Duration.ofMillis(millisecondDelay);
        start();
    }
    public void start() { // Allows restarting
        eventTime = Instant.now().plus(delayTime);
    }
    public boolean ready() {
        return Instant.now().isAfter(eventTime);
    }
    public abstract void action();
}
