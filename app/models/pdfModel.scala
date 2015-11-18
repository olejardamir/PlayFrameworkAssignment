package models

import play.db.DB;
import play.api.Play.current
import java.sql._

import scala.collection.mutable.ListBuffer


    class pdfModel {

  def getFileNames(): ListBuffer[String] = {
    val connection = DB.getConnection()
    val statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
    val query = "SELECT filename FROM files"
    val resultset = statement.executeQuery(query)
    var lb:ListBuffer[String] = new ListBuffer[String];

    while (resultset.next()) {
      lb+=resultset.getString("filename")
    }
    connection.close()
    lb
  }

      //YOU MUST CONFIGURE AppArmor !!! https://en.wikipedia.org/wiki/AppArmor !!!
  def insertFile(filename: String, path:String) {
    val connection = DB.getConnection()
    val statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
    val query = "INSERT INTO files (filename, data) VALUES ('" + filename +
      "', LOAD_FILE('." +
      path +
      "'));"
    val resultset = statement.executeUpdate(query)
    connection.close()

  }




}