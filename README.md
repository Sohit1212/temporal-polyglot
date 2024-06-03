# Polyglot Example

One of the benefits of Temporal is that it provides SDKs for several
languages and you can use multiple languages in the context of a single
Workflow. For example, you might write your Workflow in Java but use
Python or TypeScript for an Activity in that workflow. 

This example demonstrates a Workflow interface and implementation that executes
an Activity called `PythonHelloWorld`. 

We need: 
- Python Worker with the implementation of the Activity
  Listening on eg. python-task-queue
- Java Workflow code calling the execute activity function on python-task-queue
- Java Workflow starter code, which will start the workflow in a separate queue eg. java-worker-queue
- Java Worker which will listen on java-worker-queue and execute the Java Workflow code.
