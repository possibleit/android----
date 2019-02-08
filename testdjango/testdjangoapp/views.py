from django.shortcuts import render

# Create your views here.
from django.http import HttpResponse, StreamingHttpResponse
from django.core.files.base import ContentFile
import json
import pymysql
from testdjangoapp.models import *
from django.views.decorators.csrf import csrf_protect
from testdjangoapp.forms import LogForm,ImgForm

from rest_framework import viewsets
from .serializers import WeibotableSerializers

class WeiboViewSet(viewsets.ModelViewSet):
    # 指定结果集并设置排序
    queryset = Weibotable.objects.all().order_by('-pk')
    # 指定序列化的类
    serializer_class = WeibotableSerializers


def add(req):
    a=req.GET['a']
    b=req.GET['b']
    c=int (a) + int (b)
    return HttpResponse(str(c))
# def getjson(req):
#     resp={'errorcode':100,'detail':'success'}
#     return HttpResponse(json.dumps(resp),content_type='application/json')
def dbtest(req):
    p = testdb(name=req.GET['name'],bian=1)
    p.save()
    s=testdb.objects.get(name='ss').id
    return HttpResponse(s)

def login(req):#登陆
    name = req.GET['name']
    password = req.GET['password']
    if Usertable.objects.get_or_create(username = name,password=password)[1] != True :
        return HttpResponse('name error')
    else:
        return HttpResponse('success')
def register(req):#注册
    name = req.GET['name']
    password = req.GET['password']
    user = Usertable.objects.filter(username = name)
    if user.exists() :
        if user[0].password == password:
            return HttpResponse('登陆成功')
        else:
            return HttpResponse('密码错误')
    else:
        return HttpResponse('无用户')



def sendweibo(req):#接收客户端发送的微博
    name = req.GET['name']
    local = req.GET['local']
    text = req.GET['text']
    time = req.GET['time']
    commitnum = req.GET['commitnum']
    if name == "null":
        return HttpResponse("请登陆之后再发表微博")
    else:
        #需验证用户名
        #Usertable.objects.filter(name=name)
        #if Usertable.objects.get(username = name)[1] != True :
        p = Weibotable(username=name,local=local,text=text,time=time,commitnum=commitnum)
        p.save()
        return HttpResponse("微博发布成功")



def sendcommit(req):#接受客户端发送的commit
    text = req.GET['text']
    weiboname = req.GET['weiboname']
    commitname = req.GET['commitname']
    time = req.GET['time']
    if commitname  == 'null':
        return HttpResponse('请登陆之后再发表评论')
    else:
        #这里需要查询到这条微博，并将其cmmitnum + 1
        s = Weibotable.objects.get(username=weiboname,time=time)
        s.commitnum = s.commitnum+1
        s.save()
        p = commit(weiboname=weiboname, text=text, commitname=commitname, time=time)
        p.save()
        return HttpResponse("评论发表成功")
def get(req):#客户端获取前五个微博
    resp = {}

    x = []
    num = req.GET['num']
    # if num == 5:
    new_data = Weibotable.objects.order_by('-id')[:5]
    # s = Weibotable.objects.all().reverse()
    # s1 = s[:5]#Weibotable.objects.all()[:5]
    # if s.exists() :
    for s in new_data:
        d = {}
        d['time'] = s.time
        d['username'] = s.username

        d['local'] = s.local
        d['text'] = s.text
        d['commitnum'] = s.commitnum
        x.append(d)
        resp["body"] = x
            # for x in s:
            #     d['username'] = x.username
            #     d['time'] = x.time
            #     d['local'] = x.local
            #     d['text'] = x.text
            #     resp.add(x)
    return HttpResponse(json.dumps(resp), content_type="application/json")
        #return HttpResponse("6")
    #return HttpResponse("5")
def sendimage(req):
    if req.method == 'POST':
        return HttpResponse("成功了一半")
    else:
        str = req.FILES.get("img")
        #if str.size > 10000 and str.size < 20480000:
        path = default_storage.save('F:\python代码\Django\testdjango\img' + str.name,ContentFile(str.read()))
        tmp_file = os.path.join(settings.MEDIA_ROOT,path)
        return HttpResponse(type(str))

def img(req):
    #req.POST[]
    def file_iterator(file_name,chunk_size=512):
        with open(file_name,'r',encoding="gbk",errors='ignore') as f:
            while True:
                c = f.read(chunk_size)
                if c:
                    yield c
                else:
                    break
    file = "F:/python代码/Django/testdjango/media/images/图形学.jpg"
    response = StreamingHttpResponse(file_iterator(file))
    response['Content-Type'] = 'application/octet-stream'
    response['Content-Disposition'] = 'attachment;filename="{0}"'.format(file)
    return HttpResponse(response)

def commit_get(req):#客户端获取所有评论
    name =  req.GET['name']
    time = req.GET['time']
    resp = {}
    x = []
    #获取所有评论
    #s = commit.objects.all()
    ss  = commit.objects.filter(time=time).filter(weiboname=name)
    for s in ss:
        d = {}
        d['text'] = s.text
        d['weiboname'] = s.weiboname
        d['commitname'] = s.commitname
        d['time'] = s.time
        x.append(d)
        resp["body"] = x
        # for x in s:
        #     d['username'] = x.username
        #     d['time'] = x.time
        #     d['local'] = x.local
        #     d['text'] = x.text
        #     resp.add(x)
    return HttpResponse(json.dumps(resp), content_type="application/json")

def  starone(req):
    #关注一个人
    stared =  req.GET['stared']
    bestared  = req.GET['bestared']

    s =  starlist(stared = stared,bestared = bestared)
    s.save()
    return HttpResponse("关注成功")

def nostar(req):
    #取关
    stared = req.GET['stared']
    bestared = req.GET['bestared']

    s = starlist.objects.filter(stared=stared, bestared=bestared).delete()

    return HttpResponse("取关成功")

def frindlist(req):#获取关注列表
    name = req.GET['name']

    resp = {}
    x = []
    # 获取所有评论
    # s = commit.objects.all()
    ss = starlist.objects.filter(stared=name)#关注者的name
    for s in ss:
        d = {}
        d['stared'] = s.stared
        d['bestared'] = s.bestared
        x.append(d)
        resp["stared"] = x
        # for x in s:
        #     d['username'] = x.username
        #     d['time'] = x.time
        #     d['local'] = x.local
        #     d['text'] = x.text
        #     resp.add(x)
    ss = starlist.objects.filter(bestared=name)  # 关注者的name
    x = []
    for s in ss:
        d = {}
        d['stared'] = s.stared
        d['bestared'] = s.bestared
        x.append(d)
        resp["bestared"] = x
        # for x in s:
        #     d['username'] = x.username
        #     d['time'] = x.time
        #     d['local'] = x.local
        #     d['text'] = x.text
        #     resp.add(x)
    return HttpResponse(json.dumps(resp), content_type="application/json")

def isstared(req):
    name1 = req.GET['name1']
    name2 = req.GET['name2']

    if name1 == name2 or  name1 == "null":
        return HttpResponse("false")
    elif starlist.objects.filter(stared=name1,bestared=name2).exists() :
        return HttpResponse("true")
    else:
        return HttpResponse('flase')

def upload(req):
    if req.method == 'POST':
        form = ImgForm(req.POST, req.FILES)
        if form.is_valid():
            instance = Img(img=req.FILES['file'])  # 保存文件到FileField域
            instance.save()
            return HttpResponse('success')
        else:
            return HttpResponse('error')