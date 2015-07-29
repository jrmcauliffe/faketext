package org.birchavenue.faketext

import org.specs2.mutable.Specification

class GeneratorSpec extends Specification {

  "The 'removePunctuation' funciton should" >> {
    val gen = new Generator(Nil, false)

    "Remove except [A-Z][a-z][0-9]" >> {
      gen.removePunctuation("""a~!@#$%^&*()_+{}|:"<09>?[]\;',.//b""", false) must beEqualTo("a09b")
    }
    "Leave in full stop when required" >> {
      gen.removePunctuation("""a~!@#$%^&*()_+{}|:"<09>?[]\;',.//b""", true) must beEqualTo("a09.b")
    }
  }

  "The 'tokenise' funciton should" >> {
    val gen = new Generator(Nil, false)

    "Remove double spaces" >> {
      gen.tokenise("a  b") must beEqualTo(Array(Array("a", "b")))
    }
    "Remove newlines" >> {
      gen.tokenise("a\n b") must beEqualTo(Array(Array("a", "b")))
    }
    "Convert basic sentences" >> {
      gen.tokenise("a. b") must beEqualTo(Array(Array("a"), Array("b")))
    }
    "Convert sentences with newlines" >> {
      gen.tokenise("a.\n b") must beEqualTo(Array(Array("a"), Array("b")))
    }
    "Handle fullstops without spaces" >> {
      gen.tokenise("a.b") must beEqualTo(Array(Array("a"), Array("b")))
    }
    "Convert sentences with leading whitespace" >> {
      gen.tokenise("  a.\n b") must beEqualTo(Array(Array("a"), Array("b")))
    }
    "Convert sentences with trailing whitespace" >> {
      gen.tokenise("a.\n b   ") must beEqualTo(Array(Array("a"), Array("b")))
    }
    "Convert longer sentences" >> {
      gen.tokenise("This is my first sentence. This is my second") must beEqualTo(Array(Array("This", "is", "my", "first", "sentence"), Array("This", "is", "my", "second")))
    }
  }

  "The Generator should" >> {
    val sample1 = "I wish I may I wish I might"
    val sample2 = "I wish I might have a second sentence"
    val sample3 = "a a a"
    
    val gen1 = new Generator(Seq(sample1), false)
    val gen2 = new Generator(Seq(sample1 + ". " +  sample2), true)
    val gen3 = new Generator(Seq(sample1, sample2), false)
    val gen4 = new Generator(Seq(sample3), true)

   "Correctly parse a basic sentence into words" >> {
      gen1.sentences must beEqualTo(Array(Array("I", "wish", "I", "may", "I", "wish", "I", "might")))
    }

   "Split into sentences if required" >> {
      gen2.sentences must beEqualTo(Array(Array("I", "wish", "I", "may", "I", "wish", "I", "might"), 
                                          Array("I", "wish", "I", "might", "have", "a", "second", "sentence")))
    }

    "Correctly create sentence(s) for mulitple files" >> {
      gen3.sentences must beEqualTo(Array(Array("I", "wish", "I", "may", "I", "wish", "I", "might"), 
                                          Array("I", "wish", "I", "might", "have", "a", "second", "sentence")))
    }
    
    "Correctly create a pair map from a basic sentence" >> {
      gen1.pairMap must havePairs(("I", "wish") -> List("I", "I"),("I", "may") -> List("I"),
                                  ("may", "I") -> List("wish"), ("wish", "I") -> List("may","might"))
    }
    "Correctly create a pair map from a split text" >> {
      gen2.pairMap must havePairs(("I", "wish") -> List("I", "I", "I"),("I", "may") -> List("I"),
                                  ("may", "I") -> List("wish"), ("wish", "I") -> List("may","might","might"),
                                  ("have","a") -> List("second"), ("might","have") -> List("a"),("a","second") -> List("sentence")                                  )
    }
    "Correctly create a pair map from multiple files" >> {
      gen3.pairMap must havePairs(("I", "wish") -> List("I", "I", "I"),("I", "may") -> List("I"),
                                  ("may", "I") -> List("wish"), ("wish", "I") -> List("may","might","might"),
                                  ("have","a") -> List("second"), ("might","have") -> List("a"),("a","second") -> List("sentence")                                  )
    }
    "Stop at maximum word count" >> {
      gen4.generate(1, 10).head.split(' ').length must beEqualTo(10)
    }
    "Generate the correct number of fake text sentences" >> {
      gen4.generate(99, 10).length must beEqualTo(99)
    }

  }

}