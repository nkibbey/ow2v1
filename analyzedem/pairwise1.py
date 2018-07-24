# http://scikit-learn.org/stable/modules/generated/sklearn.metrics.pairwise.cosine_similarity.html

import numpy as np
from sklearn.metrics.pairwise import cosine_similarity

NFILE = "model2010_2015np6"

def getWord(word):
  arr = []
  with open(NFILE) as fp:
    for ln in fp:
      if ln.startswith(word+" "):
        arr = map(float, ln[len(word)+1:-2].split(" "))
  return arr

def printTerm(word):
  print ("{}\n{}".format(word, np.array(getWord(word))))

def getSimililarity(term1, term2):
  vec1 = np.array(getWord(term1))
  vec2 = np.array(getWord(term2))
  vec1 = vec1.reshape(1,-1) #formatting for cosine_similarity
  vec2 = vec2.reshape(1,-1)
  return cosine_similarity(vec1, vec2)

print (" {} ".format(getSimililarity("good", "carcinoma"))) # = array([[ 0.96362411]]), most similar

#cosine_similarity(x,z) # = array([[ 0.80178373]]), next most similar
#cosine_similarity(y,z) # = array([[ 0.69337525]]), least similar
