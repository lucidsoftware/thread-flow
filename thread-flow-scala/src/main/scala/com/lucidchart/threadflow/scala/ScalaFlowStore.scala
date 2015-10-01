package com.lucidchart.threadflow.scala

import com.lucidchart.threadflow.FlowStore
import scala.language.implicitConversions

/**
 * Wraps [[FlowStore]] with methods using [[Option]]s.
 */
class ScalaFlowStore(private val underlying: FlowStore) {

  def value = Option(underlying.get)

  def value_=(value: Option[String]) = underlying.set(value.orNull)

}

object ScalaFlowStore {

  def apply(flowStore: FlowStore) = new ScalaFlowStore(flowStore)

  implicit def fromFlowStore(flowStore: FlowStore): ScalaFlowStore = apply(flowStore)

  implicit def toFlowStore(flowStore: ScalaFlowStore): FlowStore = flowStore.underlying

}
