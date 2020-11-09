package com.example.myapplication.ui.alarm

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.accessibility.AccessibilityManager
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityAlarmPickerBinding
import com.example.myapplication.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_alarm_picker.*

class AlarmPickerActivity : BaseActivity<ActivityAlarmPickerBinding>() {
    override val layoutId: Int = R.layout.activity_alarm_picker

    private val dayList by lazy { listOf(mon, tue, wed, thu, fri, sat, sun) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDayEventListener()
    }

    private fun setDayEventListener() {
        if (isAccessibilityMode()) {//접근성 확인
            dayList.setClickListenersOnDays()
        } else {
            setDragDetectListener()
        }
    }

    private fun isAccessibilityMode() =
        ((getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager).isEnabled)

    private fun List<TextView>.setClickListenersOnDays() = forEach { day ->
        day.setOnClickListener { clickedView ->
            onChangeDaySelected(clickedView as TextView)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setDragDetectListener() {
        var previousDay: TextView? = null
        dayWrapper.setOnTouchListener { _, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    previousDay = null
                }

                MotionEvent.ACTION_MOVE -> {
                    val day =
                        getSelectedDayByPosition(event.rawX, event.rawY)
                            ?: return@setOnTouchListener false
                    if (previousDay != day) {
                        onChangeDaySelected(day)
                        previousDay = day
                    }
                }
            }
            true
        }
    }

    private fun onChangeDaySelected(view: TextView) {
        view.isSelected = !view.isSelected
    }

    private fun getSelectedDayByPosition(curX: Float, curY: Float): TextView? {
        dayList.forEach { day ->
            val inRange = day.containsPosition(curX, curY)
            if (inRange) return day
        }
        return null
    }

    private fun TextView.containsPosition(curX: Float, curY: Float): Boolean {
        val (x, y) = getDayPosition(this)

        val dayXRange = (x..x + width.toFloat())
        val dayYRange = (y..y + height.toFloat())

        val (inRangeX, inRangeY) = (curX in dayXRange) to (curY in dayYRange)
        return inRangeX && inRangeY
    }

    private fun getDayPosition(day: TextView) = IntArray(2)
        .apply { day.getLocationOnScreen(this) }
        .map { it.toFloat() }

    companion object {
        fun newIntent(context: Context) = Intent(context, AlarmPickerActivity::class.java)
    }

}
