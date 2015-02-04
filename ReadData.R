fileUrl <- "/Users/AdityaPurandare/Desktop/project/MSR2015/Data/Questions.csv"
QuestionData <- read.csv(fileUrl)
head(QuestionData)
ds <- DataframeSource(QuestionData)

cleanCorpus <- function(corpus)
{
  library(SnowballC)
  corpus.tmp <- tm_map(corpus, removePunctuation)
  corpus.tmp <- tm_map(corpus.tmp, tolower)
  corpus.tmp <- tm_map(corpus.tmp, removeWords, stopwords("english"))
}
TidyCorpus<-cleanCorpus(VCorpus(ds))
inspect(TidyCorpus)