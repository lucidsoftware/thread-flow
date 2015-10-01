package com.lucidchart.threadflow.play

import scala.concurrent.Future
import scala.language.higherKinds
import _root_.play.api.mvc.ActionTransformer

trait ActionSideEffect[R[_]] extends ActionTransformer[R, R] {

  protected[this] def `do`[A](request: R[A])

  protected def transform[A](request: R[A]) = {
    `do`(request)
    Future.successful(request)
  }

}
