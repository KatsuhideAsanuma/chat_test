package actors

import org.apache.pekko.actor.{Actor, ActorRef, Props}

object ChatWebSocketActor {
  def props(out: ActorRef): Props = Props(new ChatWebSocketActor(out))
}

class ChatWebSocketActor(out: ActorRef) extends Actor {
  def receive = {
    case msg: String =>
      // クライアントから受け取ったメッセージをエコーバックする
      out ! ("I received your message: " + msg)
  }
}