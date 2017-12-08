package com.patres.converter

import java.util.ArrayList

fun CharSequence.split(number: Int): List<String> {
    val strings = ArrayList<String>()
    var index = 0
    while (index < this.length) {
        strings.add(this.substring(index, Math.min(index + number, this.length)))
        index += number
    }
    return strings
}