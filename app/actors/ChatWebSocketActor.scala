package actors

import org.apache.pekko.actor.{Actor, ActorRef, Props}
import play.api.libs.json.Json
import models.ChatMessage
import play.api.Logger

object ChatWebSocketActor {
  def props(out: ActorRef, username: String, messageBroker: ActorRef): Props =
    Props(new ChatWebSocketActor(out, username, messageBroker))
}



class ChatWebSocketActor(out: ActorRef, username: String, messageBroker: ActorRef) extends Actor {
  private val logger = Logger(this.getClass)

  override def preStart(): Unit = {
    messageBroker ! Register(self)
    logger.info(s"ChatWebSocketActor for $username started.")
  }

  override def postStop(): Unit = {
    messageBroker ! Unregister(self)
    logger.info(s"ChatWebSocketActor for $username stopped.")
  }

  def receive = {
    case msg: String =>
      val chatMessage = ChatMessage(username, msg)
      messageBroker ! Broadcast(chatMessage)
  }
}