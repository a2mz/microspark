package om.mrs.data

/**
  * Created by Morozov Oleksandr on 6/14/17.
  */
object DriverStage {

  trait State {
    def name: String
  }
  case object Finished extends State {
    override def name: String = "FINISHED"
  }
  case object Running extends State {
    override def name: String = "RUNNING"
  }
  case object Error extends State {
    override def name: String = "ERROR"
  }
  import play.api.libs.json._

  implicit object StateWrite extends Writes[DriverStage.State] {
    override def writes(o: State): JsValue = JsString(o.name)
  }

  implicit object StateRead extends Reads[DriverStage.State] {
    override def reads(json: JsValue): JsResult[State] = json.validate[String].map {
      case s: String if s == DriverStage.Finished.name => DriverStage.Finished
      case s: String if s == DriverStage.Running.name  => DriverStage.Running
      case s: String if s == DriverStage.Error.name    => DriverStage.Error
    }
  }

}
