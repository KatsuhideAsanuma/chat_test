package actors

import org.apache.pekko.actor.{Actor, ActorRef, Props}
import play.api.libs.json.Json
import play.api.Logger
import models.ChatMessage

object MessageBrokerActor {
  def props = Props[MessageBrokerActor]

  case class Register(actorRef: ActorRef)
  case class Unregister(actorRef: ActorRef)
  case class Broadcast(message: ChatMessage)
}

class MessageBrokerActor extends Actor {
  import MessageBrokerActor._
  private val logger = Logger(this.getClass)
  private var clients: Set[ActorRef] = Set.empty

  def receive = {
   case Register(actorRef) =>
      clients += actorRef
      logger.info(s"Actor registered: $actorRef. Total clients: ${clients.size}")
    case Unregister(actorRef) =>
      clients -= actorRef
      logger.info(s"Actor unregistered: $actorRef. Total clients: ${clients.size}")
    case Broadcast(message) =>
      val messageJson = Json.toJson(message).toString() // ここで一度だけシリアライズ
      clients.foreach(_ ! messageJson)
  }
}