import io
import cv2
from flask import Flask,render_template,request, send_file,Response
import jsonpickle
import numpy as np
import cv2
import json
import requests
import base64
    

app = Flask(__name__)


@app.route('/api/test', methods=['POST'])
def test():
    r = request
    form_data = r.form
    Location = r.form['t1']
    Dest = r.form['t2']
    img = r.form['upload']
    #print(img)
    # convert string of image data to uint8
    #location= data.t1
    #destination= data.t2
    #loc=str(location)
    #des=str(destination)
    #nparr = np.frombuffer(img, np.uint8)
    img=base64.b64decode(img)
    # decode image
    with open("ResultRoomNoCheck.jpg",'wb') as f:
        f.write(img)
    #img = cv2.imdecode(img, cv2.IMREAD_COLOR)
    
    #img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    #img = cv2.resize(img, (780, 450))
    #height, width= img.shape
    #cv2.imwrite("ResultRoomNoCheck.jpg", img)


    def ocr_space_file(filename, overlay=False, api_key='32709bb24288957', language='eng',OCREngine=2):
        payload = {'isOverlayRequired': overlay,'apikey': api_key,'language': language,'OCREngine': 2}
        with open(filename, 'rb') as f:
            r = requests.post('https://api.ocr.space/parse/image',files={filename: f},data=payload)
            return r.content.decode()

    
    
    test_file = ocr_space_file(filename='ResultRoomNoCheck.jpg', language='eng')

    y = json.loads(test_file)

    
    for doc in y['ParsedResults']:
        x= doc['ParsedText']

    
    response = {x}
    x="S-"+x
    #x=x
    #print('Location:',x)
    # encode response using jsonpickle
    response_pickled = jsonpickle.encode(response)

    #return Response(response=response_pickled, status=200, mimetype="application/json")
    return(x)
    


@app.route('/<RoomNo>')
def data(RoomNo):

    
    Rooms=['S-401', 'S-425', 'S-424', 'S-423', 'S-416', 'S-415', 'S-413', 'S-4th-Floor-Toilets', 'S-40',
           'S-403','S-402','S-404']
    RoomsPixels=[[126,391],[235,391],[348,391],[391,368],[391,288],[391,217],
                 [391,137],[376,113],[319,113],[263,113],[205,113],[152,113]]
    Floor2Rooms=['Building Manager','Staris',  'S-219',   'S-218',   'S-217',  'S-216',   'S-215',  'S-214',    'S-213', 'S-212',  'S-211',   'S-210',  'S-2nd-Floor-Toilets', 'S-207',  'S-205', 'S-204',  'S-206',  'S-203',   'S-201',  'S-202',    'Lift',    'LiftStairs']
    PixelsFloor2=[[286,87],[176,87] ,[176,133],[176,361], [241,418],  [241,418]  ,[241,418],[241,418],[318,418],[318,418],[318,418], [393,433],[393,433],[393,347],[393,347],[393,324],[393,324],[393,208],[393,184], [393,134], [393,134], [393,87]]
    def CurrLoc(point):
        for x in range(point[0],point[0]+8):
            for y in range(point[1],point[1]+8):
                img[x,y]=0
            
        for x in range(point[0]-8,point[0]):
            for y in range(point[1],point[1]+8):
                img[x,y]=0

        for x in range(point[0]-8,point[0]):
            for y in range(point[1]-8,point[1]):
                img[x,y]=0

        for x in range(point[0],point[0]+8):
            for y in range(point[1]-8,point[1]):
                img[x,y]=0
    
    #pos=Rooms.index(str(RoomNo))
    #MarkingPixel=RoomsPixels[pos]
    #CurrLoc(MarkingPixel)
    if ((str(RoomNo)[2])=="2"):
        img = cv2.imread("Floor2.png")
        img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        height, width= img.shape
        pos=Floor2Rooms.index(str(RoomNo))
        MarkingPixel=PixelsFloor2[pos]
        CurrLoc(MarkingPixel)
    ##change before
        cv2.imwrite("Floor2-Copy.png", img)
    ##change before
        with open("Floor2-Copy.png", 'rb') as bites:
            return send_file(io.BytesIO(bites.read()),attachment_filename='Floor3.jpg',
                             mimetype='image/jpg')



    if ((str(RoomNo)[2])=="4"):
        img = cv2.imread("Floor3.jpg")
        img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        height, width= img.shape
        pos=Rooms.index(str(RoomNo))
        MarkingPixel=RoomsPixels[pos]
        CurrLoc(MarkingPixel)
    ##change before
        cv2.imwrite("Location.jpg", img)
    ##change before
        with open("Location.jpg", 'rb') as bites:
            return send_file(io.BytesIO(bites.read()),attachment_filename='Floor3.jpg',
                             mimetype='image/jpg')





