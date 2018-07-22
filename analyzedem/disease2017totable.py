import gensim
import numpy as np

diseases = ["chlamydia", "syphilis", "tetanus", "lice", "schistosomiasis", "filariasis", "zika", "measles", "endocarditis", "pericarditis", "hypogonadism", "pancreatitis", "hyperparathyroidism", "hypopituitarism", "mucositis", "gastritis", "carcinoma", "mesothelioma", "dementia", "narcolepsy"]

def batch_compare_words_json(model):
    f = open("dispDiseaseTable.js", "w+")
    f.write("diseaseVals = [ \n")
    data_dist = []
    counter=0
    for i in range(0, len(diseases)):
        f.write("{ ")
        f.write("\"name\": \"{}\",".format(diseases[i]))
        for j in range(0, len(diseases)):
            fromend = len(diseases)-j-1
            f.write(" \"{}\":{},".format( diseases[fromend], model.similarity(diseases[i], diseases[fromend])))
            if (j<i):
                counter+=1
                data_dist.append(model.similarity(diseases[i],diseases[j]))
        f.write(" }, \n")

    f.write("];\n")
    data_dist = np.array(data_dist)
    f.write("extraInfo = {\n")
    f.write("\"average\":{}, \"upper\":{}, \"lower\":{}, \"count\":{}".format(
        (np.percentile(data_dist, 50)), (np.percentile(data_dist, 75)), (np.percentile(data_dist, 25)), counter))
    f.write("\n}; \n")
    f.close()

def batch_compare_words(model):
    f = open("printdisp.txt", "w+")
    f.write("upper")

    for s in diseases:
        f.write("\t{:.6}".format(s))
    for i in range(0, len(diseases)):
        f.write("\n{:.6}".format(diseases[i]))

        for j in range(0, len(diseases)):

            f.write("\t{0:.4f}".format(model.similarity(diseases[i], diseases[j])))
    f.close()


model = gensim.models.KeyedVectors.load_word2vec_format('../models/model_2017', binary=False)
batch_compare_words(model)