package controllers

import java.nio.file.Paths
import play.api.mvc.{MessagesAbstractController, MessagesControllerComponents}
import javax.inject.Inject
import scala.concurrent.{ExecutionContext}


/**
 * This controller handles a file upload.
 */

class HomeController @Inject() (cc:MessagesControllerComponents)
                               (implicit executionContext: ExecutionContext)
  extends MessagesAbstractController(cc) {


  /**
   * Renders a start page.
   */
  def index = Action { implicit request =>
    Ok(views.html.index())
  }

  /**
   * Uploads a  file as a POST request.
   */
  def upload = Action(parse.multipartFormData) { request =>
    request.body
      .file("name")
      .map { picture =>
        val filename    = Paths.get(picture.filename).getFileName
        val fileSize    = picture.fileSize
        val contentType = picture.contentType

        picture.ref.copyTo(Paths.get(s"C:/Users/Dharmen/AppData/Local/Temp/$filename"), replace = true)
        Ok("File uploaded")
      }
      .getOrElse {
        Redirect(routes.HomeController.index).flashing("error" -> "Missing file")
      }
  }


}