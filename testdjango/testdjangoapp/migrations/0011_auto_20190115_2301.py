# -*- coding: utf-8 -*-
# Generated by Django 1.11.8 on 2019-01-15 15:01
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('testdjangoapp', '0010_commit_time'),
    ]

    operations = [
        migrations.AlterField(
            model_name='weibotable',
            name='time',
            field=models.CharField(max_length=100),
        ),
    ]
