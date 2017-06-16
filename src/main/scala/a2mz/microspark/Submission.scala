package a2mz.microspark

import a2mz.microspark.data.{CreateSubmissionsError, HttpRequestError, StatusSubmissionsError}
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.Materializer
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import om.microspark.data._
import play.api.libs.json.Json

import scala.concurrent.{ExecutionContextExecutor, Future}

/**
  * Created by Morozov Oleksandr on 6/13/17.
  */
case class Submission(host: String, port: Int)(
    implicit system: ActorSystem,
    materializer: Materializer)
  extends PlayJsonSupport {

  private implicit def executor: ExecutionContextExecutor = system.dispatcher

  private val http = Http(system)

  private val reqHeaders: scala.collection.immutable.Seq[HttpHeader] =
    scala.collection.immutable.Seq(
      RawHeader("content-type", "application/json"),
      RawHeader("charset", "UTF-8")
    )

  private def unFuture[A, B]: (Future[Future[Either[A, B]]]) => Future[Either[A, B]] = (x) => {
    x.flatMap(y => y.map(identity))
  }

  private implicit val toArrayByte = (s: Request) => Json.toBytes(Json.toJson(s))

  def submit(request: Request): Future[Either[HttpRequestError, Response]] = {
    val uri         = Uri(s"http://$host:$port/v1/submissions/create")
    val entity      = HttpEntity(ContentTypes.`application/json`, request)
    val httpRequest = HttpRequest(HttpMethods.POST, uri, Nil, entity)
    unFuture(
      http
        .singleRequest(httpRequest)
        .map(response =>
          response.status match {
            case OK => Unmarshal(response.entity).to[Response].map(Right(_))
            case x @ _ =>
              Future.successful(
                Left[HttpRequestError, Response](CreateSubmissionsError(x.defaultMessage())))
        }))
  }

  def status(submissionId: String): Future[Either[HttpRequestError, Response]] = {
    val uri         = Uri(s"http://$host:$port/v1/submissions/status/$submissionId")
    val httpRequest = HttpRequest(HttpMethods.GET, uri, reqHeaders)
    unFuture(
      http
        .singleRequest(httpRequest)
        .map(response =>
          response.status match {
            case OK => Unmarshal(response.entity).to[Response].map(Right(_))
            case x @ _ =>
              Future.successful(
                Left[HttpRequestError, Response](StatusSubmissionsError(x.defaultMessage())))
        }))

  }
}
