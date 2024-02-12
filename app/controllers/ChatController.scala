import play.api.mvc._
import play.api.libs.streams.ActorFlow
import akka.actor.ActorSystem
import akka.stream.Materializer
import javax.inject.Inject

class ChatController @Inject()(cc: ControllerComponents)(implicit system: ActorSystem, mat: Materializer) extends AbstractController(cc) {

  def socket = WebSocket.accept[String, String] { request =>
    ActorFlow.actorRef { out =>
      ChatActor.props(out)
    }
  }
}