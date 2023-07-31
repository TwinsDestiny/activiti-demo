package org.yuno.activitidemo.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class MyTaskListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("监听器被执行");
        if(EVENTNAME_CREATE.equals(delegateTask.getEventName())){
            delegateTask.setAssignee("zhangsan");
        }
    }
}