@app.route('/from/<loc>/to/<dest>')
def Path(loc,dest):
    img = cv2.imread("Floor2.png")
    img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    height, width= img.shape
    Rooms=['S-401', 'S-425', 'S-424','Empty', 'S-423', 'S-416', 'S-415', 'S-413','Empty', 'S-4th-Floor-Toilets', 'S-40',
           'S-403','S-402','S-404']
    RoomsPixels=[[126,391],[235,391],[355,391],[397,391],[397,372],[397,288],[397,217],
                 [397,145],[397,118],[385,118],[319,118],[263,118],[205,118],[152,118]]

    Floor2Rooms=['Building Manager','Staris',  'S-219',   'S-218',   'S-217',  'S-216',   'S-215',  'S-214',    'S-213', 'S-212',  'S-211',   'S-210',  'S-2nd-Floor-Toilets', 'S-207',  'S-205', 'S-204',  'S-206',  'S-203',   'S-201',  'S-202',    'Lift',    'LiftStairs']
    PixelsFloor2=[[286,87],[176,87] ,[176,133],[176,361], [241,418],  [241,418]  ,[241,418],[241,418],[318,418],[318,418],[318,418], [393,433],[393,433],[393,347],[393,347],[393,324],[393,324],[393,208],[393,184], [393,134], [393,134], [393,87]]
    
    Floor2RoomsPath=['Building Manager','Staris',  'S-219',   'S-218',   'S-217',  'S-216',   'S-215',  'S-214',    'S-213', 'S-212',  'S-211',   'S-210',  'S-2nd-Floor-Toilets', 'S-207',  'S-205', 'S-204',  'S-206',  'S-203',   'S-201',  'S-202',    'Lift',    'LiftStairs']
    PixelsFloor2Path=[[286,87],[176,87] ,[176,133],[176,361], [241,361],  [241,361]  ,[241,361],[241,361],[318,361],[318,361],[318,361], [393,361],[393,361],[393,347],[393,347],[393,324],[393,324],[393,208],[393,184], [393,134], [393,134], [393,87]]
    Floor2SideRooms=[  'S-217',  'S-216',   'S-215',  'S-214',    'S-213', 'S-212',  'S-211',   'S-210',  'S-2nd-Floor-Toilets']
    
    ShortPathRoom2=['S-203',   'S-201',    'S-202',    'Lift',    'LiftStairs','Building Manager','Staris',  'S-219']
    ShortPathPixels2=[[393,208],[393,184], [393,134], [393,134], [393,87],      [286,87],        [176,87], [176,133]]
    
    path=[]

    def MarkRoom(point):
        for x in range(point[0],point[0]+8):
            for y in range(point[1],point[1]+8):
                img[x,y]=0
            
        for x in range(point[0]-8,point[0]):
            for y in range(point[1],point[1]+8):
                img[x,y]=0

        for x in range(point[0]-8,point[0]):
            for y in range(point[1]-8,point[1]):
                img[x,y]=0

        for x in range(point[0],point[0]+8):
            for y in range(point[1]-8,point[1]):
                img[x,y]=0
            
  


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



    if (str(loc)[2]=='4') and (str(dest)[2]=='4'):
        img = cv2.imread("Floor3.jpg")
        img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        height, width= img.shape
        path=[]
        pos1=Rooms.index(str(loc))
        pos2=Rooms.index(str(dest))
        MarkingPixel1=RoomsPixels[pos1]
        MarkRoom(MarkingPixel1)
        MarkingPixel2=RoomsPixels[pos2]
        MarkRoom(MarkingPixel2)
        CreatePath(pos1,pos2,RoomsPixels)
        cv2.imwrite("Location.jpg", img)
        with open("Location.jpg", 'rb') as bites:
            return send_file(io.BytesIO(bites.read()),attachment_filename='Floor3.jpg',
                             mimetype='image/jpg')
        
    if (str(loc)[2]=='2') and (str(dest)[2]=='2'):
        pos1=Floor2Rooms.index(str(loc))
        pos2=Floor2Rooms.index(str(dest))
    
        MarkingPixel1=PixelsFloor2[pos1]
        MarkRoom(MarkingPixel1)
        MarkingPixel2=PixelsFloor2[pos2]
        MarkRoom(MarkingPixel2)
        MarkingPixel1=PixelsFloor2Path[pos1]
        MarkingPixel2=PixelsFloor2Path[pos2]



        if loc in Floor2SideRooms:
            MarkLine(PixelsFloor2[pos1],PixelsFloor2Path[pos1])

        if (loc in ShortPathRoom2) and (dest in ShortPathRoom2):
            pos1=ShortPathRoom2.index(loc)
            pos2=ShortPathRoom2.index(dest)
            CreatePath(pos1,pos2,ShortPathPixels2)

        if (loc not in ShortPathRoom2) or (dest not in ShortPathRoom2):
            CreatePath(pos1,pos2,PixelsFloor2Path)

    
        if dest in Floor2SideRooms:
            MarkLine(PixelsFloor2[pos2],PixelsFloor2Path[pos2])

    
        cv2.imwrite("Floor2-Copy.png", img)
    
        with open("Floor2-Copy.png", 'rb') as bites:
            return send_file(io.BytesIO(bites.read()),
                             attachment_filename='Floor3.jpg',
                             mimetype='image/jpg')

    
