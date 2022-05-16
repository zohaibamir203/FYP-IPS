# Importing Module
import io
from cv2 import COLOR_BAYER_BG2BGR, imread
from flask import Flask, send_file
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
    GroundShop = ['G-01A','G-01','G-02','G-03','G-04','G-05','G-06','G-07','G-08A','G-08','G-09','G-10','G-11','G-11A','G-12','G-13','G-15','G-16',
    'G-17','G-18','G-19','G-20','G-21','G-22','G-23','G-24','G-25','G-26','G-27','G-28','G-29','G-30','G-31','G-33','G-34','G-35','G-36'
    ,'G-37','G-38','G-39','G-40','G-41','G-42','G-43']

    # Ground Floor Shop Pixels Array
    GroundPixels = [[52,354],[82,354],[115,354],[148,354],[188,354],[308,354],[350,354],[386,354],[422,354],[454,354],[429,316],[464,354],[429,266],[429,293],[429,240],[429,202],[429,161],[446,120],
    [429,131],[429,147],[429,164],[429,181],[429,198],[429,218],[380,354],[328,354],[174,354],[122,354],[75,318],[75,293,],[75,264],[75,233],[75,202],[75,162],[55,120],[75,132],[75,147],
    [75,164],[75,183],[75,198],[75,219],[115,282],[167,282],[250,212]]
    
     # First Floor Shop Array
    FirstShop = ['F-01','F-02','F-03','F-04','F-05','F-06','F-07','F-08','F-09','F-10','F-11','F-12','F-13','F-14','F-15','F-16'
    ,'F-18','F-19','F-20','F-21','F-22','F-23','F-24','F-25','F-26','F-27','F-28','F-29','F-30','F-31','F-32','F-33','F-34','F-35','F-36'
    ,'F-37','F-38','F-40','F-41']

    # First Floor Shop Pixel Array
    FirstPixels = [[82,357],[112,357],[148,357],[182,357],[217,357],[248,357],[283,357],[317,357],[352,357],[386,357],[421,357],[421,318],[421,294],[421,267],[421,234],[421,201],
    [421,162],[448,120],[371,187],[284,187],[200,187],[132,187],[421,215],[421,250],[421,282],[421,316],[356,357],[146,357],[82,313],[82,283],[82,252],[82,218],[82,317],[82,293],[82,268],
    [82,230],[82,201],[82,162],[55,120]]

    # Mark the pixels which are passed to this function. 
    def MarkCurrLoc(pixelValue):
            
            for x in range(pixelValue[0],pixelValue[0]+3):
                for y in range(pixelValue[1],pixelValue[1]+3):
                    img[x,y]=0

            for x in range(pixelValue[0]-3,pixelValue[0]):
                for y in range(pixelValue[1],pixelValue[1]+3):
                    img[x,y]=0

            for x in range(pixelValue[0]-3,pixelValue[0]):
                for y in range(pixelValue[1]-3,pixelValue[1]):
                    img[x,y]=0

            for x in range(pixelValue[0],pixelValue[0]+3):
                for y in range(pixelValue[1]-3,pixelValue[1]):
                    img[x,y]=0

    #Checking if passed parameter is of Ground Floor and then marking the current location.
    if (str(ShopNo[0]) == "G"):
        img = cv2.imread('GFloor.png')
        addressIndex = GroundShop.index(str(ShopNo))
        print(addressIndex)
        pixelValue = GroundPixels[addressIndex]
        print(pixelValue)
        swap = pixelValue[0]
        pixelValue[0] = pixelValue [1]
        pixelValue[1] = swap
        MarkCurrLoc(pixelValue)
        cv2.imwrite("Floor-Copy.png", img)
    ##change before
        with open("Floor-Copy.png", 'rb') as bites:
            return send_file(io.BytesIO(bites.read()),attachment_filename='Floor.jpg',
                             mimetype='image/jpg')

    #Checking if passed parameter is of First Floor and then marking the current location.
    if (str(ShopNo[0]) == "F"):
        img = cv2.imread('FFloor.png')
        addressIndex = FirstShop.index(str(ShopNo))
        print(addressIndex)
        pixelValue = FirstPixels[addressIndex]
        print(pixelValue)
        swap = pixelValue[0]
        pixelValue[0] = pixelValue [1]
        pixelValue[1] = swap
        MarkCurrLoc(pixelValue)
        cv2.imwrite("Floor-Copy.png", img)
        with open("Floor-Copy.png", 'rb') as bites:
            return send_file(io.BytesIO(bites.read()),attachment_filename='Floor.jpg',
                             mimetype='image/jpg')

