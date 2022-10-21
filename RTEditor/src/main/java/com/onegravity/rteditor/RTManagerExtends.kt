package com.onegravity.rteditor

import android.text.Editable
import com.onegravity.rteditor.effects.Effect
import com.onegravity.rteditor.effects.Effects
import com.onegravity.rteditor.effects.NumberEffect
import com.onegravity.rteditor.spans.NumberSpan
import com.onegravity.rteditor.spans.RTSpan
import com.onegravity.rteditor.utils.isNewLineChar
import com.onegravity.rteditor.utils.isZeroWidthChar

//fun <V, C : RTSpan<V>> onEffectSelected(editor: RTEditText, effect: Effect<V, C>, value: V) {
//    val editable = editor.text ?: return
//
//    if (effect is NumberEffect) {
//        val lineStart = editor.paragraphsInSelection.start()
//        if (value as Boolean) {
//            // insert zero width char
//            if (editable.length - 1 >= lineStart) {
//                if (!editable[lineStart].isZeroWidthChar()) {
//                    editable.insertZeroWidthChar(lineStart)
//                }
//            } else {
//                editable.insertZeroWidthChar(lineStart)
//            }
//        } else {
//            // remove zero width char
//            if (editable.length > 0 && lineStart < editable.length) {
//                if (editable[lineStart].isZeroWidthChar()) {
//                    editable.delete(lineStart, lineStart + 1)
//                }
//            }
//        }
//    }
//}

