package xyz.akko.yandroid

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import xyz.akko.yandroid.logic.model.ImgAdapter
import xyz.akko.yandroid.ui.homePage.HomePageViewModel
import xyz.akko.yandroid.util.MyActivityManager
import xyz.akko.yandroid.util.Utils


class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this)[HomePageViewModel::class.java]  //https://blog.csdn.net/keysking/article/details/104347348
    }

    private lateinit var adapter: ImgAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolBar)
        MyActivityManager().addActivity(this)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
        }

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
        adapter = ImgAdapter(viewModel.initList)
        recyclerView.adapter = adapter

        refreshLayout.setEnableAutoLoadMore(false);

        //TODO 更换GlobalScope
        refreshLayout.run {
            setOnRefreshListener{
                GlobalScope.launch {
                    viewModel.previousPage()
                }
            }
            setOnLoadMoreListener{
                GlobalScope.launch {
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
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> drawerLayout.openDrawer(GravityCompat.START)
            R.id.searchButton -> {
                val intent = Intent()
                intent.setClass(this, SearchActivity::class.java)
                val transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(
                    this@MainActivity
                )
                startActivity(intent, transitionActivityOptions.toBundle())
            }
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_menu_setting -> {
                    startActivity(Intent(Utils.context, SettingsActivity::class.java))
                }
            }
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        MyActivityManager().finishActivity(this)
    }

    //https://juejin.cn/post/6844904065948712974#heading-2
    //https://medium.com/@alexstyl/https-medium-com-alexstyl-animating-the-toolbar-7a8f1aab39dd
    //https://github.com/alexstyl/Material-SearchTransition
}
