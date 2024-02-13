package actors

import org.apache.pekko.actor.{Actor, ActorRef, Props}
import play.api.libs.json.Json
import models.ChatMessage

object ChatWebSocketActor {
  def props(out: ActorRef, username: String, messageBroker: ActorRef): Props =
    Props(new ChatWebSocketActor(out, username, messageBroker))
}

class ChatWebSocketActor(out: ActorRef, username: String, messageBroker: ActorRef) extends Actor {
  import MessageBrokerActor._

  override def preStart(): Unit = {
    messageBroker ! Register(self)
  }

  override def postStop(): Unit = {
    messageBroker ! Unregister(self)
  }

  def receive = {
    case msg: String =>
      val chatMessage = ChatMessage(username, msg)
      messageBroker ! Broadcast(chatMessage)
  }
}