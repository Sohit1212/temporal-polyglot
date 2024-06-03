package polyglot;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;

// test plans service should execute the the workflow
public class WorkflowStarter {

    public static final String TASK_QUEUE = "polyglot-java-taskqueue";

    public static void main(String[] args) throws Exception {

        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();

        WorkflowClient client = WorkflowClient.newInstance(service);

        WorkerFactory factory = WorkerFactory.newInstance(client);

        // create a new worker
        Worker worker = factory.newWorker(TASK_QUEUE);

        // register the java workflow
        worker.registerWorkflowImplementationTypes(PolyglotWorkflowImpl.class);

        // start the worker
        factory.start();

        System.out.println("Started the Worker, starting workflow...");

        WorkflowOptions options = WorkflowOptions.newBuilder()
                .setWorkflowId("polyglot-workflow")
                .setTaskQueue(TASK_QUEUE)
                .build();

        PolyglotWorkflow workflow = client.newWorkflowStub(PolyglotWorkflow.class, options);

        // execute the workflow
        String greeting = workflow.pythonActivity(args[0]);

        String workflowId = WorkflowStub.fromTyped(workflow).getExecution().getWorkflowId();

        System.out.println(workflowId + " " + greeting);
        System.exit(0);
    }
}
