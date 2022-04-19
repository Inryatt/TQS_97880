import json
import sys
import urllib.request 
import os



from flask import render_template,Flask,request

app = Flask(__name__)

@app.route("/")
def home():
    return render_template("index.html")

@app.route("/world")
def world():
    try:
        url = "http://localhost:8080/api/v1/all".format(os.environ.get("TMDB_API_KEY"))

        response = urllib.request.urlopen(url)
        data = response.read()
        dict = json.loads(data)
    except Exception:
        dict = "Error fetching data."
    return render_template("worldwide.html",data=dict)


@app.route("/country/", methods=["GET"])
def country(days=0):
        name = request.args.get("country")
        if name is None:
            url = "http://localhost:8080/api/v1/countries/".format(os.environ.get("TMDB_API_KEY"))
            name=""
        else:
            url = "http://localhost:8080/api/v1/countries/".format(os.environ.get("TMDB_API_KEY"))+name


        response = urllib.request.urlopen(url)
        data = response.read()
        dict = json.loads(data)

        try:
            if name=="":
                flag=""
            else:
                flag = dict["countryInfo"]["flag"]
        except KeyError:
            flag=""
    #except Exception:
    #    dict = "Error fetching data."
        return render_template("country.html",data=dict,country=name,flag=flag)


@app.route("/stats")
def stats():
    try:
        url = "http://localhost:8080/api/v1/stats".format(os.environ.get("TMDB_API_KEY"))

        response = urllib.request.urlopen(url)
        data = response.read()
        dict = json.loads(data)
    except Exception:
        dict = "Error fetching data."
    return render_template("stats.html",data=dict)


