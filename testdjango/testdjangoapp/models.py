from django.db import models
import uuid

# Create your models here.
class testdb(models.Model):
     name=models.CharField(max_length=20)
     bian=models.IntegerField(default=0)
     def __str__(self):
         return self.name
class Usertable(models.Model):
    username=models.CharField(max_length=20,unique=True)#用户名唯一
    password=models.CharField(max_length=20)
    def __unicode__(self):
        return '%d: %s' % (self.pk, self.username)


def custom_path(instance, filename):
    ext = filename.split('.')[-1]
    filename = '{}.{}'.format(uuid.uuid4().hex[:10], ext)
    filename  = "image/" + filename
    return filename

class Weibotable(models.Model):
    time = models.CharField(max_length=100)
    local = models.CharField(max_length=100)
    username = models.CharField(max_length=20)
    text = models.TextField()
    commitnum = models.IntegerField(default=0)
    def __unicode__(self):
        return '%d: %s' % (self.pk, self.username)

class commit(models.Model):
    text = models.TextField()
    weiboname = models.CharField(max_length=20)
    commitname = models.CharField(max_length=20)
    time = models.CharField(max_length=100)
    def __unicode__(self):
        return '%d: %s' % (self.pk)

class starlist(models.Model):
    bestared = models.CharField(max_length=20)
    stared = models.CharField(max_length=20)
    def __unicode__(self):
        return '%d: %s' % (self.pk)

class Img(models.Model):
    img = models.FileField()
    time = models.TextField(default='tt')