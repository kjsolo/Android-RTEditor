package com.onegravity.rteditor.utils

import android.text.Editable

fun Char.isNewLineChar(): Boolean {
    return this == Constants.CHAR_NEW_LINE
}

fun Char.isZeroWidthChar(): Boolean {
    return this == Constants.ZERO_WIDTH_SPACE_CHAR
}

//fun Editable.insertZeroWidthChar(index: Int) {
//    this.insert(index, Constants.ZERO_WIDTH_SPACE_STR)
//}

fun Editable.insertZeroWidthChar(lineStart: Int) {
    // insert zero width char
    val editable = this
    if (editable.length - 1 >= lineStart) {
        if (!editable[lineStart].isZeroWidthChar()) {
            this.insert(lineStart, Constants.ZERO_WIDTH_SPACE_STR)
        }
    } else {
        this.insert(lineStart, Constants.ZERO_WIDTH_SPACE_STR)
    }
}

fun Editable.insertZeroWidthChar(paragraph: Paragraph) {
    // insert zero width char
    val editable = this
    val lineStart = paragraph.start()
    if (editable.length - 1 >= lineStart) {
        if (!editable[lineStart].isZeroWidthChar()) {
            this.insert(lineStart, Constants.ZERO_WIDTH_SPACE_STR)
        }
    } else {
        this.insert(lineStart, Constants.ZERO_WIDTH_SPACE_STR)
    }
}

fun Editable.removeZeroWidthChar(lineStart: Int) {
    // remove zero width char
    val editable = this
    if (editable.isNotEmpty() && lineStart < editable.length) {
        if (editable[lineStart].isZeroWidthChar()) {
            editable.delete(lineStart, lineStart + 1)
        }
    }
}