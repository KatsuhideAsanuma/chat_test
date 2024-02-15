package controllers

import javax.inject.Inject
import org.apache.pekko.actor.ActorSystem
import org.apache.pekko.stream.Materializer
import play.api.libs.streams.ActorFlow
import play.api.mvc._
import play.api.Logger
import actors.{ChatWebSocketActor, MessageBrokerActor}


class ChatController @Inject()(cc: ControllerComponents)(implicit system: ActorSystem, mat: Materializer) extends AbstractController(cc) {
  private val logger = Logger(this.getClass)

  val messageBroker = system.actorOf(MessageBrokerActor.props, "messageBroker")

def socket(username: String) = WebSocket.accept[String, String] { request =>
  logger.info(s"WebSocket connection established for user: $username")
  ActorFlow.actorRef { out =>
    ChatWebSocketActor.props(out, username, messageBroker) // 修正済み
  }
}
}