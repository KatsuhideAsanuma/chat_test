package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def chat = Action { implicit request: Request[AnyContent] =>
  println("home controller chat method called")
    Ok(views.html.chat("WebSocket Chat"))
  }
}