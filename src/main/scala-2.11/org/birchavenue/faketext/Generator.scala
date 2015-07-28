package org.birchavenue.faketext

class Generator(contents: Seq[String], splitSentences: Boolean) {
  
  //Sets of 'sentences' used to build pairs (If sentences are ignored, whole contents is sentenct)
  val words = contents.map(content => tokenize(removePunctuation(content, splitSentences)))

  // Remove punctuation characters with option to leave in full stop for sentence boundary
  def removePunctuation(in: String, keepFullStop: Boolean): String = keepFullStop match {
    case true => in.replaceAll("""[\p{Punct}&&[^.]]""", "")
    case false => in.replaceAll("""[\p{Punct}]""", "")
  }
  
  // Split into groups of words that will be used to build pairs
  def tokenize(in: String):Array[Array[String]] = {
    // Break out individual sentences if full stop is present
    val sentences = in.split('.')
    // Tokenise into words (collapsing whitespace)
    sentences.map(_.trim.split("\\W+"))   
  }
  
}