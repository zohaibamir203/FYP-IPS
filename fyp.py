# Importing Module
from flask import Flask
import cv2
# Creating App
app = Flask(__name__)
# Testing if Flask is working or not
@app.route('/')
def test():
    return "App is running on Server using Flask."
# Setting a route with Parameter
@app.route('/<ShopNo>')
def data(ShopNo):
    # Ground Floor Shop Array
    GroundShop = ['G-01A','G-01','G-02','G-03','G-04','G-05','G-06','G-07','G-08','G-09','G-10','G-11','G-11A','G-12','G-13','G-14','G-15','G-16'
    ,'G-17','G-18','G-19','G-20','G-21','G-22','G-23','G-24','G-25','G-26','G-27','G-28','G-29','G-30','G-31','G-32','G-33','G-34','G-35','G-36'
    ,'G-37','G-38','G-39','G-40','G-41','G-42','G-43']
    # Ground Floor Shop Pixels Array
    GroundPixels = []
    # First Floor Shop Array
    FirstShop = ['F-01','F-02','F-03','F-04','F-05','F-06','F-07','F-08','F-09','F-10','F-11','F-12','F-13','F-14','F-15','F-16'
    ,'F-17','F-18','F-19','F-20','F-21','F-22','F-23','F-24','F-25','F-26','F-27','F-28','F-29','F-30','F-31','F-32','F-33','F-34','F-35','F-36'
    ,'F-37','F-38','F-39','F-40','F-41','F-42']
    #Checking if passed parameter is of Ground Floor
    if (ShopNo[0] == "G"):
        img = cv2.imread('ground.png')
        height, width= img.shape[:2]
        print ("Height "+str(height)+" Width "+str(width))
        return "This is Ground Floor"
    #Checking if passed parameter is of First Floor
    if (ShopNo[0] == "1"):
        return "This is First Floor"

    
app.run(host='0.0.0.0',port=3000)
