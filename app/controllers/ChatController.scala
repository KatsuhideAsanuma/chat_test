import org.apache.pekko.actor.typed.ActorRef
import play.api.mvc._
import play.api.libs.streams.ActorFlow
import org.apache.pekko.actor.typed.ActorSystem
import org.apache.pekko.stream.Materializer
import javax.inject.Inject

class ChatController @Inject()(cc: ControllerComponents)(implicit system: ActorSystem[_], mat: Materializer) extends AbstractController(cc) {

  def socket = WebSocket.accept[String, String] { request =>
    ActorFlow.actorRef { out: ActorRef[String] =>
      ChatActor.props(out)
    }
  }
}