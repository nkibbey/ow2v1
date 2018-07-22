##Hello, this is the stupid implementation of Word Embeddings

###Idea:
We want to know similar words in  a space of abstracts. If word A shows up in an abstract 
then the other words in that abstract are now word A's context. If word B shows up in 
word A's context in the top 50% of all other occurring words we say word B is relevant to A.

If we look at the list of all relevant words to A and B if they both have this program states
they are similar. If the relation is not mutually we say it might be related and if neither
are found in the "relevant context" of the other then it is less likely they are related.

####Build:
```
mvn clean package
```

####Run:

```
java -jar target/<jar-with-dependencies>.jar <input_file_to_pubmed_xml>
```

