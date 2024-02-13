import org.apache.pekko.actor.typed.ActorRef
import org.apache.pekko.actor.typed.Behavior
import org.apache.pekko.actor.typed.scaladsl.Behaviors
import org.apache.pekko.actor.{Actor, Props}

object ChatActor {
  sealed trait Command
  case class SendMessage(msg: String) extends Command
  case class MessageReceived(msg: String) extends Command

  def apply(out: ActorRef[String]): Behavior[Command] = Behaviors.receiveMessage {
    case SendMessage(msg) =>
      out ! s"Received: $msg"
      Behaviors.same
  }

  // Propsメソッドを追加
  def props(out: ActorRef[String]): Props = Props(new Actor {
    def receive = {
      case SendMessage(msg) =>
        out ! s"Received: $msg"
    }
  })
}