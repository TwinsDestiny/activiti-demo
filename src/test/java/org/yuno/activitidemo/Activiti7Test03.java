package org.yuno.activitidemo;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activiti7Test03 {

    /**
     * 使用配置
     */
    @Test
    public void test1() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        System.out.println(processEngine);
    }

    /**
     * 使用代码配置
     */
    @Test
    public void test2() {
        ProcessEngine processEngine = ProcessEngineConfiguration
                .createStandaloneProcessEngineConfiguration()
                .setJdbcDriver("")
                .buildProcessEngine();
    }

    /**
     * 流程部署操作
     */
    @Test
    public void test3() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deply = repositoryService.createDeployment()
                .addClasspathResource("flow/test3.bpmn20.xml")
                .name("请假流程-监听")
                .deploy();
        System.out.println(deply.getId());
        System.out.println(deply.getName());
    }

    /**
     * 查询部署流程
     */
    @Test
    public void test4() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();

        List<Deployment> list = repositoryService.createDeploymentQuery().list();
        for (Deployment deployment : list) {
            System.out.println(deployment.getId());
            System.out.println(deployment.getName());
        }

        List<ProcessDefinition> list1 = repositoryService.createProcessDefinitionQuery().list();
        for (ProcessDefinition processDefinition : list1) {
            System.out.println(processDefinition.getId());
            System.out.println(processDefinition.getName());
            System.out.println(processDefinition.getDescription());
        }

    }

    /**
     * 发起流程
     */
    @Test
    public void test5() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceById("test3:3:37503");
        System.out.println(processInstance.getId());
        System.out.println(processInstance.getDeploymentId());
        System.out.println(processInstance.getDescription());
    }

    /**
     * 代办查询
     */
    @Test
    public void test6() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        List<Task> list = taskService.createTaskQuery().taskAssignee("zhangsan").list();
        if(!list.isEmpty()){
            for (Task task : list) {
                System.out.println(task.getId());
                System.out.println(task.getName());
                System.out.println(task.getAssignee());
            }
        }else{
            System.out.println("当前没有代办任务");
        }
    }

    /**
     * 任务审批
     */
    @Test
    public void test7() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        List<Task> list = taskService.createTaskQuery().taskAssignee("lisi").list();
        if(!list.isEmpty()){
            for (Task task : list) {
                taskService.complete(task.getId());
            }
        }
    }
}
