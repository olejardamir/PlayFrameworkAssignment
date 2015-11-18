package controllers

import models.pdfModel
import play.api._
import play.api.mvc._
import java.io.File
import scala.collection.mutable.ListBuffer



class Application extends Controller {

  def index = Action {
    var data:String = "Select PDF file to upload"
    Ok(views.html.index(data))
  }

  /**
    * uploadFile action definition
    */
  def uploadFile = Action(parse.multipartFormData) {
    var fname:String = ""

    request =>
    request.body.file("fileUpload").map { pdf =>
      fname = pdf.filename

      val contentType = pdf.contentType.get


      pdf.ref.moveTo(new File("./public/uploaded/"+fname))
      var absolut = new File("./public/uploaded/"+fname).getAbsolutePath
      uploadToDatabase(fname,absolut)
    }.getOrElse {
      Redirect(routes.Application.index)
    }

    var data:String = getCurrentFiles()
    Ok(fname+" has been uploaded. These are the uploaded files, to date: "+data)
  }





  private def uploadToDatabase(fname:String, path:String) ={
    val pmodel: pdfModel = new pdfModel
    pmodel.insertFile(fname,path)
  }

  private def getCurrentFiles():String={
    val pmodel: pdfModel = new pdfModel
  val lb:ListBuffer[String] = pmodel.getFileNames();
    var data:String = ""
    lb.foreach(x=>data+=","+x)
    data = data.substring(1,data.length)
     data
  }


}
