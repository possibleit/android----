# -*- coding: utf-8 -*-
# Generated by Django 1.11.8 on 2019-01-13 10:02
from __future__ import unicode_literals

from django.db import migrations, models
import django.utils.timezone


class Migration(migrations.Migration):

    dependencies = [
        ('testdjangoapp', '0003_usertable_weibotable'),
    ]

    operations = [
        migrations.AddField(
            model_name='weibotable',
            name='img',
            field=models.ImageField(default=django.utils.timezone.now, upload_to='img'),
            preserve_default=False,
        ),
    ]