@app.route('/from/<loc>/to/<Dest>/using/<Using>')
def Use(loc,Dest,Using):
    print(loc,Dest,Using)
    Rooms=['Stairs','S-401', 'S-425', 'S-424','Empty', 'S-423', 'S-416', 'S-415', 'S-413','Empty', 'S-4th-Floor-Toilets', 'S-40',
           'S-403','S-402','S-404','Lift','LiftStairs']
    RoomsPixels=[[80,391],[126,391],[235,391],[355,391],[397,391],[397,372],[397,288],[397,217],[397,145],[397,118],[385,118],[319,118],[263,118],[205,118],[152,118],[114,118],[118,86]]
    Floor2Rooms=['Building Manager','Stairs',  'S-219',   'S-218',   'S-217',  'S-216',   'S-215',  'S-214',    'S-213', 'S-212',  'S-211',   'S-210',  'S-2nd-Floor-Toilets', 'S-207',  'S-205', 'S-204',  'S-206',  'S-203',   'S-201',  'S-202',    'Lift',    'LiftStairs']
    PixelsFloor2=[[286,87],[176,87] ,[176,133],[176,361], [241,418],  [241,418]  ,[241,418],[241,418],[318,418],[318,418],[318,418], [393,433],[393,433],[393,347],[393,347],[393,324],[393,324],[393,208],[393,184], [393,134], [393,134], [393,87]]
    
    Floor2RoomsPath=['Building Manager','Staris',  'S-219',   'S-218',   'S-217',  'S-216',   'S-215',  'S-214',    'S-213', 'S-212',  'S-211',   'S-210',  'S-2nd-Floor-Toilets', 'S-207',  'S-205', 'S-204',  'S-206',  'S-203',   'S-201',  'S-202',    'Lift',    'LiftStairs']
    PixelsFloor2Path=[[286,87],[176,87] ,[176,133],[176,361], [241,361],  [241,361]  ,[241,361],[241,361],[318,361],[318,361],[318,361], [393,361],[393,361],[393,347],[393,347],[393,324],[393,324],[393,208],[393,184], [393,134], [393,134], [393,87]]
    Floor2SideRooms=[  'S-217',  'S-216',   'S-215',  'S-214',    'S-213', 'S-212',  'S-211',   'S-210',  'Toilets']
    
    ShortPathRoom2=['S-203',   'S-201',    'S-202',    'Lift',    'LiftStairs','Building Manager','Stairs',  'S-219']
    ShortPathPixels2=[[393,208],[393,184], [393,134], [393,134], [393,87],      [286,87],        [176,87], [176,133]]
    
    path=[]

    def MarkRoom(point):
        for x in range(point[0],point[0]+8):
            for y in range(point[1],point[1]+8):
                img[x,y]=0
            
        for x in range(point[0]-8,point[0]):
            for y in range(point[1],point[1]+8):
                img[x,y]=0

        for x in range(point[0]-8,point[0]):
            for y in range(point[1]-8,point[1]):
                img[x,y]=0

        for x in range(point[0],point[0]+8):
            for y in range(point[1]-8,point[1]):
                img[x,y]=0
            
  


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
        

    if (str(loc)[2]=='2') or (str(Dest)[2]=='2'):
        img = cv2.imread("Floor2.png")
        img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        height, width= img.shape
        if (str(loc)[2]=='2'):
            pos1=Floor2Rooms.index(str(loc))
        elif(str(Dest)[2]=='2'):
            pos1=Floor2Rooms.index(str(Dest))
        pos2=Floor2Rooms.index(str(Using))
        MarkingPixel1=PixelsFloor2[pos1]
        MarkRoom(MarkingPixel1)
        MarkingPixel2=PixelsFloor2[pos2]
        MarkRoom(MarkingPixel2)
        MarkingPixel1=PixelsFloor2Path[pos1]
        MarkingPixel2=PixelsFloor2Path[pos2]
        print(pos1,pos2)

        if (((loc in ShortPathRoom2) or (Dest in ShortPathRoom2)) and (Using in ShortPathRoom2)):
            
            if (loc in ShortPathRoom2): 
                pos1=ShortPathRoom2.index(str(loc))
            if (Dest in ShortPathRoom2):
                pos1=ShortPathRoom2.index(str(Dest))
            pos2=ShortPathRoom2.index(str(Using))
            CreatePath(pos1,pos2,ShortPathPixels2)
            print(pos1,pos2)
            #CreatePath(pos1,pos2,PixelsFloor2Path)

        else:
            print(pos1,pos2)
            CreatePath(pos1,pos2,PixelsFloor2Path)

            
        if loc in Floor2SideRooms:
            MarkLine(PixelsFloor2[pos1],PixelsFloor2Path[pos1])

        if Dest in Floor2SideRooms:
            MarkLine(PixelsFloor2[pos1],PixelsFloor2Path[pos1])
        
        cv2.imwrite("Floor2-Copy.png", img)
        path=[]

      
    if (str(loc)[2]=='4') or (str(Dest)[2]=='4'):
        img = cv2.imread("Floor3.jpg")
        img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        height, width= img.shape
        path=[]
        if (str(loc)[2]=='4'):
            pos1=Rooms.index(str(loc))
        elif(str(Dest)[2]=='4'):
            pos1=Rooms.index(str(Dest))
        pos2=Rooms.index(str(Using))

        MarkingPixel1=RoomsPixels[pos1]
        MarkRoom(MarkingPixel1)
        MarkingPixel2=RoomsPixels[pos2]
        MarkRoom(MarkingPixel2)

        CreatePath(pos1,pos2,RoomsPixels)
        cv2.imwrite("Location.jpg", img)



    if (str(loc)[2]=='4'):
        img1 = cv2.imread("MoveToFloor2.jpg")
        img1 = cv2.cvtColor(img1, cv2.COLOR_BGR2GRAY)
        height, width= img1.shape
        img = cv2.imread("Location.jpg")
    
        img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        height, width= img1.shape
        for y in range(500):
            for x in range(25,525):
                img1[x,y]=img[x-25,y]
        cv2.imwrite("MoveToFloor2.jpg", img1)



        img = cv2.imread("Floor2-Copy.png")
    
        img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        height, width= img1.shape
        for y in range(500):
            for x in range(600,1100):
                img1[x,y]=img[x-600,y]
        cv2.imwrite("MoveToFloor2.jpg", img1)
    
    
        with open("MoveToFloor2.jpg", 'rb') as bites:
            return send_file(
                         io.BytesIO(bites.read()),
                         attachment_filename='Floor3.jpg',
                         mimetype='image/jpg')



    if (str(loc)[2]=='2'):
        img1 = cv2.imread("MoveToFloor4.jpg")
        img1 = cv2.cvtColor(img1, cv2.COLOR_BGR2GRAY)
        height, width= img1.shape
        img = cv2.imread("Floor2-Copy.png")
    
        img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        height, width= img1.shape
        for y in range(500):
            for x in range(25,525):
                img1[x,y]=img[x-25,y]
        cv2.imwrite("MoveToFloor4.jpg", img1)

        img = cv2.imread("Location.jpg")
    
        img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        height, width= img1.shape
        for y in range(500):
            for x in range(600,1100):
                img1[x,y]=img[x-600,y]
        cv2.imwrite("MoveToFloor4.jpg", img1)
    
    
        with     open("MoveToFloor4.jpg", 'rb') as bites:
            return send_file(
                         io.BytesIO(bites.read()),
                         attachment_filename='Floor3.jpg',
                         mimetype='image/jpg')




        

app.run(host = '0.0.0.0')


