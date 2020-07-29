package xyz.akko.simplegallery

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.module.AppGlideModule
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import xyz.akko.simplegallery.logic.model.ImgAdapter
import xyz.akko.simplegallery.logic.network.SGnetwork
import xyz.akko.simplegallery.ui.homePage.homePageViewModel

class MainActivity : AppCompatActivity()
{

    private val viewModel by lazy {
        ViewModelProvider(this)[homePageViewModel::class.java]  //https://blog.csdn.net/keysking/article/details/104347348
    }

    private lateinit var adapter: ImgAdapter

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
        adapter = ImgAdapter(viewModel.initList)
        recyclerView.adapter = adapter

        refreshLayout.run {
            setOnRefreshListener(){
                GlobalScope.launch {
                    viewModel.previousPage()
                }
            }
            setOnLoadMoreListener(){
                GlobalScope.launch {
                    Log.d("get","debug")
                    viewModel.nextPage()
                }
            }
        }

        viewModel.imgLiveData.observe(this, Observer {
            viewModel.initList.clear()
            viewModel.initList.addAll(it)
            adapter.notifyDataSetChanged()
            refreshLayout.finishLoadMore()
            refreshLayout.finishRefresh()
        })

        viewModel.currentPage.observe(this, Observer {
            viewModel.reloadPage(viewModel.currentPage.value!!)
        })
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

class GlideModule: AppGlideModule()
{
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
    }
}