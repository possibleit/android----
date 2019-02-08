#!/usr/bin/env python 
# -*- coding:utf-8 -*-
from rest_framework import serializers
from .models import Weibotable


class WeibotableSerializers(serializers.ModelSerializer):
    class Meta:
        model = Weibotable  # 指定的模型类
        fields = ('pk', 'username', 'local', 'time','text','image')