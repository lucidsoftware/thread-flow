package com.lucidchart.threadflow.scala

import com.lucidchart.threadflow.BaseFlow

trait ScalaOptionFlow {
  self: BaseFlow =>

  def value = Option(store.get)

  def value_=(value: Option[String]) = store.set(value.orNull)

}