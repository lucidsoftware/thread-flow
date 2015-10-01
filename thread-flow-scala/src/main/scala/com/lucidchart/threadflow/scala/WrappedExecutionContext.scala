package com.lucidchart.threadflow.scala

import scala.concurrent.ExecutionContext

class WrappedExecutionContext(underlying: ExecutionContext) extends ExecutionContext {

  def execute(runnable: Runnable) = underlying.execute(runnable)

  def reportFailure(t: Throwable) = underlying.reportFailure(t)

}
