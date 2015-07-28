package org.birchavenue.faketext

import java.io.File

class Generator(files: Seq[File], splitSentences: Boolean) {

  def removePunctuation(in: String, keepFullStop: Boolean): String = keepFullStop match {
    case true => in.replaceAll("""[\p{Punct}&&[^.]]""", "")
    case false => in.replaceAll("""[\p{Punct}]""", "")
  }
  
  def tokenize(in: String):Array[Array[String]] = {
    // Break out individual sentences if full stop is present
    val sentences = in.split('.')
    // Tokenise into words (collapsing whitespace)
    sentences.map(_.trim.split("\\W+"))   
  }
  
}