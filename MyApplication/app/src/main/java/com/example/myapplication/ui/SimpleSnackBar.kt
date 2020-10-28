package com.example.myapplication.ui


import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.myapplication.R
import com.example.myapplication.ui.SimpleSnackBar.Builder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.kasper_tooltip.view.*

/**
 *
 * @param context
 * @param pos CoordinatorLayout 의 위치를 준다. https://stackoverflow.com/questions/31492351/how-can-you-adjust-android-snackbar-to-a-specific-position-on-screen
 * @param duration SnackBar 가 얼마나 오래 나타낼 것인가에 대한 값
 *
 * Private constructor 라 @JvmOverloads 사용불가.. (혹시 다른 parameter 를 사용할 일이 있다면 빌더를 변경하세용'_'....)
 * @see Builder
 * @param attributeSet
 * @param defStyleAttr
 *
 * @author zero.dev on 2020.05.13
 */


class SimpleSnackBar private constructor(
    context: Context,
    pos: View,
    duration: Int,
    attributeSet: AttributeSet?,
    defStyleAttr: Int
) : ConstraintLayout(context, attributeSet, defStyleAttr) {
    constructor(context: Context, pos: View, duration: Int) : this(context, pos, duration, null, 0)
    constructor(context: Context, pos: View, duration: Int, attributeSet: AttributeSet?) : this(
        context,
        pos,
        duration,
        attributeSet,
        0
    )

    constructor(context: Context, pos: View, duration: Int, defStyleAttr: Int) : this(
        context,
        pos,
        duration,
        null,
        defStyleAttr
    )

    private val snackBar: Snackbar = Snackbar.make(pos, "", duration)

    init {
        inflate(context, R.layout.kasper_tooltip, this)
        (snackBar.view as Snackbar.SnackbarLayout).apply {
            background = null
            findViewById<TextView>(com.google.android.material.R.id.snackbar_text).visibility =
                View.INVISIBLE
            setPadding(0, 0, 0, 0)
            alpha = 0.9f
        }.addView(this)
    }

    fun text(@StringRes stringRes: Int): SimpleSnackBar {
        tooltip_text.text = context.resources.getString(stringRes)
        return this
    }

    fun text(string: String): SimpleSnackBar {
        tooltip_text.text = string
        return this
    }

    fun background(@DrawableRes backgroundRes: Int): SimpleSnackBar {
        background = context.getDrawable(backgroundRes)
        return this
    }


    fun show() = snackBar.show()

    fun hide() = snackBar.dismiss()

    class Builder private constructor(private val context: Context) {

        fun into(pos: View, duration: Int): SimpleSnackBar {
            return SimpleSnackBar(context = context, pos = pos, duration = duration)
        }

        companion object {
            private var INSTANCE: Builder? = null
            fun with(context: Context) = INSTANCE ?: synchronized(this) {
                INSTANCE ?: Builder(context).also { INSTANCE = it }
            }

        }
    }

}