@app.route('/from/<loc>/to/<dest>')
def Path(loc,dest):

    # Ground Floor Shop Array
    GroundShop = ['G-01A','G-01','G-02','G-03','G-04','G-05','G-06','G-07','G-08A','G-08','G-09','G-10','G-11','G-11A','G-12','G-13','G-15','G-16',
    'G-17','G-18','G-19','G-20','G-21','G-22','G-23','G-24','G-25','G-26','G-27','G-28','G-29','G-30','G-31','G-33','G-34','G-35','G-36'
    ,'G-37','G-38','G-39','G-40','G-41','G-42','G-43']

    # Ground Floor Shop Pixels Array
    GroundPixels = [[52,354],[82,354],[115,354],[148,354],[188,354],[308,354],[350,354],[386,354],[422,354],[454,354],[429,316],[464,354],[429,266],[429,293],[429,240],[429,202],[429,161],[446,120],
    [429,131],[429,147],[429,164],[429,181],[429,198],[429,218],[380,354],[328,354],[174,354],[122,354],[75,318],[75,293,],[75,264],[75,233],[75,202],[75,162],[55,120],[75,132],[75,147],
    [75,164],[75,183],[75,198],[75,219],[115,282],[167,282],[250,212]]
    
     # First Floor Shop Array
    FirstShop = ['F-01','F-02','F-03','F-04','F-05','F-06','F-07','F-08','F-09','F-10','F-11','F-12','F-13','F-14','F-15','F-16'
    ,'F-18','F-19','F-20','F-21','F-22','F-23','F-24','F-25','F-26','F-27','F-28','F-29','F-30','F-31','F-32','F-33','F-34','F-35','F-36'
    ,'F-37','F-38','F-40','F-41']

    # First Floor Shop Pixel Array
    FirstPixels = [[82,357],[112,357],[148,357],[182,357],[217,357],[248,357],[283,357],[317,357],[352,357],[386,357],[421,357],[421,318],[421,294],[421,267],[421,234],[421,201],
    [421,162],[448,120],[371,187],[284,187],[200,187],[132,187],[421,215],[421,250],[421,282],[421,316],[356,357],[146,357],[82,313],[82,283],[82,252],[82,218],[82,317],[82,293],[82,268],
    [82,230],[82,201],[82,162],[55,120]]

    def MarkCurrLoc(pixelValue):
            
            for x in range(pixelValue[0],pixelValue[0]+3):
                for y in range(pixelValue[1],pixelValue[1]+3):
                    img[x,y]=0

            for x in range(pixelValue[0]-3,pixelValue[0]):
                for y in range(pixelValue[1],pixelValue[1]+3):
                    img[x,y]=0

            for x in range(pixelValue[0]-3,pixelValue[0]):
                for y in range(pixelValue[1]-3,pixelValue[1]):
                    img[x,y]=0

            for x in range(pixelValue[0],pixelValue[0]+3):
                for y in range(pixelValue[1]-3,pixelValue[1]):
                    img[x,y]=0

    if (str(loc[0]) == "G") and (str(dest)[0] == "G"):
        img = cv2.imread("GFloor.png")
        LocIndex = GroundShop.index(str(loc))
        DesIndex = GroundShop.index(str(dest))
        LocPixel = GroundPixels[LocIndex]
        DesPixel = GroundPixels[DesIndex]
        swap = LocPixel[0]
        LocPixel[0] = LocPixel [1]
        LocPixel[1] = swap
        swap1 = DesPixel[0]
        DesPixel[0] = DesPixel [1]
        DesPixel[1] = swap1
        MarkCurrLoc(LocPixel)
        MarkCurrLoc(DesPixel)
        cv2.imwrite("Floor-Copy.png",img)
        with open("Floor-Copy.png","rb") as bites:
            return send_file(io.BytesIO(bites.read()),attachment_filename = "Floor.jpg",
            mimetype='image/jpg')

    if (str(loc[0]) == "F") and (str(dest)[0] == "F"):
        img = cv2.imread("FFloor.png")
        LocIndex = FirstShop.index(str(loc))
        DesIndex = FirstShop.index(str(dest))
        LocPixel = FirstPixels[LocIndex]
        DesPixel = FirstPixels[DesIndex]
        swap = LocPixel[0]
        LocPixel[0] = LocPixel [1]
        LocPixel[1] = swap
        swap1 = DesPixel[0]
        DesPixel[0] = DesPixel [1]
        DesPixel[1] = swap1
        MarkCurrLoc(LocPixel)
        MarkCurrLoc(DesPixel)
        cv2.imwrite("Floor-Copy.png",img)
        with open("Floor-Copy.png","rb") as bites:
            return send_file(io.BytesIO(bites.read()),attachment_filename = "Floor.jpg",
            mimetype='image/jpg')


app.run(host='0.0.0.0',port=3000)