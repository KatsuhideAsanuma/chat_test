package actors

import org.apache.pekko.actor.{Actor, ActorRef, Props}
import play.api.libs.json.Json
import play.api.Logger
import models.{ChatMessage, BroadcastChatMessage, ChatMessageTrait}

object MessageBrokerActor {
  def props = Props[MessageBrokerActor]

  case class Register(actorRef: ActorRef)
  case class Unregister(actorRef: ActorRef)
  case class Broadcast(sender: ActorRef, message: ChatMessageTrait)
}

class MessageBrokerActor extends Actor {
  import MessageBrokerActor._
  private val logger = Logger(this.getClass)
  private var clients: Set[ActorRef] = Set.empty

  def receive = {
    case Register(actorRef) =>
      clients += actorRef
    case Unregister(actorRef) =>
      clients -= actorRef
    case Broadcast(sender, message: ChatMessage) if sender != self =>
      // ChatMessageを受け取ったら、BroadcastChatMessageを生成してブロードキャスト
      val broadcastMessage = BroadcastChatMessage(message.username, message.message, "for all")
      val messageJson = Json.toJson(broadcastMessage).toString()
      clients.foreach(_ ! messageJson)
    case Broadcast(sender, message: BroadcastChatMessage) if sender != self =>
      // BroadcastChatMessageをそのままブロードキャスト
      val messageJson = Json.toJson(message).toString()
      clients.foreach(_ ! messageJson)
  }
}