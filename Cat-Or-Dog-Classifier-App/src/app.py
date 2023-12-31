import streamlit as st
from PIL import Image
import torch
from torchvision import transforms
from model import DogCatClassifier

# Define transformations for image preprocessing
transform = transforms.Compose(
    [
        transforms.Resize((256, 256)),
        transforms.ToTensor(),
        transforms.Normalize((0.5, 0.5, 0.5), (0.5, 0.5, 0.5)),
    ]
)

# Load the trained model
model = DogCatClassifier()
model.load_state_dict(torch.load("model/dog_cat_classifier.pth"))
model.eval()

# Function to make predictions
def predict(image_path):
    image = Image.open(image_path).convert("RGB")
    image = transform(image)
    image = image.unsqueeze(0)  # Add batch dimension

    with torch.no_grad():
        output = model(image)

    predicted_class = output > 0.5
    return predicted_class.item()


# Streamlit app
st.title("Dog and Cat Classifier")
image = Image.open("static/hero.jpg")
st.image(image, caption="Photo by Alvan Nee on Unsplash")
uploaded_file = st.file_uploader("Choose an image...", type="jpg")

if uploaded_file is not None:
    st.image(uploaded_file, caption="Uploaded Image.", use_column_width=True)
    st.write("")
    st.write("Classifying...")

    # Perform prediction
    class_index = predict(uploaded_file)

    # Display the result
    classes = {0: "Dog", 1: "Cat"}
    st.write(f"Prediction: {classes[class_index]}")
