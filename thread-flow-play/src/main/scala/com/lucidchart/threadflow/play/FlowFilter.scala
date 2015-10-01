package com.lucidchart.threadflow.play

import com.lucidchart.threadflow.FlowStore
import _root_.play.api.mvc.RequestHeader

class FlowFilter(store: FlowStore) extends ActionSideEffect[({type R[_] = RequestHeader})#R] {

  protected[this] def headerName = "X-Flow-Id"

  protected[this] def `do`[A](request: RequestHeader) = {
    store.set(request.headers.get(headerName).orNull)
  }

}
