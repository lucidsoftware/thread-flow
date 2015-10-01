package com.lucidchart.threadflow.test

import com.lucidchart.threadflow.FlowStore

class MockFlowStore extends FlowStore {
  private[this] var value: String = null

  def get = value

  def set(value: String) = this.value = value

}
