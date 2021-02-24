package com.home.innerClass2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xu.dm
 * @Date: 2021/2/23 17:17
 * @Version: 1.0
 * @Description: 控制器类
 * 用来管理并触发事件的实际控制框架。Event 对象被保存在 List<Event> 类型的容器对象中，
 *  run() 方法循环遍历 eventList，寻找就绪的（ready()）、要运行的 Event 对象。
 *  对找到的每一个就绪的（ready()）事件，使用对象的 toString() 打印其信息，调用其 action() 方法，然后从列表中移除此 Event。
 *
 * 注意，在目前的设计中你并不知道 Event 到底做了什么。
 * 这正是此设计的关键所在—"使变化的事物与不变的事物相互分离”。
 * 用我的话说，“变化向量”就是各种不同的 Event 对象所具有的不同行为，
 * 而你通过创建不同的 Event 子类来表现不同的行为。
 **/
public class Controller {
    // A class from java.util to hold Event objects:
    private List<Event> eventList = new ArrayList<>();
    public void addEvent(Event c) { eventList.add(c); }
    public void run() {
        while(eventList.size() > 0)
            // Make a copy so you're not modifying the list
            // while you're selecting the elements in it:
            for(Event e : new ArrayList<>(eventList))
                if(e.ready()) {
                    System.out.println(e);
                    e.action();
                    eventList.remove(e);
                }
    }
}
