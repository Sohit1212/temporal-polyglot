package polyglot;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import polyglot.PolyglotWorkflowImpl;

public class StartWorker {
    public static final String TASK_QUEUE = "polyglot-java-taskqueue";

    public static void main(String[] args) {

        WorkflowServiceStubs service = WorkflowServiceStubs.newInstance();
        WorkflowClient client = WorkflowClient.newInstance(service);
        WorkerFactory factory = WorkerFactory.newInstance(client);

        Worker worker = factory.newWorker(TASK_QUEUE);

        // register the java workflow
        worker.registerWorkflowImplementationTypes(PolyglotWorkflowImpl.class);

        factory.start();

    }
}
