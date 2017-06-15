package om.mrs

import a2mz.mrs.Submission
import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.{ActorMaterializer, Materializer}
import om.mrs.data.Action.SubmissionStatusResponse
import om.mrs.data._
import play.api.libs.json.Json

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}
import scala.util.{Failure, Success, Try}

/**
  * Created by Morozov Oleksandr on 6/13/17.
  */



object Runn extends App {
  val r = Response(
    Action.CreateSubmissionResponse,
    Some(DriverStage.Running),
    "1.2.3",
    "driver-20151008145126-0000",
    true,
    Some("192.168.3.153:46894"),
    Some("worker-20151007093409-192.168.3.153-46894"),
    Some("some-message")
  )
  println(Json.toJson(r))
  val x: Response = Json.toJson(r).as[Response]
  println(x)

  val submission1 =
    "{\n  \"action\" : \"CreateSubmissionResponse\",\n  \"message\" : \"Driver successfully submitted as driver-20151008145126-0000\",\n  \"serverSparkVersion\" : \"1.5.0\",\n  \"submissionId\" : \"driver-20151008145126-0000\",\n  \"success\" : true\n}"

  println(Json.toJson(Json.parse(submission1)).as[Response])

  val status = "{\n  \"action\" : \"SubmissionStatusResponse\",\n  \"driverState\" : \"FINISHED\",\n  \"serverSparkVersion\" : \"1.5.0\",\n  \"submissionId\" : \"driver-20151008145126-0000\",\n  \"success\" : true,\n  \"workerHostPort\" : \"192.168.3.153:46894\",\n  \"workerId\" : \"worker-20151007093409-192.168.3.153-46894\"}"
  println(Json.toJson(Json.parse(status)).as[Response])

  val kill = "{\n  \"action\" : \"KillSubmissionResponse\",\n  \"message\" : \"Kill request for driver-20151008145126-0000 submitted\",\n  \"serverSparkVersion\" : \"1.5.0\",\n  \"submissionId\" : \"driver-20151008145126-0000\",\n  \"success\" : true\n}"

  println(Json.toJson(Json.parse(kill)).as[Response])

  val request = "{\n  \"action\" : \"CreateSubmissionRequest\",\n  \"appArgs\" : [ \"myAppArgument1\" ],\n  \"appResource\" : \"file:/jar/spark_jobs-1.0-75439.jar\",\n  \"clientSparkVersion\" : \"1.5.0\",\n  \"environmentVariables\" : {\n    \"SPARK_ENV_LOADED\" : \"1\"\n  },\n  \"mainClass\" : \"filter.RunFiltering\",\n  \"sparkProperties\" : {\n    \"spark.jars\" : \"file:/jar/spark_jobs-1.0-75439.jar\",\n    \"spark.cassandra.connection.host\" : \"cassandra\",\n    \"spark.driver.supervise\" : \"false\",\n    \"spark.app.name\" : \"MyJob2\",\n    \"spark.eventLog.enabled\": \"true\",\n    \"spark.submit.deployMode\" : \"cluster\",\n    \"spark.master\" : \"spark://spark-master:6066\"\n  }\n}"
  val request1 = "{\n  \"action\" : \"CreateSubmissionRequest\",\n\n  \"appResource\" : \"file:/myfilepath/spark-job-1.0.jar\",\n  \"clientSparkVersion\" : \"1.5.0\",\n  \"environmentVariables\" : {\n    \"SPARK_ENV_LOADED\" : \"1\"\n  },\n  \"mainClass\" : \"com.mycompany.MyJob\",\n  \"sparkProperties\" : {\n    \"spark.jars\" : \"file:/myfilepath/spark-job-1.0.jar\",\n    \"spark.driver.supervise\" : \"false\",\n    \"spark.app.name\" : \"MyJob\",\n    \"spark.eventLog.enabled\": \"true\",\n    \"spark.submit.deployMode\" : \"cluster\",\n    \"spark.master\" : \"spark://spark-cluster-ip:6066\"\n  }\n}"
  val re3 = Json.toJson(Json.parse(request)).as[Request]
  println(re3)
  println(Json.prettyPrint(Json.toJson(re3)))

  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: Materializer = ActorMaterializer()

  val submission = Submission("0.0.0.0", 6066)
  val submmit = submission.submit(re3)

  case class FutureEither[L, +R](future: Future[Either[L, R]]) extends AnyVal {
    def flatMap[R2](f: R => FutureEither[L, R2])(implicit ec: ExecutionContext): FutureEither[L, R2] = {
      val newFuture = future.flatMap {
        case Right(r) => f(r).future
        case Left(l) => Future.successful(Left(l))
      }
      FutureEither(newFuture)
    }

    def map[R2](f: R => R2)(implicit ec: ExecutionContext): FutureEither[L, R2] = {
      FutureEither(future.map(either => either.right map f))
    }

  }

  import scala.concurrent.ExecutionContext.Implicits.global

  val fff: FutureEither[HttpRequestError, Response] = for {
    isubmit <- FutureEither(submission.submit(re3))




    istat <- FutureEither(submission.status(isubmit.submissionId))
  // sId <- submit.map(_.submissionId)
  //    status <- submission.status(sId)

  } yield istat
  /*status match {
          case Left(d) => println("ok>" + d)
          case Right(f) => println(s"-$f-")

}
*/

  fff.future.onComplete {
  case Success(s) => println(s)
  case Failure(s) => println(s)
  case _ => println("!!!")
}
}

