faketext 
--------

Generate semi-realistic sentences from an existing text.

```
Usage: java -jar faketext.jar [options] <file>...

  -n <value> | --numlines <value>
        Number of lines of fake text to generate
  -m <value> | --maxwords <value>
        Maximum number of words per sentence
  -d | --dontsplit
        Allow pairs to continue across sentence boudaries
  <file>...
        File(s) for text source
```
Running tests
```
  > cd dist
  > sbt test
```

Build jar with dependencies (target/scala-2.11/faketext.jar)
```
  > cd dist
  > sbt assmebly
```

Assumptions

* Word pair list can have duplicates
* Punctution is simply removed (apart from full stops). This can lead to some issues (e.g. hypens).
* Once selected, a next word from a word pair list is not removed (over selection)
