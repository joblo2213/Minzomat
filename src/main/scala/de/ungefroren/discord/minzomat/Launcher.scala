package de.ungefroren.discord.minzomat

import java.time.format.{DateTimeFormatter, DateTimeParseException}
import java.time.{LocalDate, LocalTime, OffsetDateTime, ZoneOffset}

import de.ungefroren.discord.minzomat.utils.RestartScheduler

import scala.concurrent.duration._
import scala.io.StdIn

object Launcher {

  def main(args: Array[String]): Unit = {
    val token = args.find(arg => arg.toLowerCase.startsWith("token=") || arg.toLowerCase.startsWith("t=")).map(_.substring(6)).getOrElse({
      print("Please enter the bots discord authentication token!\n> ")
      StdIn.readLine()
    })
    val restart = args.find(_.toLowerCase.startsWith("restart=")).map(arg => {
      try {
        val t = LocalTime.parse(arg.substring(8), DateTimeFormatter.ISO_TIME)
        if (t.isBefore(LocalTime.now())) t.atDate(LocalDate.now()).plusDays(1) else t.atDate(LocalDate.now())
      } catch {
        case e: DateTimeParseException =>
          System.err.println(s"Invalid restart time: ${e.getMessage}")
          sys.exit(-1)
          null
      }
    }).map(t => RestartScheduler(t.atOffset(OffsetDateTime.now().getOffset), 10 seconds, 6))
    new Minzomat(token, restart).init()
  }

}
