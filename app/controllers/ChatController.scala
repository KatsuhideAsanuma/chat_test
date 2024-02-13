package controllers

import javax.inject.Inject
import org.apache.pekko.actor.ActorSystem
import org.apache.pekko.stream.Materializer
import play.api.libs.streams.ActorFlow
import play.api.mvc._
import actors.ChatWebSocketActor

class ChatController @Inject()(cc: ControllerComponents)(implicit system: ActorSystem, mat: Materializer) extends AbstractController(cc) {
  def socket = WebSocket.accept[String, String] { request =>
    ActorFlow.actorRef { out =>
      ChatWebSocketActor.props(out)
    }
  }
}