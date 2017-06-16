package a2mz.microspark.data

/**
  * Created by Morozov Oleksandr on 6/14/17.
  */
case class Response(
    action: Action.ActionType,
    driverState: Option[DriverStage.State],
    serverSparkVersion: String,
    submissionId: String,
    success: Boolean,
    workerHostPort: Option[String],
    workerId: Option[String],
    message: Option[String]
)

object Response {
  import play.api.libs.functional.syntax._
  import play.api.libs.json._

  implicit val responseWrite: Writes[Response] = (
    (__ \ 'action).write[Action.ActionType] and
      (__ \ 'driverState).writeNullable[DriverStage.State] and
      (__ \ 'serverSparkVersion).write[String] and
      (__ \ 'submissionId).write[String] and
      (__ \ 'success).write[Boolean] and
      (__ \ 'workerHostPort).writeNullable[String] and
      (__ \ 'workerId).writeNullable[String] and
      (__ \ 'message).writeNullable[String]
  )(unlift(Response.unapply))

  implicit val responseReads: Reads[Response] = (
    (__ \ 'action).read[Action.ActionType] and
      (__ \ 'driverState).readNullable[DriverStage.State] and
      (__ \ 'serverSparkVersion).read[String] and
      (__ \ 'submissionId).read[String] and
      (__ \ 'success).read[Boolean] and
      (__ \ 'workerHostPort).readNullable[String] and
      (__ \ 'workerId).readNullable[String] and
      (__ \ 'message).readNullable[String]
  )(Response.apply _)
}
