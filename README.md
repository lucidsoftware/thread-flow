# thread-flow

Thread-flow is a collection of libraries for tracking execution flow through asynchronous or
distributed systems on the JVM.

The "thread" in "thread-flow" can be literal JVM threads, or a more generic notion of a "series of
related computation", e.g. HTTP requests in a services-oriented architecture.

## Libraries

Thread-flow supports several common JVM concurrent or distributed systems.

* thread-flow-core - Common APIs / functionality
* thread-flow-java - Thread flow for JDK concurrency
* thread-flow-scala - Thread flow for scala-library concurrency
* thread-flow-slf4j - Uses MDC
* thread-flow-akka - Thread flow for [Akka](http://akka.io/)
* thread-flow-play - Reads request headers in [Play framework](https://www.playframework.com/)
* thread-flow-httpclient - Adds request headers for Apache's [httpclient](https://hc.apache.org/httpcomponents-client-ga/)

Each library depends on an absolute minimum of dependencies. For example, thread-flow-core has no
dependencies, and thread-flow-java only depends on thread-flow-core.

All libraries are compatible with Java 6+.

## Examples

### Java

```java
import com.lucidchart.threadflow.ThreadLocalStore
import com.lucidchart.threadflow.java.Flow

Flow flow = new Flow(new ThreadLocalStore);
ExecutorService executorService = Executors.newFixedThreadPool(8);

flow.setValue("foo");
for (int i = 0; i < 5; i++) {
    final int x = i;
    executorService.submit(new Runnable() {
        public void run() {
            System.out.println(String.format("%d %s", i, flow.getValue()));
            Thread.sleep(1);
        }
    });
}

flow.setValue("bar");
for (int i = 5; i < 10; i++) {
    final int x = i;
    executorService.submit(new Runnable() {
        public void run() {
            System.out.println(String.format("%d %s", i, flow.getValue()));
            Thread.sleep(1);
        }
    });
}
```

prints

```
0 foo
5 bar
1 foo
6 bar
2 foo
7 bar
3 foo
8 bar
4 foo
9 bar
```

### Scala

The equivalent of the Java example is

```scala
import com.lucidchart.threadflow.ThreadLocalStore
import com.lucidchart.threadflow.scala.Flow
import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext

val flow = Flow(new ThreadLocalStore)
implicit val executionContext = flow(
    ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(8))
)


flow.value = Some("foo")
(0 until 5).foreach(i => Future {
  println(s"$i ${flow.value.getOrElse("")}")
  Thread.sleep(1)
})

flow.value = Some("bar")
(5 until 10).foreach(i => Future {
  println(s"$i ${flow.value.getOrElse("")}")
  Thread.sleep(1)
})
```

### Apache Http Client

```java
import com.lucidchart.threadflow.ThreadLocalStore;
import com.lucidchart.threadflow.http.HttpFlow;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpRequest;

HttpFlow flow = new HttpFlow(new ThreadLocalStore);
HttpClient client = flow.addFlowHeader(HttpClients.custom())
    .build()

try {
    flow.setValue("foo");
    HttpResponse response = client.execute(new HttpGet("http://httpbin.org/headers"));
    System.out.println(EntityUtils.toString(response.getEntity));
} finally {
    client.close();
}
```

prints

```
{
  "headers": {
    "Accept-Encoding": "gzip,deflate",
    "Host": "httpbin.org",
    "User-Agent": "Apache-HttpClient/4.5 ...",
    "X-Flow-Id": "foo"
  }
}
```

### Akka

Currently, this only supports in-process actor systems.

Replace `default-dispatcher.executor`. Move the existing value to
`default-dispatcher.flow.proxy-executor`, and make sure to leave appropriate configuration for the
proxied `ExecutorServiceFactorProvider`.


```hocon
akka {
    actor {
        default-dispatcher {
            executor = com.lucidchart.threadflow.akka.GlobalFlowExecutorConfigurator
            flow {
                proxy-executor = fork-join-executor
            }

            fork-join-executor {
                parallelism-factor = 1.0
                parallelism-max = 100
            }
        }
    }
}
```

This uses `ThreadLocalStore.GLOBAL`. To use a different `FlowStore`, extend
`FlowExecutorConfigurator`.

```scala
package example

class ExampleFlowExecutorConfigurator(config: Config, prerequisites: DispatcherPrerequisites)
    extends FlowExecutorConfigurator(new ThreadLocalStore, config, prerequisites)
```

## Implementation

The same `FlowStore` should be used across all concurrency boundaries on a JVM.

For example, if you have multiple actor systems, they should all use the same `FlowStore`.

Using `ThreadLocalStore.GLOBAL` is a convenient way to do this.


## Status

Thread-flow is currently in active development. No artifacts have been published.
