package xyz.akko.yandroid.util

import android.app.Activity
import java.util.*

class MyActivityManager {
    companion object{
        private var activityList:Stack<Activity> = Stack<Activity>()
    }

    fun getActivityList(): Stack<Activity>
    {
        return activityList
    }
    fun addActivity(activity: Activity)
    {
        activityList.push(activity)
    }
    fun finishActivity(activity: Activity)
    {
        activityList.remove(activity)
    }
    fun finishAll()
    {
        while (!activityList.empty())
        {
            activityList.peek().finish()
            activityList.pop()
        }
    }
}