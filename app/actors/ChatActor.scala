import org.apache.pekko.actor.typed.ActorRef
import org.apache.pekko.actor.typed.Behavior
import org.apache.pekko.actor.typed.scaladsl.Behaviors

object ChatActor {
  // メッセージプロトコルの定義
  sealed trait Command
  case class SendMessage(msg: String) extends Command
  case class MessageReceived(msg: String) extends Command

  def apply(out: ActorRef[String]): Behavior[Command] = Behaviors.receiveMessage {
    case SendMessage(msg) =>
      out ! s"Received: $msg"
      Behaviors.same
  }
}