import asyncio
from temporalio import worker, client # type: ignore
from temporalio import activity

import logging

logging.basicConfig(level=logging.DEBUG)

@activity.defn(name="PythonHelloWorld")
async def PythonHelloWorld(name: str) -> str:
    return f"Hello {name}"


async def main():
    temporal_client = await client.Client.connect("localhost:7233")

    greeting_worker =  worker.Worker(
        client=temporal_client,
        task_queue="polyglot-taskqueue",
        activities=[PythonHelloWorld],
    )
    await greeting_worker.run()

if __name__ == "__main__":
    asyncio.run(main())
