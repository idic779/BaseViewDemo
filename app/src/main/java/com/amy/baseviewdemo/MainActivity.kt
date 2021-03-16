package com.amy.baseviewdemo

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updatePadding
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.drakeet.multitype.MultiTypeAdapter
import com.drakeet.multitype.ViewDelegate

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Make the status and navigation bars transparent
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = PregnantPagerAdapter(viewPager)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val items = ArrayList<String>()
        val adapter = MultiTypeAdapter(items)
        adapter.register(String::class, StringViewDelegate())
        recyclerView.adapter = adapter

        val drawer = findViewById<DrawerLayout>(R.id.drawer)
        drawer.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                // items.add("onDrawerSlide($slideOffset)")
                // adapter.notifyDataSetChanged()
            }

            override fun onDrawerOpened(drawerView: View) {
                items.add("onDrawerOpened()")
                adapter.notifyDataSetChanged()
            }

            override fun onDrawerClosed(drawerView: View) {
                items.add("onDrawerClosed()")
                adapter.notifyDataSetChanged()
            }

            override fun onDrawerStateChanged(newState: Int) {
                items.add("onDrawerStateChanged($newState)")
                adapter.notifyDataSetChanged()
            }
        })
    }

    class StringViewDelegate : ViewDelegate<String, TextView>() {

        override fun onCreateView(context: Context): TextView {
            return TextView(context).apply {
                setTextColor(Color.BLACK)
                gravity = Gravity.CENTER
                updatePadding(left = 24.dp, right = 24.dp)
                layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.MATCH_PARENT)
            }
        }

        override fun onBindView(view: TextView, item: String) {
            view.text = item
        }
    }

    class PregnantPagerAdapter(private val viewPager: ViewPager) : PagerAdapter() {

        init {
            viewPager.offscreenPageLimit = count
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any = container.getChildAt(position)

        override fun getCount(): Int = viewPager.childCount

        override fun isViewFromObject(view: View, any: Any): Boolean = (view === any)

        override fun destroyItem(parent: ViewGroup, position: Int, any: Any) = parent.removeView(any as View)

        override fun getItemPosition(any: Any): Int {
            val index = viewPager.indexOfChild(any as View)
            return if (index == -1) POSITION_NONE else index
        }
    }
}

internal val Int.dp: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()
