package controllers

import javax.inject.Inject
import org.apache.pekko.actor.ActorSystem
import org.apache.pekko.stream.Materializer
import play.api.libs.streams.ActorFlow
import play.api.mvc._
import actors.{ChatWebSocketActor, MessageBrokerActor}

class ChatController @Inject()(cc: ControllerComponents)(implicit system: ActorSystem, mat: Materializer) extends AbstractController(cc) {
  val messageBroker = system.actorOf(MessageBrokerActor.props, "messageBroker")

  def socket(username: String) = WebSocket.accept[String, String] { request =>
    ActorFlow.actorRef { out => ChatWebSocketActor.props(out, username, messageBroker) }
  }
}