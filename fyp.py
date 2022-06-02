# Importing Module
import io
from turtle import pos
from cv2 import COLOR_BAYER_BG2BGR, imread
from flask import Flask, send_file
import cv2
# Creating App
app = Flask(__name__)
# Testing if Flask is working or not
@app.route('/')
def test():
    return "App is running on Server using Flask."
# Only Mark a Point on ShopNo we passed as a Parameter
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
        swap = pixelValue[0]
        pixelValue[0] = pixelValue [1]
        pixelValue[1] = swap
        MarkCurrLoc(pixelValue)
        print("Pixel Value: " + str(pixelValue))
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
        print("Pixel Value: " + str(pixelValue))
        cv2.imwrite("Floor-Copy.png", img)
        with open("Floor-Copy.png", 'rb') as bites:
            return send_file(io.BytesIO(bites.read()),attachment_filename='Floor.jpg',
                             mimetype='image/jpg')
# Show Route From Location to Destination on same Floor
@app.route('/from/<loc>/to/<dest>')
def Path(loc,dest):

    # Array in which all pixels of path will be stored
    path =[]

    # Ground Floor Shop Array
    GroundFShop = ['G-34','G-35','G-36','G-33','G-37','G-38','Lift','G-39','G-31','G-40','G-30','G-40A','G-29','G-28','G-27','G-01','G-02','G-26',
    'G-03','G-25','G-04','G-05','G-24','G-06','G-23','G-07','G-08','G-09','G-11A','G-11','G-22A','G-12','G-22','G-13','G-21','G-14',
    'G-20','G-19','G-15','G-18','G-17','G-16']
    GroundSShop = ['G-41','G-42','EMPTY','EMPTY','G-43','EMPTY','EMPTY','G-05','G-24','G-06','G-23','G-07','G-08','G-09','G-11A','G-11',
    'G-22A','G-12','G-22','G-13','G-21','G-14','G-20','G-19','G-15','G-18','G-17','G-16']
    GroundTShop = ['G-04','G-25','G-03','G-26','G-02','G-01','G-27','G-28','EMPTY','G-41','G-42','EMPTY','EMPTY','G-43']
    GroundRShop = ['G-34','G-35','G-36','G-33','G-37','G-38','Lift','G-39','G-31','G-40','G-30','G-40A','G-29','EMPTY','G-41','G-42',
    'EMPTY','EMPTY','G-43']
    # Ground Floor Shop Pixels Array
    GroundFPixels = [[110,75],[132,75],[147,75],[162,75],[164,75],[183,75],[188,75],[198,75],[202,75],[219,75],[233,75],[251,75],[264,75],[293,75],[318,75],[354,75],[354,115],[354,122],
    [354,148],[354,174],[354,188],[354,308],[354,328],[354,350],[354,380],[354,386],[354,429],[316,429],[293,429],[266,429],[251,429],[240,429],[218,429],[202,429],[198,429],[189,429],
    [181,429],[164,429],[161,429],[147,429],[131,429],[110,429]]
    GroundSPixels = [[282,115],[282,167],[282,212],[212,212],[212,250],[212,291],[354,291],[354,308],[354,328],[354,350],[354,380],[354,386],[354,429],[316,429],[293,429],[266,429],
    [251,429],[240,429],[218,429],[202,429],[198,429],[189,429],[181,429],[164,429],[161,429],[147,429],[131,429],[110,429]]
    GroundTPixels = [[354,188],[354,174],[354,148],[354,122],[354,115],[354,75],[318,75],[293,75],[282,75],[282,115],[282,167],[282,212],[212,212],[212,250]]
    GroundRPixels = [[110,75],[132,75],[147,75],[162,75],[164,75],[183,75],[188,75],[198,75],[202,75],[219,75],[233,75],[251,75],[264,75],[282,75],[282,115],[282,167],
    [282,212],[212,212],[212,250]]
     # First Floor Shop Array
    FirstFShop = ['F-41','F-40','Lift','F-38','F-33','F-37','F-32','F-36','F-31','F-35','F-30','F-34','F-01',
    'F-02','F-03','F-29','F-04','F-05','F-06','F-07','F-08','F-09','F-28','F-10','F-11','F-12','F-27',
    'F-13','F-26','F-14','F-25','F-15','F-24','F-16','F-17','F-18','F-19']
    FirstSShop = ['F-41','F-40','Lift','EMPTY','F-23','F-22','F-21','F-20','F-17','F-18','F-19']
    FirstTShop = ['F-20','F-21','F-22','F-23','EMPTY','F-38','F-33','F-37','F-32','F-36','F-31','F-35','F-30','F-34','F-01',
    'F-02','F-03','F-29','F-04','F-05','F-06','F-07','F-08','F-09','F-28','F-10','F-11','F-12','F-27','F-13','F-26','F-14',
    'F-25','F-15','F-24','F-16','F-17','F-20']
    FirstRShop = ['F-41','F-40','Lift','EMPTY','F-23','F-22','F-21','F-20','F-17','F-16','F-24','F-15','F-23','F-14','F-26',
    'F-13','F-27','F-12','F-11']
    FirstHShop = ['F-19','F-18','F-17','F-20','F-21','F-22','F-23','Lift','F-38','F-33','F-37','F-32','F-36','F-31','F-35',
    'F-30','F-34','F-01']
    # First Floor Shop Pixel Array
    FirstFPixels = [[110,82],[162,82],[182,82],[201,82],[218,82],[230,82],[252,82],[268,82],[283,82],[293,82],[313,82],[317,82],[357,82],
    [357,112],[357,146],[357,148],[357,182],[357,217],[357,248],[357,283],[357,317],[357,312],[357,356],[357,386],[357,421],[318,421],[316,421],
    [294,421],[282,421],[267,421],[250,421],[234,421],[215,421],[201,421],[185,421],[162,421],[110,421]]
    FirstSPixels = [[110,82],[162,82],[182,82],[186,82],[186,132],[186,200],[186,284],[186,372],[186,421],[162,421],[110,421]]
    FirstTPixels = [[186,372],[186,284],[186,200],[186,132],[186,82],[201,82],[218,82],[230,82],[252,82],[268,82],[283,82],[293,82],[313,82],[317,82],[357,82],
    [357,112],[357,146],[357,148],[357,182],[357,217],[357,248],[357,283],[357,317],[357,312],[357,356],[357,386],[357,421],[318,421],[316,421],
    [294,421],[282,421],[267,421],[250,421],[234,421],[215,421],[201,421],[185,421],[186,372]]
    FirstRPixels = [[110,82],[162,82],[182,82],[186,82],[186,132],[186,200],[186,284],[186,372],[186,421],[201,421],[215,421],[234,421],[251,421],[267,421],[282,421],
    [293,421],[315,421],[318,421],[357,421]]
    FirstHPixels = [[110,421],[162,421],[186,421],[186,372],[186,284],[186,200],[186,132],[186,82],[201,82],[218,82],[230,82],[252,82],[268,82],[283,82],[293,82]
    ,[313,82],[317,82],[357,82]]
    # Mark the Location and Destination Shop
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

    # Create Path from Pixels Stored in Path Array
    def MarkLine(point1,point2):
        if (point1[0]==point2[0]):
            for y in range(point2[1],point1[1]+3):
                for x in range(point1[0],point1[0]+3):
                    img[x,y]=0
                    
            if (point1[1]<point2[1]):
                for y in range(point1[1],point2[1]+3):
                    for x in range(point1[0],point1[0]+3):
                        img[x,y]=0
        
    
        if (point1[1]==point2[1]):
            if (point1[0]<point2[0]):
                for x in range(point1[0],point2[0]):
                    for y in range(point1[1],point1[1]+3):
                        img[x,y]=0

        if (point1[0]>point2[0]):
            for x in range(point2[0],point1[0]):
                for y in range(point1[1],point1[1]+3):
                    img[x,y]=0

    # Store Path Pixels in Array
    def CreatePath(point1,point2,RoomPixel):
        if (point1<point2):
            for i in range(point1,point2+1):
                path.append(RoomPixel[i])

        if (point1>point2):
            for i in range(point2,point1+1):
                path.append(RoomPixel[i])
            
        for i in range(len(path)):
            if(i!=len(path)-1):
                MarkLine(path[i],path[i+1])


    if (str(loc[0]) == "G") and (str(dest)[0] == "G"):
        img = cv2.imread("GFloor.png")
        if((str(loc) in GroundFShop) and  (str(dest) in GroundFShop)):
            LocIndex = GroundFShop.index(str(loc))
            DesIndex = GroundFShop.index(str(dest))
            LocPixel = GroundFPixels[LocIndex]
            DesPixel = GroundFPixels[DesIndex]
            MarkCurrLoc(LocPixel)
            MarkCurrLoc(DesPixel)
            CreatePath(LocIndex,DesIndex,GroundFPixels)
        elif((str(loc) in GroundSShop) and (str(dest) in GroundSShop)):
            LocIndex = GroundSShop.index(str(loc))
            DesIndex = GroundSShop.index(str(dest))
            LocPixel = GroundSPixels[LocIndex]
            DesPixel = GroundSPixels[DesIndex]
            MarkCurrLoc(LocPixel)
            MarkCurrLoc(DesPixel)
            CreatePath(LocIndex,DesIndex,GroundSPixels)
        elif((str(loc)in GroundTShop) and (str(dest) in GroundTShop)):
            LocIndex = GroundTShop.index(str(loc))
            DesIndex = GroundTShop.index(str(dest))
            LocPixel = GroundTPixels[LocIndex]
            DesPixel = GroundTPixels[DesIndex]
            MarkCurrLoc(LocPixel)
            MarkCurrLoc(DesPixel)
            CreatePath(LocIndex,DesIndex,GroundTPixels)  
        elif((str(loc) in GroundRShop) and (str(dest) in GroundRShop)):
            LocIndex = GroundRShop.index(str(loc))
            DesIndex = GroundRShop.index(str(dest))
            LocPixel = GroundRPixels[LocIndex]
            DesPixel = GroundRPixels[DesIndex]
            MarkCurrLoc(LocPixel)
            MarkCurrLoc(DesPixel)
            CreatePath(LocIndex,DesIndex,GroundRPixels)  
        else:
            print("Address Not in Any Array")
        cv2.imwrite("Floor-Copy.png",img)
        with open("Floor-Copy.png","rb") as bites:
            return send_file(io.BytesIO(bites.read()),attachment_filename = "Floor.jpg",
            mimetype='image/jpg')

    if (str(loc[0]) == "F") and (str(dest)[0] == "F"):
        img = cv2.imread("FFloor.png")
        if ((str(loc) in FirstSShop) and (str(dest) in FirstSShop)):
            LocIndex = FirstSShop.index(str(loc))
            DesIndex = FirstSShop.index(str(dest))
            LocPixel = FirstSPixels[LocIndex]
            DesPixel = FirstSPixels[DesIndex]
            MarkCurrLoc(LocPixel)
            MarkCurrLoc(DesPixel)
            CreatePath(LocIndex,DesIndex,FirstSPixels)
        elif ((str(loc) in FirstRShop) and (str(dest) in FirstRShop)):
            LocIndex = FirstRShop.index(str(loc))
            DesIndex = FirstRShop.index(str(dest))
            LocPixel = FirstRPixels[LocIndex]
            DesPixel = FirstRPixels[DesIndex]
            MarkCurrLoc(LocPixel)
            MarkCurrLoc(DesPixel)
            CreatePath(LocIndex,DesIndex,FirstRPixels)
        elif ((str(loc) in FirstHShop) and (str(dest) in FirstHShop)):
            LocIndex = FirstHShop.index(str(loc))
            DesIndex = FirstHShop.index(str(dest))
            LocPixel = FirstHPixels[LocIndex]
            DesPixel = FirstHPixels[DesIndex]
            MarkCurrLoc(LocPixel)
            MarkCurrLoc(DesPixel)
            CreatePath(LocIndex,DesIndex,FirstHPixels)
        elif ((str(loc) in FirstTShop) and (str(dest) in FirstTShop)):
            LocIndex = FirstTShop.index(str(loc))
            DesIndex = FirstTShop.index(str(dest))
            LocPixel = FirstTPixels[LocIndex]
            DesPixel = FirstTPixels[DesIndex]
            MarkCurrLoc(LocPixel)
            MarkCurrLoc(DesPixel)
            CreatePath(LocIndex,DesIndex,FirstTPixels)
        elif ((str(loc) in FirstFShop) and (str(dest) in FirstFShop)):
            LocIndex = FirstFShop.index(str(loc))
            DesIndex = FirstFShop.index(str(dest))
            LocPixel = FirstFPixels[LocIndex]
            DesPixel = FirstFPixels[DesIndex]
            MarkCurrLoc(LocPixel)
            MarkCurrLoc(DesPixel)
            CreatePath(LocIndex,DesIndex,FirstFPixels)
        cv2.imwrite("Floor-Copy.png",img)
        with open("Floor-Copy.png","rb") as bites:
            return send_file(io.BytesIO(bites.read()),attachment_filename = "Floor.jpg",
            mimetype='image/jpg')
