package com.example.newzyapp

object ColorPicker {
    var colors = arrayOf( "#0000ff", "#0040ff", "#00bfff",  "#00ffff", "#00ffbf",  	"#00ff80",
        "#00ff00", 	"#80ff00", 	"#bfff00", "#ffbf00", "#ff8000", 	"#ff0040", "#ff00bf", 	"#ff00ff", "#8000ff")

    var colorIndex = 0
    public
    fun getColors(): String
    {
        return colors[ (colorIndex++ % colors.size) ]
    }

}