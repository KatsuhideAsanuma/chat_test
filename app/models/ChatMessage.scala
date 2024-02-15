package models

import play.api.libs.json.{Format, Json, Writes}

case class ChatMessage(username: String, message: String) extends ChatMessageTrait

object ChatMessage {
  implicit val format: Format[ChatMessage] = Json.format[ChatMessage]
}

case class BroadcastChatMessage(username: String, message: String, additionalInfo: String) extends ChatMessageTrait

object BroadcastChatMessage {
  // BroadcastChatMessageのためのJson Writesを定義
  implicit val format: Format[BroadcastChatMessage] = Json.format[BroadcastChatMessage]
}