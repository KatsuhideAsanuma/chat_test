package actors

import org.apache.pekko.actor.{Actor, ActorRef, Props}
import play.api.libs.json.Json
import models.ChatMessage

object MessageBrokerActor {
  def props = Props[MessageBrokerActor]

  case class Register(actorRef: ActorRef)
  case class Unregister(actorRef: ActorRef)
  case class Broadcast(message: ChatMessage)
}

class MessageBrokerActor extends Actor {
  import MessageBrokerActor._

  private var clients: Set[ActorRef] = Set.empty

  def receive = {
    case Register(actorRef) =>
      clients += actorRef
    case Unregister(actorRef) =>
      clients -= actorRef
    case Broadcast(message) =>
      clients.foreach(_ ! Json.toJson(message).toString)
  }
}