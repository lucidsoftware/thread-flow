package com.lucidchart.threadflow.scala

import scala.concurrent.ExecutionContext

trait ProxyExecutionContext extends ExecutionContext {

  protected def proxy[T](f: () => T): () => T

  abstract override def execute(runnable: Runnable) = {
    val f = proxy(() => runnable.run())
    super.execute(new Runnable {
      def run() = f()
    })
  }

}
