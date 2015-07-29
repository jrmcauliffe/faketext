package org.birchavenue.faketext

import scopt._
import java.io.File
import scala.io.Source

object Main {

  // For command line configuration
  case class Config(numLines: Int = 1, maxWords:Int = 50, verbose: Boolean = false, 
                    dontSplitSentences: Boolean = false, files: Seq[File] = Seq())
  
  val parser = new scopt.OptionParser[Config]("java -jar faketext.jar") {
    head("faketext", "0.0.1")
    opt[Int]('n', "numlines") action { (x, c) => c.copy(numLines = x) } text("Number of lines of fake text to generate")
    opt[Int]('m', "maxwords") action { (x, c) => c.copy(maxWords = x) } text("Maximum number of words per sentence")
    opt[Unit]('d', "dontsplit") action { (_, c) => c.copy(verbose = true) } text("Allow pairs to continue across sentence boudaries")
    arg[File]("<file>...") unbounded() action { (x, c) => c.copy(files = c.files :+ x) } text("File(s) for text source")
  }
    
  def main(args: Array[String]) {
    
    // Check command line arguments
    parser.parse(args, Config()) match {
      case Some(config) =>

       // Get the contents of each file as a string
       val validFiles = config.files.filter(_.exists())
       val contents = validFiles.map(f => scala.io.Source.fromFile(f).getLines. mkString("\n"))

       // Make sure we have valid file(s)
       contents.length match {
         case 0 => println("No valid files found")
         case _ => {
           
            // Create the Generator
           val generator = new Generator(contents, !config.dontSplitSentences)
        
           // Generate some fake text
           generator.generate(config.numLines, config.maxWords).foreach(println)
         }
         
       }
       
      case None =>
      // arguments are bad, error message will have been displayed
    }
  }
 
}