package org.birchavenue.faketext

import scopt._
import java.io.File

object Main {

  // For command line configuration
  case class Config(verbose: Boolean = false, splitSentences: Boolean = false, files: Seq[File] = Seq())
  
  val parser = new scopt.OptionParser[Config]("java -jar faketext.jar") {
    head("faketext", "0.0.1")
    opt[Unit]("verbose") action { (_, c) => c.copy(verbose = true) } text("Show logging information")
    opt[Unit]("splitSentences") action { (_, c) => c.copy(verbose = true) } text("Prevent pairs from continuning across sentence boudaries")
    arg[File]("<file>...") unbounded() action { (x, c) =>
    c.copy(files = c.files :+ x) } text("File(s) for text source")
  }
    
  def main(args: Array[String]) {
    
    parser.parse(args, Config()) match {
      case Some(config) =>
        println("main" + config.verbose)

      case None =>
    // arguments are bad, error message will have been displayed
    }
  }
 
}