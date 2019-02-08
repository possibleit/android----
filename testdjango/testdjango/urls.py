"""testdjango URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.11/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
#from django.conf.urls import url

from django.conf.urls import include,url
from django.contrib import admin
from testdjangoapp import views

from rest_framework import routers


route = routers.DefaultRouter()

route.register(r'list' , views.WeiboViewSet)


urlpatterns = [
    url(r'^admin/', admin.site.urls),
    url(r'^add/$', views.add, name='add'),
    url(r'^dbtest/$',views.dbtest),

    url('api/', include(route.urls)),

    url(r'^sendimage/$',views.sendimage),
    url(r'^img/$',views.img),

    url(r'^login/$',views.login),#登陆
    url(r'^register/$',views.register),#注册
    url(r'^sendweibo/$',views.sendweibo),#发微博
    url(r'^get/$',views.get),#获取微博
    url(r'^commit_get/$',views.commit_get),#客户端获得commit
    url(r'^sendcommit/$',views.sendcommit),#客户端发送commit
    url(r'^starone/$',views.starone),#关注
    url(r'^nostar/$',views.nostar),#取关
    url(r'^frindlist/$',views.frindlist),#获取好友列表
    url(r'^isstared/$',views.isstared),#判断是否关注
    url(r'^upload/$',views.upload)
]
