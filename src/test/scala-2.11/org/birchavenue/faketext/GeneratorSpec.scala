package org.birchavenue.faketext

import org.specs2.mutable.Specification


class GeneratorSpec extends Specification {
  
  val gen = new Generator(Nil, false)
  
  "The 'removePunctuation' funciton should" >> {
    "Remove except [A-Z][a-z][0-9]" >> {
      gen.removePunctuation("""a~!@#$%^&*()_+{}|:"<09>?[]\;',.//b""", false) must beEqualTo("a09b")
    }
  "Leave in full stop when required" >> {
      gen.removePunctuation("""a~!@#$%^&*()_+{}|:"<09>?[]\;',.//b""", true) must beEqualTo("a09.b")  
    }
  }
  
  "The 'tokenize' funciton should" >> {
    "Remove double spaces" >> {
      gen.tokenize("a  b") must beEqualTo(Array(Array("a","b")))
    }
    "Remove newlines" >> {
      gen.tokenize("a\n b") must beEqualTo(Array(Array("a","b")))
    }
    "Detect basic sentences" >> {
      gen.tokenize("a. b") must beEqualTo(Array(Array("a"),Array("b")))
    }
    "Detect sentences with newlines" >> {
      gen.tokenize("a.\n b") must beEqualTo(Array(Array("a"),Array("b")))
    }
   "Detect sentences with leading whitespace" >> {
      gen.tokenize("  a.\n b") must beEqualTo(Array(Array("a"),Array("b")))
    }
    "Detect sentences with trailing whitespace" >> {
      gen.tokenize("a.\n b   ") must beEqualTo(Array(Array("a"),Array("b")))
    }
    "Detect longer sentences" >> {
      gen.tokenize("This is my first sentence. This is my second") must beEqualTo(Array(Array("This","is","my","first","sentence"),Array("This","is","my","second")))
    }
  }

}