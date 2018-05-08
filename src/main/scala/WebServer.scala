import akka.Done
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._

import scala.concurrent.Future

object WebServer {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("web-server")
    implicit val materializer = ActorMaterializer()
    implicit val context = system.dispatcher

    var tableOrder: List[Item] = List.empty

    final case class Item (id: Long, name:String)
    final case class Order (items:List[Item])

    def fetchItem(itemId: Long): Future[Option[Item]] = Future {
      tableOrder.find(x => x.id == itemId)
    }

    def saveOrder(order: Order): Future[Done] = {
      tableOrder = order match {
        case Order(items) => items ::: tableOrder
        case _ => tableOrder
      }
      Future {Done}
    }

    implicit val itemFormatter = jsonFormat2(Item)
    implicit val orderFormatter = jsonFormat1(Order)

    val route = get {
      pathPrefix("item" / LongNumber) { id =>
        onSuccess(fetchItem(id)) {
          case Some(items) => complete(items)
          case None => complete(StatusCodes.NotFound)
        }
      }
    } ~
    post {
      path("create-order") {
        entity(as[Order]) { order =>
          val saved = saveOrder(order)
          onComplete(saved) { done =>
            complete("order created")
          }
        }
      }
    }

    val binding = Http().bindAndHandle(route, "localhost", 8080)
    println("Server started ....")
  }
}
