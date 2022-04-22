from flask import Flask

app = Flask(__name__)

@app.route('/')
def test():
    return "App is running on Server using Flask"

app.run(host='0.0.0.0',port=3000)
