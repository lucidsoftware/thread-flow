package com.lucidchart.threadflow.scala

import com.lucidchart.threadflow.{AggregateFlowStore, BaseFlow, FlowStore}
import scala.concurrent.{ExecutionContext, Future}

case class Flow(store: ScalaFlowStore) {

  def withId[A](id: String)(f: => A) = {
    store.value = Some(id)
    try {
      f
    } finally {
      store.value = None
    }
  }

  def apply[A](f: () => A) = store.value.fold(f)(id => () => withId(id)(f()))

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