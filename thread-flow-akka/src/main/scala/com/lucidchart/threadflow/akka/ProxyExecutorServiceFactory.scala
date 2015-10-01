package com.lucidchart.threadflow.akka

import akka.dispatch.ExecutorServiceFactory
import com.lucidchart.threadflow.java.WrappedExecutorService

trait ProxyExecutorServiceFactory extends ExecutorServiceFactory {
  self =>

  protected def proxy[A](f: () => A): () => A

  abstract override def createExecutorService = {
    new WrappedExecutorService(super.createExecutorService) {
      protected def proxy[A](f: () => A) = self.proxy(f)
    }
  }

}
