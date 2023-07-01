/*
// 根据businessKey获取流程实例
ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
        .processInstanceBusinessKey(businessKey)
        .singleResult();

// 根据taskDefKey获取当前节点任务
        Task currentTask = taskService.createTaskQuery()
        .processInstanceId(processInstance.getId())
        .taskDefinitionKey(taskDefKey)
        .singleResult();

// 获取流程定义信息
        ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId());
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());

// 根据bpmnModel创建activity实例
        ProcessEngineConfigurationImpl engineConfiguration = (ProcessEngineConfigurationImpl) processEngine.getProcessEngineConfiguration();
        ActivityInstance activityInstance = engineConfiguration.getRuntimeService()
        .getActivityInstance(processInstance.getId());
        ReadOnlyProcessDefinition processDefinitionEntity = engineConfiguration.getDeploymentManager()
        .getProcessDefinitionCache().get(processDefinition.getId());
        ActivityInstanceStateHandler activityInstanceStateHandler = engineConfiguration.getActivityInstanceStateHandler();
        ActivityExecutionTree activityExecutionTree = engineConfiguration.getExecutionTreeService()
        .createExecutionTree(processInstance.getId(), processDefinitionEntity);
        ActivityExecution activityExecution = activityExecutionTree.findExecutionById(currentTask.getExecutionId());

        ActivityBehaviorFactory behaviorFactory = engineConfiguration.getActivityBehaviorFactory();
        DelegateActivityBehavior delegateActivityBehavior = (DelegateActivityBehavior) behaviorFactory.createActivityBehavior(currentTask.getTaskDefinitionKey());
        ReadOnlyActivityDefinition activityDefinition = processDefinitionEntity.findActivityDefinition(currentTask.getTaskDefinitionKey());
        ActivityExecutionEntity activityExecutionEntity = (ActivityExecutionEntity) engineConfiguration
        .getExecutionEntityManager().findById(activityExecution.getId());

        ActivityImpl activity = bpmnModel.getProcesses().get(0).findActivity(currentTask.getTaskDefinitionKey());
        activity.setActivityBehavior(delegateActivityBehavior);

        try {
// 获取当前节点
        ActivityNode currentNode = activity.getCurrentNode(activityExecutionEntity, activityExecutionTree,
        activityInstanceStateHandler, processDefinition, bpmnModel, behaviorFactory);

// 创建多个审批人节点，并添加到流程中
        List<ActivityNode> approverNodes = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
        ActivityNode newApproverNode = new ActivityNode("New Approver " + i, "This is a new approver node " + i);

        UserTask userTask = new UserTask();
        userTask.setAssignee("admin");
        userTask.setName("New Approver Task " + i);

        newApproverNode.setActivityBehavior(userTask);
        activity.addNode(newApproverNode);

        approverNodes.add(newApproverNode);
        }

// 为当前节点添加多个输出节点，与新审批人节点相连
        for (ActivityNode approverNode : approverNodes) {
        currentNode.addOutcome("Approve", approverNode);
        }
        } catch (Exception e) {
// 处理异常
        e.printStackTrace();
        }*/
