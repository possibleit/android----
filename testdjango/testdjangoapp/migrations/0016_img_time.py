# -*- coding: utf-8 -*-
# Generated by Django 1.11.8 on 2019-02-06 09:21
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('testdjangoapp', '0015_remove_img_name'),
    ]

    operations = [
        migrations.AddField(
            model_name='img',
            name='time',
            field=models.TextField(default='tt'),
        ),
    ]