fun onTextChanged(editor: RTEditText, toolbars: MutableMap<Int, RTToolbar>,
                            startChangedPos: Int, endChangedPos: Int,
                            selStartBefore: Int, selEndBefore: Int,
                            selStartAfter: Int, selEndAfter: Int) {

    val editable = editor.text ?: return

    val numberSpans = Effects.NUMBER.spansInSelection(editor)
    if (numberSpans.isNotEmpty()) {
        if (endChangedPos > startChangedPos) {
//            val span = numberSpans[0] as NumberSpan
//            val spanStart = editable.getSpanStart(span)
//            val spanEnd = editable.getSpanEnd(span)
//            // input
//            val lastIndex = endChangedPos - 1
//            if (editable[lastIndex].isNewLineChar()) {
//                // 在没有内容的情况下直接回车，我们要删掉span和\n
//                //
//                // before:
//                // * [Z]A\n
//                // * [Z]<User types \n here, at an empty span>
//                //
//                // Now:
//                // * [Z]A\n
//                //
//                if (lastIndex > 1 && editable[lastIndex - 1].isZeroWidthChar()) {
//                    editor.disableTextChanges {
//                        editable.delete(spanStart, spanEnd)
//                        editor.applyEffect(Effects.NUMBER, false)
//                    }
//                    for (toolbar in toolbars.values) {
//                        toolbar.setNumber(false)
//                    }
//                } else {
//                    // insert zero width character
//                    editable.insertZeroWidthChar(spanEnd)
//                }
//            }
        } else {
//            // delete
//            val firstSpan = numberSpans.first()
//            val firstSpanStart = editable.getSpanStart(firstSpan)
//            val firstSpanEnd = editable.getSpanEnd(firstSpan)
//            when {
//                startChangedPos == 0 -> {
//                    // 删掉首行第一个零宽字符
//                    //
//                    // Before:
//                    // * [Z] <用户光标在这里，然后删除[Z]> Other words
//                    // * [Z]B
//                    //
//                    // Now:
//                    // Other words
//                    // * [Z]B
//                    editable.removeSpan(firstSpan)
//                }
//                editable[endChangedPos - 1].isNewLineChar() -> {
//                    // 删掉其他行第一个零宽字符
//                    //
//                    // Before:
//                    // * [Z]A\n
//                    // * [Z] <用户的光标在这里，然后删除了[Z]> Other words
//                    // * [Z]C
//                    //
//                    // Now:
//                    // * [Z]A \n
//                    // * <用户的光标在这里, 即将删除\n>Other words
//                    // * [Z]C
//                    //
//                    // After:
//                    // * [Z]A Other words
//                    // * [Z]C
//                    editable.removeSpan(firstSpan)
//
//                    // 删掉上一行的\n，可以让光标接到上一行
//                    // 这里会触发下面"firstSpanEnd == endChangedPos"的条件
//                    editable.delete(endChangedPos - 1, endChangedPos)
//                }
//                firstSpanEnd == endChangedPos -> {
//                    // 这里表示用户删除了上一行的\n
//                    //
//                    // 所以我们要：
//                    // - 删掉拼接上来的段落span
//                    // - 删掉拼接上来的的ZWSP
//                    // - 我们需要重新设置上一行的span的范围
//                    //
//                    // Before:
//                    // * [Z]A\n
//                    // <用户的光标在这里，然后删除了上一行的\n> [Z]B\n
//                    // * [Z]C
//                    //
//                    // Now:
//                    // * [Z]AB\n <删掉[Z]并重新设置Span范围>
//                    // * [Z]C
//
//                    // 删掉因为删除后残留的段落span，有两种情况
//                    // - spanEnd后面没有字符，这个时候getSpans会包含上一段的span，所以要过滤
//                    // - spanEnd后面有字符，这个时候getSpans只有后面的spans，可以全部删除
//                    editable.getSpans<NumberSpan>(
//                            start = firstSpanEnd,
//                            end = min(firstSpanEnd + 1, editable.length)
//                    ).forEach { span ->
//                        if (span != firstSpan) {
//                            editable.removeSpan(span)
//                        }
//                    }
//
//                    var newSpanEnd = firstSpanEnd
//                    val lineEnd = editor.paragraphsInSelection.end()
//                    val hasOtherWords = lineEnd > firstSpanEnd
//                    if (hasOtherWords) {
//                        // 如果span后面还有文字，我们将span的范围扩大到lineEnd
//                        editable.removeSpan(firstSpan)
//                        newSpanEnd = lineEnd
//
//                        // 如果这一行有\n，我们设置的span范围不需要包含\n
//                        val lastIndex = lineEnd - 1
//                        if (lastIndex in editable.indices) {
//                            if (editable[lastIndex].isNewLineChar()) {
//                                newSpanEnd = lastIndex
//                            }
//                        }
//                        editable.setSpan(
//                                firstSpan, firstSpanStart, newSpanEnd,
//                                Editable.SPAN_INCLUSIVE_INCLUSIVE
//                        )
//                    }
//
//                    // 如果span后面接着是ZWSP，我们将它删掉，删除的操作我们都放到最后面
//                    if (hasOtherWords) {
//                        if (editable[firstSpanEnd].isZeroWidthChar()) {
//                            editable.delete(firstSpanEnd, firstSpanEnd + 1)
//                        }
//                    }
//                }
//                firstSpanStart == startChangedPos -> {
//                    // 删除了上一段其他样式的\n
//                    // 既然能走到这里，就表明上一段是正文，没有别的span，所以我们要在这里移除自己的span
//                    //
//                    // Before:
//                    // Other words\n
//                    // * <用户光标在这里，然后删除\n> [Z]A
//                    // * [Z]B
//                    //
//                    // Now:
//                    // Other words [Z]A
//                    // * [Z]B
//                    editable.removeSpan(firstSpan)
//
//                    // 删掉ZWSP
//                    if (firstSpanStart in editable.indices) {
//                        if (editable[firstSpanStart].isZeroWidthChar()) {
//                            editable.delete(firstSpanStart, firstSpanStart + 1)
//                        }
//                    }
//                }
//            }
        }
    }

}

fun RTEditText.disableTextChanges(block: () -> Unit) {
    this.ignoreTextChanges()
    block()
    this.registerTextChanges()
}
