package models

import play.api.libs.json.{Format, Json}

case class ChatMessage(username: String, message: String)

object ChatMessage {
  implicit val format: Format[ChatMessage] = Json.format[ChatMessage]
}