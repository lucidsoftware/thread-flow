package com.lucidchart.threadflow.akka

import akka.dispatch.ExecutorServiceFactoryProvider
import java.util.concurrent.ThreadFactory

/**
 * A factory factory. Thanks, Akka.
 */
trait ProxyExecutorServiceFactoryProvider extends ExecutorServiceFactoryProvider {
  self =>

  protected def proxy[A](f: () => A): () => A

  abstract override def createExecutorServiceFactory(id: String, threadFactory: ThreadFactory) = {
    new WrappedExecutorServiceFactory(super.createExecutorServiceFactory(id, threadFactory)) with ProxyExecutorServiceFactory {
      protected def proxy[A](f: () => A) = self.proxy(f)
    }
  }

}
