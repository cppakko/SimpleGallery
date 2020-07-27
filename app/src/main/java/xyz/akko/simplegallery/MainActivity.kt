package xyz.akko.simplegallery

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import xyz.akko.simplegallery.logic.model.imgAdapter
import xyz.akko.simplegallery.logic.network.SGnetwork
import xyz.akko.simplegallery.logic.repository
import xyz.akko.simplegallery.ui.homePage.homePageViewModel
import java.lang.Exception

class MainActivity : AppCompatActivity()
{

    val viewModel by lazy {
        ViewModelProvider(this)[homePageViewModel::class.java]  //https://blog.csdn.net/keysking/article/details/104347348
    }

    private lateinit var adapter: imgAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolBar)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
        }

        val layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
        adapter = imgAdapter(viewModel.initList) //NULL警告
        recyclerView.adapter = adapter

        viewModel.imgLiveData.observe(this, Observer {
            Log.d("get","i get")
            viewModel.initList.addAll(it)
            adapter.notifyDataSetChanged()
        })
    }

    override fun onResume() {
        super.onResume()
        GlobalScope.launch {
            val temp = SGnetwork().pageGet(1)
            viewModel.imgLiveData.postValue(temp)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId)
        {
            android.R.id.home -> drawerLayout.openDrawer(GravityCompat.START)
        }
        return true
    }
}