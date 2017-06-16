package a2mz.microspark.data

/**
  * Created by Morozov Oleksandr on 6/15/17.
  */
sealed trait HttpRequestError {
  val message: String
}
case class CreateSubmissionsError(message: String) extends HttpRequestError
case class StatusSubmissionsError(message: String) extends HttpRequestError
