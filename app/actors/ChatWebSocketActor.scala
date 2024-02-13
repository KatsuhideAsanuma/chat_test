package actors

import org.apache.pekko.actor.{Actor, ActorRef, Props}
import play.api.libs.json.Json
import models.ChatMessage

object ChatWebSocketActor {
  def props(out: ActorRef, username: String): Props = Props(new ChatWebSocketActor(out, username))
}

class ChatWebSocketActor(out: ActorRef, username: String) extends Actor {
  def receive = {
    case msg: String =>
      // ユーザー名を使用してメッセージをエコーバックする
      out ! Json.toJson(ChatMessage(username, "I received your message: " + msg)).toString
  }
}