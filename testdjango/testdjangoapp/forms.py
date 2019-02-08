#!/usr/bin/env python 
# -*- coding:utf-8 -*-
from django import forms
from .models import Img
class LogForm(forms.Form):
    username = forms.CharField(max_length = 30)
    time = forms.CharField(max_length=100)
    local = forms.CharField(max_length=100)
    text = forms.CharField()
    commitnum = forms.IntegerField()

class commitForm(forms.Form):
    text = forms.CharField()
    weiboname = forms.CharField(max_length=20)
    commitname = forms.CharField(max_length=20)
    time = forms.CharField(max_length=100)

class starForm(forms.Form):
    bestared = forms.CharField(max_length=20)
    stared = forms.CharField(max_length=20)

class ImgForm(forms.Form):
    # img = forms.FileField()
    # name = forms.CharField()
    class Meta:
        model = Img