# Show Route From Location to Destination on Different Floor
@app.route('/from/<loc>/to/<dest>/lift')
def lift(loc,dest):

    # Array in which all pixels of path will be stored
    path =[]

    # Ground Floor Shop Array
    GroundFShop = ['G-34','G-35','G-36','G-33','G-37','G-38','Lift','G-39','G-31','G-40','G-30','G-40A','G-29','G-28','G-27','G-01','G-02','G-26',
    'G-03','G-25','G-04','G-05','G-24','G-06','G-23','G-07','G-08','G-09','G-11A','G-11','G-22A','G-12','G-22','G-13','G-21','G-14',
    'G-20','G-19','G-15','G-18','G-17','G-16']
    GroundSShop = ['G-41','G-42','EMPTY','EMPTY','G-43','EMPTY','EMPTY','G-05','G-24','G-06','G-23','G-07','G-08','G-09','G-11A','G-11',
    'G-22A','G-12','G-22','G-13','G-21','G-14','G-20','G-19','G-15','G-18','G-17','G-16']
    GroundTShop = ['G-04','G-25','G-03','G-26','G-02','G-01','G-27','G-28','EMPTY','G-41','G-42','EMPTY','EMPTY','G-43']
    GroundRShop = ['G-34','G-35','G-36','G-33','G-37','G-38','Lift','G-39','G-31','G-40','G-30','G-40A','G-29','EMPTY','G-41','G-42',
    'EMPTY','EMPTY','G-43']
    # Ground Floor Shop Pixels Array
    GroundFPixels = [[110,75],[132,75],[147,75],[162,75],[164,75],[183,75],[188,75],[198,75],[202,75],[219,75],[233,75],[251,75],[264,75],[293,75],[318,75],[354,75],[354,115],[354,122],
    [354,148],[354,174],[354,188],[354,308],[354,328],[354,350],[354,380],[354,386],[354,429],[316,429],[293,429],[266,429],[251,429],[240,429],[218,429],[202,429],[198,429],[189,429],
    [181,429],[164,429],[161,429],[147,429],[131,429],[110,429]]
    GroundSPixels = [[282,115],[282,167],[282,212],[212,212],[212,250],[212,291],[354,291],[354,308],[354,328],[354,350],[354,380],[354,386],[354,429],[316,429],[293,429],[266,429],
    [251,429],[240,429],[218,429],[202,429],[198,429],[189,429],[181,429],[164,429],[161,429],[147,429],[131,429],[110,429]]
    GroundTPixels = [[354,188],[354,174],[354,148],[354,122],[354,115],[354,75],[318,75],[293,75],[282,75],[282,115],[282,167],[282,212],[212,212],[212,250]]
    GroundRPixels = [[110,75],[132,75],[147,75],[162,75],[164,75],[183,75],[188,75],[198,75],[202,75],[219,75],[233,75],[251,75],[264,75],[282,75],[282,115],[282,167],
    [282,212],[212,212],[212,250]]
     # First Floor Shop Array
    FirstFShop = ['F-41','F-40','Lift','F-38','F-33','F-37','F-32','F-36','F-31','F-35','F-30','F-34','F-01',
    'F-02','F-03','F-29','F-04','F-05','F-06','F-07','F-08','F-09','F-28','F-10','F-11','F-12','F-27',
    'F-13','F-26','F-14','F-25','F-15','F-24','F-16','F-17','F-18','F-19']
    FirstSShop = ['F-41','F-40','Lift','EMPTY','F-23','F-22','F-21','F-20','F-17','F-18','F-19']
    FirstTShop = ['F-20','F-21','F-22','F-23','EMPTY','F-38','F-33','F-37','F-32','F-36','F-31','F-35','F-30','F-34','F-01',
    'F-02','F-03','F-29','F-04','F-05','F-06','F-07','F-08','F-09','F-28','F-10','F-11','F-12','F-27','F-13','F-26','F-14',
    'F-25','F-15','F-24','F-16','F-17','F-20']
    FirstRShop = ['F-41','F-40','Lift','EMPTY','F-23','F-22','F-21','F-20','F-17','F-16','F-24','F-15','F-23','F-14','F-26',
    'F-13','F-27','F-12','F-11']
    FirstHShop = ['F-19','F-18','F-17','F-20','F-21','F-22','F-23','Lift','F-38','F-33','F-37','F-32','F-36','F-31','F-35',
    'F-30','F-34','F-01']
    # First Floor Shop Pixel Array
    FirstFPixels = [[110,82],[162,82],[182,82],[201,82],[218,82],[230,82],[252,82],[268,82],[283,82],[293,82],[313,82],[317,82],[357,82],
    [357,112],[357,146],[357,148],[357,182],[357,217],[357,248],[357,283],[357,317],[357,312],[357,356],[357,386],[357,421],[318,421],[316,421],
    [294,421],[282,421],[267,421],[250,421],[234,421],[215,421],[201,421],[185,421],[162,421],[110,421]]
    FirstSPixels = [[110,82],[162,82],[182,82],[186,82],[186,132],[186,200],[186,284],[186,372],[186,421],[162,421],[110,421]]
    FirstTPixels = [[186,372],[186,284],[186,200],[186,132],[186,82],[201,82],[218,82],[230,82],[252,82],[268,82],[283,82],[293,82],[313,82],[317,82],[357,82],
    [357,112],[357,146],[357,148],[357,182],[357,217],[357,248],[357,283],[357,317],[357,312],[357,356],[357,386],[357,421],[318,421],[316,421],
    [294,421],[282,421],[267,421],[250,421],[234,421],[215,421],[201,421],[185,421],[186,372]]
    FirstRPixels = [[110,82],[162,82],[182,82],[186,82],[186,132],[186,200],[186,284],[186,372],[186,421],[201,421],[215,421],[234,421],[251,421],[267,421],[282,421],
    [293,421],[315,421],[318,421],[357,421]]
    FirstHPixels = [[110,421],[162,421],[186,421],[186,372],[186,284],[186,200],[186,132],[186,82],[201,82],[218,82],[230,82],[252,82],[268,82],[283,82],[293,82]
    ,[313,82],[317,82],[357,82]]
    # Mark the Location and Destination Shop
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

    # Create Path from Pixels Stored in Path Array
    def MarkLine(point1,point2):
        if (point1[0]==point2[0]):
            for y in range(point2[1],point1[1]+3):
                for x in range(point1[0],point1[0]+3):
                    img[x,y]=0
                    
            if (point1[1]<point2[1]):
                for y in range(point1[1],point2[1]+3):
                    for x in range(point1[0],point1[0]+3):
                        img[x,y]=0
        
    
        if (point1[1]==point2[1]):
            if (point1[0]<point2[0]):
                for x in range(point1[0],point2[0]):
                    for y in range(point1[1],point1[1]+3):
                        img[x,y]=0

        if (point1[0]>point2[0]):
            for x in range(point2[0],point1[0]):
                for y in range(point1[1],point1[1]+3):
                    img[x,y]=0

    # Store Path Pixels in Array
    def CreatePath(point1,point2,RoomPixel):
        if (point1<point2):
            for i in range(point1,point2+1):
                path.append(RoomPixel[i])

        if (point1>point2):
            for i in range(point2,point1+1):
                path.append(RoomPixel[i])
            
        for i in range(len(path)):
            if(i!=len(path)-1):
                MarkLine(path[i],path[i+1])
    
    if (str(loc)[0] == 'G') or (str(dest)[0] == 'G'):
        img = cv2.imread("GFloor.png")
        if (str(loc)[0] == 'G'):
            if (str(loc) in GroundFShop):
                StartPos = GroundFShop.index(str(loc))
                EndPos = GroundFShop.index('Lift')
                ShopPixel = GroundFPixels[StartPos]
                LiftPixel = GroundFPixels[EndPos]
                MarkCurrLoc(ShopPixel)
                MarkCurrLoc(LiftPixel)
                CreatePath (StartPos,EndPos,GroundFPixels)
            else:
                StartPos = GroundRShop.index(str(loc))
                EndPos = GroundRShop.index('Lift')
                ShopPixel = GroundRPixels[StartPos]
                LiftPixel = GroundRPixels[EndPos] 
                MarkCurrLoc(ShopPixel)
                MarkCurrLoc(LiftPixel)
                CreatePath (StartPos,EndPos,GroundRPixels)   
        elif(str(dest)[0] == 'G'):
            if (str(dest) in GroundFShop):
                StartPos = GroundFShop.index(str(dest))
                EndPos = GroundFShop.index('Lift')
                ShopPixel = GroundFPixels[StartPos]
                LiftPixel = GroundFPixels[EndPos]
                MarkCurrLoc(ShopPixel)
                MarkCurrLoc(LiftPixel)
                CreatePath (StartPos,EndPos,GroundFPixels)
            else:
                StartPos = GroundRShop.index(str(dest))
                EndPos = GroundRShop.index('Lift')
                ShopPixel = GroundRPixels[StartPos]
                LiftPixel = GroundRPixels[EndPos]
                MarkCurrLoc(ShopPixel)
                MarkCurrLoc(LiftPixel)
                CreatePath (StartPos,EndPos,GroundRPixels)
        cv2.imwrite("GroundFloorLift.png",img)
        path = []

    if (str(loc)[0] == 'F') or (str(dest)[0] == 'F'):
        img = cv2.imread("FFloor.png")
        if (str(loc)[0] == 'F'):
            if (str(loc) in FirstFShop):
                StartPos = FirstFShop.index(str(loc))
                EndPos = FirstFShop.index('Lift')
                ShopPixel = FirstFPixels[StartPos]
                LiftPixel = FirstFPixels[EndPos]
                MarkCurrLoc(ShopPixel)
                MarkCurrLoc(LiftPixel)
                CreatePath (StartPos,EndPos,FirstFPixels)
            elif(str(loc) in FirstRShop):
                StartPos = FirstRShop.index(str(loc))
                EndPos = FirstRShop.index('Lift')
                ShopPixel = FirstRPixels[StartPos]
                LiftPixel = FirstRPixels[EndPos]
                MarkCurrLoc(ShopPixel)
                MarkCurrLoc(LiftPixel)
                CreatePath (StartPos,EndPos,FirstRPixels)
            elif(str(loc) in FirstHShop):
                StartPos = FirstHShop.index(str(loc))
                EndPos = FirstHShop.index('Lift')
                ShopPixel = FirstHPixels[StartPos]
                LiftPixel = FirstHPixels[EndPos]
                MarkCurrLoc(ShopPixel)
                MarkCurrLoc(LiftPixel)
                CreatePath (StartPos,EndPos,FirstHPixels)   
            else:
                StartPos = FirstSShop.index(str(loc))
                EndPos = FirstSShop.index('Lift')
                ShopPixel = FirstSPixels[StartPos]
                LiftPixel = FirstSPixels[EndPos] 
                MarkCurrLoc(ShopPixel)
                MarkCurrLoc(LiftPixel)
                CreatePath (StartPos,EndPos,FirstSPixels)   
        elif(str(dest)[0] == 'F'):
            if (str(dest) in FirstSShop):
                StartPos = FirstSShop.index(str(dest))
                EndPos = FirstSShop.index('Lift')
                ShopPixel = FirstSPixels[StartPos]
                LiftPixel = FirstSPixels[EndPos]
                MarkCurrLoc(ShopPixel)
                MarkCurrLoc(LiftPixel)
                CreatePath (StartPos,EndPos,FirstSPixels)
            elif(str(dest) in FirstRShop):
                StartPos = FirstRShop.index(str(dest))
                EndPos = FirstRShop.index('Lift')
                ShopPixel = FirstRPixels[StartPos]
                LiftPixel = FirstRPixels[EndPos]
                MarkCurrLoc(ShopPixel)
                MarkCurrLoc(LiftPixel)
                CreatePath (StartPos,EndPos,FirstRPixels)
            elif(str(dest) in FirstHShop):
                StartPos = FirstHShop.index(str(dest))
                EndPos = FirstHShop.index('Lift')
                ShopPixel = FirstHPixels[StartPos]
                LiftPixel = FirstHPixels[EndPos]
                MarkCurrLoc(ShopPixel)
                MarkCurrLoc(LiftPixel)
                CreatePath (StartPos,EndPos,FirstHPixels) 
            else:
                StartPos = FirstFShop.index(str(dest))
                EndPos = FirstFShop.index('Lift')
                ShopPixel = FirstFPixels[StartPos]
                LiftPixel = FirstFPixels[EndPos]
                MarkCurrLoc(ShopPixel)
                MarkCurrLoc(LiftPixel)
                CreatePath (StartPos,EndPos,FirstFPixels)
        cv2.imwrite("FirstFloorLift.png",img)
        path = [] 

    if (str(loc)[0] == 'G'):
        finalImage = cv2.imread('MoveToFirstFloor.png')
        img = cv2.imread("GroundFloorLift.png")
        for y in range(500):
            for x in range(25,525):
                finalImage[x,y+50] = img[x-25,y]
        cv2.imwrite('MoveToFirstFloorCopy.png',finalImage)
        img = cv2.imread('FirstFloorLift.png')
        for y in range(500):
            for x in range(700,1200):
                finalImage[x,y+50] = img[x-700,y]
        cv2.imwrite('MoveToFirstFloorCopy.png',finalImage)  
        with open("MoveToFirstFloorCopy.png", 'rb') as bites:
            return send_file(
                         io.BytesIO(bites.read()),
                         attachment_filename='Floor.jpg',
                         mimetype='image/jpg')   
    
    if (str(loc)[0] == 'F'):
        finalImage = cv2.imread('MoveToGroundFloor.png')
        img = cv2.imread("FirstFloorLift.png")
        for y in range(500):
            for x in range(25,525):
                finalImage[x,y+50] = img[x-25,y]
        cv2.imwrite('MoveToGroundFloorCopy.png',finalImage)
        img = cv2.imread('GroundFloorLift.png')
        for y in range(500):
            for x in range(700,1200):
                finalImage[x,y+50] = img[x-700,y]
        cv2.imwrite('MoveToGroundFloorCopy.png',finalImage)  
        with open("MoveToGroundFloorCopy.png", 'rb') as bites:
            return send_file(
                         io.BytesIO(bites.read()),
                         attachment_filename='Floor.jpg',
                         mimetype='image/jpg') 
app.run(host='0.0.0.0',port=3000)