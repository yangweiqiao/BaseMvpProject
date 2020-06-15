package com.example.baselibrary.data.protocol

class BaseException  (
    val status: Int,
    val msg: String
):Throwable()