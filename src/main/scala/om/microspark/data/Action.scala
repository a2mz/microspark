package om.microspark.data

import play.api.libs.json._

/**
  * Created by Morozov Oleksandr on 6/14/17.
  */
object Action {
  trait ActionType {
    def name: String
  }

  case object CreateSubmissionResponse extends ActionType {
    override def name: String = "CreateSubmissionResponse"
  }
  case object SubmissionStatusResponse extends ActionType {
    override def name: String = "SubmissionStatusResponse"
  }
  case object KillSubmissionResponse extends ActionType {
    override def name: String = "KillSubmissionResponse"
  }
  case object CreateSubmissionRequest extends ActionType {
    override def name: String = "CreateSubmissionRequest"
  }

  implicit object ActionTypeWriter extends Writes[Action.ActionType] {
    override def writes(o: Action.ActionType): JsValue = JsString(o.name)
  }

  implicit object ActionTypeReader extends Reads[Action.ActionType] {
    override def reads(json: JsValue): JsResult[Action.ActionType] = json.validate[String].map {
      case s: String if s == CreateSubmissionResponse.name => CreateSubmissionResponse
      case s: String if s == SubmissionStatusResponse.name => SubmissionStatusResponse
      case s: String if s == KillSubmissionResponse.name   => KillSubmissionResponse
      case s: String if s == CreateSubmissionRequest.name  => CreateSubmissionRequest
    }
  }
}
