package a2mz.microspark.data

/**
  * Created by Morozov Oleksandr on 6/14/17.
  */
case class Request(
    action: Action.ActionType,
    appArgs: Array[String],
    appResource: String,
    clientSparkVersion: String,
    environmentVariables: String,
    mainClass: String,
    sparkProperties: Map[String, String]
)

object Request {
  import play.api.libs.functional.syntax._
  import play.api.libs.json._

  implicit val requestWrite: Writes[Request] = (
    (__ \ 'action).write[Action.ActionType] and
      (__ \ 'appArgs).write[Array[String]] and
      (__ \ 'appResource).write[String] and
      (__ \ 'clientSparkVersion).write[String] and
      (__ \ 'environmentVariables \ 'SPARK_ENV_LOADED).write[String] and
      (__ \ 'mainClass).write[String] and
      (__ \ 'sparkProperties).write[Map[String, String]]
  )(unlift(Request.unapply))

  implicit val requestReads: Reads[Request] = (
    (__ \ 'action).read[Action.ActionType] and
      (__ \ 'appArgs).read[Array[String]] and
      (__ \ 'appResource).read[String] and
      (__ \ 'clientSparkVersion).read[String] and
      (__ \ 'environmentVariables \ 'SPARK_ENV_LOADED).read[String] and
      (__ \ 'mainClass).read[String] and
      (__ \ 'sparkProperties).read[Map[String, String]]
  )(Request.apply _)
}
