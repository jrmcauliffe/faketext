package org.birchavenue.faketext

import scala.annotation.tailrec

class Generator(contents: Seq[String], splitSentences: Boolean) {
  
  // Some basic types to make things easier to follow
  type Word = String
  type WordPair = Tuple2[Word, Word]
  type Sentence = Array[Word]
  
  // Sets of 'sentences' used to build pairs (If sentences are ignored, whole file contents is sentence)
  val sentences: Array[Sentence] = contents.map(content => tokenise(removePunctuation(content, splitSentences))).toArray.flatten

  // Create pair maps for all sentences then group by distinct pairs creating a list of all following letters
  val pairMap: Map[WordPair, List[Word]] = sentences.map(s => createPairMap(s)).flatten.groupBy( _._1).mapValues(_.map(_._2).toList)
  
  // Method to generate the fake text string(s)
  def generate(n: Int, maxWords: Int): List[String] = {
   
    val r = scala.util.Random
   
    @tailrec 
    def nextWord(acc: List[Word]): List[Word] = {
      val currentPair = (acc(1), acc(0))
      // Keep going until we can't find a pair, or at max words
      if(pairMap.contains(currentPair) && acc.length < maxWords) {
        // Get a new random next word
        val newWord = pairMap(currentPair)(r.nextInt(pairMap(currentPair).size))
        nextWord(newWord :: acc)
      } else acc // Reached max length     
    }
  
    // Create correct number of fake text sentences with random starting pair
    List.fill(n){
      // Random starting pair
      val start =  pairMap.keys.toList(r.nextInt(pairMap.size))
      // Generate word list (backwards for easier list calls)
      val wordlist = nextWord(List(start._2, start._1))
      // Switch around the right way and convert to string
      wordlist.reverse.mkString(" ")
    }
  }

  // Helper Methods

  // Split into groups of words that will be used to build pairs
  def tokenise(in: String):Array[Array[String]] = {
    // Break out individual sentences if full stop is present
    val sentences = in.split('.')
    // Tokenise into words (collapsing whitespace)
    sentences.map(_.trim.split("\\W+"))   
  }

  // Remove punctuation characters with option to leave in full stop for sentence boundary
  def removePunctuation(in: String, keepFullStop: Boolean): String = keepFullStop match {
    case true => in.replaceAll("""[\p{Punct}&&[^.]]""", "")
    case false => in.replaceAll("""[\p{Punct}]""", "")
  }
  
  // Turn a sentence into a map of adjacent pairs and possible following letters
  def createPairMap(l: Sentence) = (l zip l.drop(1)) zip l.drop(2)

}