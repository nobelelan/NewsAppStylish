package com.parulson.newsappchezzycode

object ColorPicker {
    val colors= arrayOf("#FF0000","#4287f5","#112647","#8062a8","#920c99","#6fad50","#db500b")
    var colorIndex = 1
    fun getColor(): String{
        return colors[colorIndex++ % colors.size]
    }
}