from django.contrib import admin
from .models import Weibotable,commit,Usertable,starlist,Img
# Register your models here.
@admin.register(Weibotable)
class BlogTypeAdmin(admin.ModelAdmin):
    list_display = ('pk', 'username','local','time','text','commitnum')
@admin.register(commit)
class CommitAdmin(admin.ModelAdmin):
    list_display = ('pk', 'text','weiboname','commitname','time')
@admin.register(Usertable)
class UsertableAdmin(admin.ModelAdmin):
    list_display = ('pk', 'username', 'password')

@admin.register(starlist)
class starlistAdmin(admin.ModelAdmin):
    list_display = ('pk', 'stared', 'bestared')

@admin.register(Img)
class ImgAdmin(admin.ModelAdmin):
    list_display = ('pk','img','time')