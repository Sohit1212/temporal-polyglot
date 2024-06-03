package polyglot;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import io.temporal.serviceclient.WorkflowServiceStubs;

// test plans service should execute the the workflow
public class WorkflowStarter {
    public static void main(String[] args) throws Exception {

        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();

        WorkflowClient client = WorkflowClient.newInstance(service);

        WorkflowOptions options = WorkflowOptions.newBuilder()
                .setWorkflowId("polyglot-workflow")
                .setTaskQueue("polyglot-java-taskqueue")
                .build();

        PolyglotWorkflow workflow = client.newWorkflowStub(PolyglotWorkflow.class, options);

        String greeting = workflow.pythonActivity(args[0]);

        String workflowId = WorkflowStub.fromTyped(workflow).getExecution().getWorkflowId();

        System.out.println(workflowId + " " + greeting);
        System.exit(0);
    }
}
