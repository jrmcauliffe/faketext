package org.birchavenue.faketext

class Generator(contents: Seq[String], splitSentences: Boolean) {
  
  // Sets of 'sentences' used to build pairs (If sentences are ignored, whole file contents is sentence)
  val sentences = contents.map(content => tokenise(removePunctuation(content, splitSentences))).toArray.flatten

  // Create pair maps for all sentences then group by distinct pairs creating a list of all following letters
  val pairMaps = sentences.map(s => createPairMap(s)).flatten.groupBy( _._1).mapValues(_.map(_._2))
  
  // Helper Methods
  
  // Turn a sentence into a map of adjacent pairs and possible following letters
  def createPairMap(l: Array[String]) = (l zip l.drop(1)) zip l.drop(2)

  // Remove punctuation characters with option to leave in full stop for sentence boundary
  def removePunctuation(in: String, keepFullStop: Boolean): String = keepFullStop match {
    case true => in.replaceAll("""[\p{Punct}&&[^.]]""", "")
    case false => in.replaceAll("""[\p{Punct}]""", "")
  }
  
  // Split into groups of words that will be used to build pairs
  def tokenise(in: String):Array[Array[String]] = {
    // Break out individual sentences if full stop is present
    val sentences = in.split('.')
    // Tokenise into words (collapsing whitespace)
    sentences.map(_.trim.split("\\W+"))   
  }
  
}