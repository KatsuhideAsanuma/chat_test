import akka.actor._

object ChatActor {
  def props(out: ActorRef) = Props(new ChatActor(out))
}

class ChatActor(out: ActorRef) extends Actor {
  def receive = {
    case msg: String =>
      // ここでチャットメッセージを処理します
      out ! s"Received: $msg"
  }
}