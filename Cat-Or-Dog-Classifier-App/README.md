# Cat-Or-Dog-Classifier-Streamlit

<<<<<<< HEAD
> Fork of ![Cat-Or-Dog-Recognizer-Web-App-DL-streamlit](https://github.com/surdebmalya/Cat-Or-Dog-Recognizer-Web-App-DL-streamlit/tree/master)
=======
> Fork of [Cat-Or-Dog-Recognizer-Web-App-DL-streamlit](https://github.com/surdebmalya/Cat-Or-Dog-Recognizer-Web-App-DL-streamlit/tree/master)
>>>>>>> b9abcaf (feat: Added Assignment1 & Tutorial)

# Introduction

It is a web application where one user can test their images to predict whether
the image is a dog or a cat! The model has the power to predict image of Cats
and Dogs only, so you are requested to give image of a Cat Or a Dog, unless
useless prediction can be done!!!

# Usage

- Install the requirements
  ```sh
  pip install -r requirements.txt
  ```
- Download the training data. Follow instructions in [Readme](./input/Readme.md)
- Train the model.
  ```sh
  python3 src/model.py
  ```
- run the app
  ```sh
  streamlit run src/app.py
  ```
- Enjoy!!
  ![Demo](./static/demo.png)

# Behind The Scene

Beside the scene there is a CNN model, it's pretty straight forward model, as my aim was to build a full stack, so I was not bother about the accuracy, though the rough model already gives aroung **65%** accuracy on the validation data, which was made by 30% splitting of training data!

Checkout model architecture in [model.py](./src/model.py)
