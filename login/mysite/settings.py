"""
Django settings for mysite project.

Generated by 'django-admin startproject' using Django 2.0.5.

For more information on this file, see
https://docs.djangoproject.com/en/2.0.5/topics/settings/

For the full list of settings and their values, see
https://docs.djangoproject.com/en/2.0.5/ref/settings/
"""

import os
from django.contrib.messages import constants as messages

# Build paths inside the project like this: os.path.join(BASE_DIR, ...)
BASE_DIR = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))

# Quick-start development settings - unsuitable for production
# See https://docs.djangoproject.com/en/2.0.5/howto/deployment/checklist/

# SECURITY WARNING: keep the secret key used in production secret!
SECRET_KEY = '^7ca8gx-ssbzbd+9y2x)jow3v*8f0-csv(#v3ji(k=uz@i68#p'

# SECURITY WARNING: don't run with debug turned on in production!
DEBUG = True

ALLOWED_HOSTS = ["*"]

# Application definition

INSTALLED_APPS = [
    'django.contrib.admin',
    'django.contrib.auth',
    'django.contrib.contenttypes',
    'django.contrib.sessions',
    'django.contrib.messages',
    'django.contrib.staticfiles',
    'login.apps.LoginConfig',
    'channels',
    'demo',
    'django_cleanup',  # should go after your apps
]

MIDDLEWARE = [
    'django.middleware.security.SecurityMiddleware',
    'django.contrib.sessions.middleware.SessionMiddleware',
    'django.middleware.common.CommonMiddleware',
    'django.middleware.csrf.CsrfViewMiddleware',
    'django.contrib.auth.middleware.AuthenticationMiddleware',
    'django.contrib.messages.middleware.MessageMiddleware',
    'django.middleware.clickjacking.XFrameOptionsMiddleware',
]

ROOT_URLCONF = 'mysite.urls'

ASGI_APPLICATION = 'mysite.routing.application'

CHANNEL_LAYERS = {
    'default': {
        'BACKEND': 'channels_redis.core.RedisChannelLayer',
        'CONFIG': {
            "hosts": [('127.0.0.1', 6379)],
        },
    },
}

TEMPLATES = [
    {
        'BACKEND': 'django.template.backends.jinja2.Jinja2',
        'DIRS': [os.path.join(BASE_DIR, 'templates')]
        ,
        'APP_DIRS': True,
        'OPTIONS': {
            'environment': 'login.jinja2_env.environment',
        },
    },
    {
        'BACKEND': 'django.template.backends.django.DjangoTemplates',
        'DIRS': [os.path.join(BASE_DIR, 'templates')],
        'APP_DIRS': True,
        'OPTIONS': {
            'context_processors': [
                'django.template.context_processors.debug',
                'django.template.context_processors.request',
                'django.contrib.auth.context_processors.auth',
                'django.contrib.messages.context_processors.messages',
            ],
        },
    },
]

WSGI_APPLICATION = 'mysite.wsgi.application'

# Database
# https://docs.djangoproject.com/en/2.0.5/ref/settings/#databases

DATABASES = {
#     'default': {
#         'ENGINE': 'django.db.backends.sqlite3',
#         'NAME': os.path.join(BASE_DIR, 'db.sqlite3'),
#     }
#    'default': {
#        'ENGINE': 'django.db.backends.mysql',
#        'NAME': 'BLOG',
#        'USER': 'root',
#        'PASSWORD': '19981028lhy',
#        'HOST': '127.0.0.1',
#         'PORT': '3306'
#     }

#    'default': {
#        'ENGINE': 'django.db.backends.mysql',
#        'NAME': 'taggingsystem',
#        "USER": 'root',
#        "PASSWORD": 'liao120212',
#         'HOST': '127.0.0.1',  # 本机地址
#         'PORT': '3306',  # 端口
#
# }

# 'default': {
#         'ENGINE': 'django.db.backends.mysql',
#         'NAME': 'MySQL',
#         "USER": 'dbUser',
#         "PASSWORD": 'zkn980516',
#         'HOST': '127.0.0.1',  # 本机地址
#         'PORT': '3306',  # 端口
# }
   'default': {
       'ENGINE': 'django.db.backends.mysql',
       'NAME': 'taggingdb',
       "USER": 'root',
       "PASSWORD": 'password',
        'HOST': '127.0.0.1',  # 本机地址
        'PORT': '3306',  # 端口

}

}

# Password validation
# https://docs.djangoproject.com/en/2.0.5/ref/settings/#auth-password-validators

AUTH_PASSWORD_VALIDATORS = [
    {
        'NAME': 'django.contrib.auth.password_validation.UserAttributeSimilarityValidator',
    },
    {
        'NAME': 'django.contrib.auth.password_validation.MinimumLengthValidator',
    },
    {
        'NAME': 'django.contrib.auth.password_validation.CommonPasswordValidator',
    },
    {
        'NAME': 'django.contrib.auth.password_validation.NumericPasswordValidator',
    },
]

# Internationalization
# https://docs.djangoproject.com/en/2.0.5/topics/i18n/

LANGUAGE_CODE = 'zh-hans'

TIME_ZONE = 'Asia/Shanghai'

USE_I18N = True

USE_L10N = True

USE_TZ = False

# Static files (CSS, JavaScript, Images)
# https://docs.djangoproject.com/en/2.0.5/howto/static-files/


MEDIA_URL = '/media/'
MEDIA_ROOT = os.path.join(BASE_DIR, "media")

STATIC_URL = '/static/'
STATICFILES_DIRS = [
    os.path.join(BASE_DIR, "static"),
]

SESSION_COOKIE_AGE = 60 * 60
SESSION_SAVE_EVERY_REQUEST = True
SESSION_EXPIRE_AT_BROWSER_CLOSE = False

MESSAGE_TAGS = {
    messages.SUCCESS: "alert alert-success",
    messages.INFO: "alert alert-info",
    messages.WARNING: "alert alert-warning",
    messages.ERROR: "alert alert-danger",
}

EMAIL_BACKEND = 'django.core.mail.backends.smtp.EmailBackend'
EMAIL_USE_TLS = True  # 是否使用TLS安全传输协议(用于在两个通信应用程序之间提供保密性和数据完整性。)
EMAIL_HOST = 'smtp.qq.com'  # 发送邮件的邮箱 的 SMTP服务器
EMAIL_PORT = 25  # 发件箱的SMTP服务器端口
EMAIL_HOST_USER = '1476644102@qq.com'  # 发送邮件的邮箱地址
EMAIL_HOST_PASSWORD = 'biajvghftkqnjibb'  # 发送邮件的邮箱密码(这里使用的是授权码)
EMAIL_FROM = 'OneIsAll<1476644102@qq.com>'  # 收件人看到的发件人
