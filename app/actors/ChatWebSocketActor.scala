package actors

import org.apache.pekko.actor.{Actor, ActorRef, Props}
import play.api.libs.json.{Json, JsSuccess}
import models.{ChatMessage, BroadcastChatMessage}
import actors.MessageBrokerActor.{Register, Unregister, Broadcast}
import play.api.Logger

object ChatWebSocketActor {
  def props(out: ActorRef, username: String, messageBroker: ActorRef): Props =
    Props(new ChatWebSocketActor(out, username, messageBroker))
}

class ChatWebSocketActor(out: ActorRef, username: String, messageBroker: ActorRef) extends Actor {
  private val logger = Logger(this.getClass)

  override def preStart(): Unit = {
    messageBroker ! Register(self)
  }

  override def postStop(): Unit = {
    messageBroker ! Unregister(self)
  }

  def receive = {
    case msg: String =>
    try{
      Json.parse(msg).validate[BroadcastChatMessage] match {
        case JsSuccess(broadcastMsg, _) =>
          // 文字列化されたBroadcastChatMessage型のメッセージをクライアントに送信
          out ! Json.toJson(broadcastMsg).toString()
        case _ =>
          // BroadcastChatMessage型でない場合、通常のチャットメッセージとして処理
          val chatMessage = ChatMessage(username, msg)
          // ここでselfを含めてMessageBrokerActorにメッセージを送信
          messageBroker ! Broadcast(self, chatMessage)
      }
    } catch {
         case e: Exception =>
          // JSON形式でない場合の処理
          logger.error("Received a non-JSON message", e)
          val chatMessage = ChatMessage(username, msg)
          // ここでselfを含めてMessageBrokerActorにメッセージを送信
          messageBroker ! Broadcast(self, chatMessage)    
    }
  }
}