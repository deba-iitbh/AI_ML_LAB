# Assignment - 3

# The Birds-Sound classifier App

In this engaging project, you will be working with 30-second audio samples featuring the distinct sounds of five different bird species. Your task is to create a classifier that can accurately identify and classify each bird based on its unique vocalizations. This exciting assignment will not only enhance your understanding of audio classification but also deepen your appreciation for the diverse world of avian sounds. Get ready to immerse yourself in the melodies of nature as you embark on this fascinating journey of bird sound classification!

## Dataset

The [Birds dataset](https://storage.googleapis.com/laurencemoroney-blog.appspot.com/birds_dataset.zip) is an educational collection of 5 types of bird songs:

- White-breasted Wood-Wren
- House Sparrow
- Red Crossbill
- Chestnut-crowned Antpitta
- Azara's Spinetail

## Example Approach

1. Classify the audio using the [YAMNet](https://github.com/tensorflow/models/tree/master/research/audioset/yamnet) classifier to get the sound for bird/animals.
2. If YAMNet model is fairly confident, with its prediction, then run the second classifier to get the type of bird, that created this sound.

Please use any other approach you see fit.

## Submission

1. Python notebook with the code to train the classifier.
2. Apk for the android classifier app. [No code is needed].
