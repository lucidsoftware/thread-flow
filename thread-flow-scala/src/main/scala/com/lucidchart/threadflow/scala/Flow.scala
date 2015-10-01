package com.lucidchart.threadflow.scala

import com.lucidchart.threadflow.{AggregateFlowStore, BaseFlow, FlowStore}
import scala.concurrent.{ExecutionContext, Future}

class Flow(store: FlowStore) extends BaseFlow(store) with ScalaOptionFlow {

  def withId[A](id: String)(f: => A) = {
    value = Some(id)
    try {
      f
    } finally {
      value = None
    }
  }

  def apply[A](f: () => A) = value.fold(f)(id => () => withId(id)(f()))

  def apply(executionContext: ExecutionContext): ExecutionContext = {
    new WrappedExecutionContext(executionContext) with ProxyExecutionContext {
      protected def proxy[T](f: () => T) = apply(f)
    }
  }

  def future[A](f: => A)(implicit executionContext: ExecutionContext) = {
    val g = apply(() => f)
    Future(g())
  }

}

object Flow {

  def apply(store: FlowStore, others: FlowStore*) = {
    new Flow(new AggregateFlowStore(store, others: _*))
  }

}